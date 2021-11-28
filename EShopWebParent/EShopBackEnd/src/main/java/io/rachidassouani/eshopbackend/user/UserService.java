package io.rachidassouani.eshopbackend.user;

import java.util.List;

import io.rachidassouani.eshopcommon.model.User;

public interface UserService {

	List<User> findAllUsers();
}
