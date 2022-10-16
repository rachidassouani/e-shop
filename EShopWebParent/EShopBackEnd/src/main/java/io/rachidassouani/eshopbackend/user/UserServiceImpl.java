package io.rachidassouani.eshopbackend.user;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.rachidassouani.eshopbackend.util.Constant;
import io.rachidassouani.eshopbackend.util.RandomCodeService;
import io.rachidassouani.eshopcommon.model.Role;
import io.rachidassouani.eshopcommon.model.User;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}
	
	@Override
	public Page<User> findAllUsersPerPageNumber(int pageNumber) {
		
		Pageable pageable = PageRequest.of(pageNumber - 1, Constant.USERS_PER_PAGE);
		
		// find all users by pageNumber and pageSize
		Page<User> pageUser = userRepository.findAll(pageable);
		
		return pageUser;
	}
	
	@Override
	public List<Role> findAllRoles() {
		return roleRepository.findAll();
	}

	@Override
	public boolean saveUser(User user) {
		try {
			
			// case of updating existing user
			if (user.getId() != null) {
				User existingUser = userRepository.findById(user.getId()).get();
				if (user.getPassword().isEmpty()) {	
					user.setPassword(existingUser.getPassword());	
				} else {	
					encodeUserPassword(user);
				}
				user.setCode(existingUser.getCode());
				
			// case of adding new user
			} else {
				user.setCode(RandomCodeService.generatCode());
				encodeUserPassword(user);
			}
			
			// saving user
			userRepository.save(user);
			return true;
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean isEmailUnique(Integer id, String email) {
		User user = userRepository.findUserByEmail(email);
		
		if (user == null) return true;
		
		boolean creatingNewUser = (id == null);
		
		if (creatingNewUser) {
			if (user != null) {
				return false;
			}
		} else {
			if (user.getId() != id) {
				return false;
			}
		}
		return true;
		
	}

	@Override
	public User findUserByCode(String code) throws UserNotFoundException {	
		try {
			User user =  userRepository.findUserByCode(code);
			if (user == null)
				throw new NoSuchElementException();
			return user;
		} catch (NoSuchElementException ex) {
			throw new UserNotFoundException(Constant.USER_NOT_FOUND);
		}
	}
	
	private void encodeUserPassword(User user) {
		final String password = user.getPassword();
		final String encodedPassword = encodePassword(password);
		
		user.setPassword(encodedPassword);
	}
	
	private String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}

	@Override
	public void deleteUserByCode(String code) throws UserNotFoundException {
		User foundedUser = userRepository.findUserByCode(code);
		
		if (foundedUser == null) {
			throw new UserNotFoundException(Constant.USER_NOT_FOUND);
		}
		userRepository.delete(foundedUser);
		
	}

	@Override
	public void updateUserStatus(String code, boolean status) throws UserNotFoundException {
		User userToUpdate = userRepository.findUserByCode(code);
		if (userToUpdate == null) {
			throw new UserNotFoundException(Constant.USER_NOT_FOUND);
		}
		userRepository.updateUserStatus(code, status);
	}
	
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}
	
	@Override
	public void updateUser(User userInForm) throws UserNotFoundException {
		
		// find user by logged in user's ID
		User existingUser = userRepository.findById(userInForm.getId()).get();
		
		// logged in user want's to update his password
		if (!userInForm.getPassword().isEmpty()) {
			existingUser.setPassword(userInForm.getPassword());
			encodeUserPassword(existingUser);
		}
		
		// logged in user want's to update his firstName
		existingUser.setFirstName(userInForm.getFirstName());
		
		// logged in user want's to update his lastName
		existingUser.setLastName(userInForm.getLastName());
	
		// update the user
		userRepository.save(existingUser);
	}
}
