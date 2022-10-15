package io.rachidassouani.eshopbackend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import io.rachidassouani.eshopbackend.user.UserService;
import io.rachidassouani.eshopbackend.util.Constant;
import io.rachidassouani.eshopcommon.model.User;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findUserByEmail(username);
		if (user != null) {			
			return new UserDetailsImpl(user);
		}
		throw new UsernameNotFoundException(Constant.USER_NOT_FOUND + " : " + username);
	}
}
