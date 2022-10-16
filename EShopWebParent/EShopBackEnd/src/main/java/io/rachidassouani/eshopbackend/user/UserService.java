package io.rachidassouani.eshopbackend.user;

import java.util.List;

import org.springframework.data.domain.Page;

import io.rachidassouani.eshopcommon.model.Role;
import io.rachidassouani.eshopcommon.model.User;

public interface UserService {

	List<User> findAllUsers();
	Page<User> findAllUsersPerPageNumber(int pageNumber);
	List<Role> findAllRoles();
	boolean saveUser(User user);
	boolean isEmailUnique(Integer id, String email);
	User findUserByCode(String code) throws UserNotFoundException;
	User findUserByEmail(String email);
	void deleteUserByCode(String code) throws UserNotFoundException;
	void updateUserStatus(String code, boolean status) throws UserNotFoundException;
	void updateUser(User userInForm) throws UserNotFoundException;
}
