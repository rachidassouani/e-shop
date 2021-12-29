package io.rachidassouani.eshopbackend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

	@Autowired
	public UserService userService;
	
	@PostMapping("/users/checkEmail")
	public String checkDuplicateEmail(@Param("id") Integer id, @Param("email") String email) {
		return (userService.isEmailUnique(id, email)) ? "OK" : "Duplicated"; 
	}
}
