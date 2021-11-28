package io.rachidassouani.eshopbackend.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.rachidassouani.eshopcommon.model.User;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	
	@GetMapping("/users")
	public String findAllUsers(Model model) {
		
		// retrieving list of users
		List<User> listUsers = userService.findAllUsers();
		
		//setting list of returned users to the model
		model.addAttribute("listUsers", listUsers);
		
		return "users";
	}
}
