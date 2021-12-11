package io.rachidassouani.eshopbackend.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.rachidassouani.eshopcommon.model.Role;
import io.rachidassouani.eshopcommon.model.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}
	
	@Override
	public List<Role> findAllRoles() {
		return roleRepository.findAll();
	}

	@Override
	public boolean saveUser(User user) {
		try {
			userRepository.save(user);
			return true;
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

}
