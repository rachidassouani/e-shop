package io.rachidassouani.eshopbackend.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.rachidassouani.eshopcommon.model.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

}
