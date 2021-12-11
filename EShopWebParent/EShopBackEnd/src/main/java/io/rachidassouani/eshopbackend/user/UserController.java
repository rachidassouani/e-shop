package io.rachidassouani.eshopbackend.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.rachidassouani.eshopcommon.model.Role;
import io.rachidassouani.eshopcommon.model.User;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	
	@GetMapping("/users")
	public String findAllUsers(Model model) {
		
		// retrieving list of users
		List<User> users = userService.findAllUsers();
		
		//setting list of returned users to the model
		model.addAttribute("users", users);
		
		return "users";
	}
	
	@GetMapping("/users/new")
	public String saveUserForm(Model model, @ModelAttribute("user") User user) {
		user.setEnabled(true);
		List<Role> roles = userService.findAllRoles();
		model.addAttribute("roles", roles);
		return "userForm";
	}
	
	@PostMapping("/users/save")
	public String saveUser(User user, RedirectAttributes redirectAttributes) {
		System.out.println("User: " + user);
		
		boolean result = userService.saveUser(user);
		if (result) {
			redirectAttributes.addFlashAttribute("successMessage", "The user has been saved successfully");
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "The user has NOT been saved");
		}
		
		
		return "redirect:/users";
	}
}
