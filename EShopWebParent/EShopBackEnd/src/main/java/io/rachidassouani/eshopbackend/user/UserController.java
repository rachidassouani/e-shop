package io.rachidassouani.eshopbackend.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
		
		model.addAttribute("pageTitle", "Create new user");
		
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
	
	@GetMapping("/users/edit/{code}")
	public String editUser(@PathVariable("code") String code, Model model, 
			RedirectAttributes redirectAttributes) {
		
		try {
			model.addAttribute("pageTitle", "Update existing User (CODE: "+code+")");
			
			User user = userService.findUserByCode(code);
			List<Role> roles = userService.findAllRoles();
			
			model.addAttribute("user", user);
			model.addAttribute("roles", roles);
			
			return "userForm";
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
			
		}
		return "redirect:/users";
	}
	
	@GetMapping("/users/delete/{code}")
	public String deleteUser(@PathVariable("code") String code, Model model, 
			RedirectAttributes redirectAttributes) {
		
		try {
			
			userService.deleteUserByCode(code);
			
			redirectAttributes.addFlashAttribute("successMessage", "THe user code " + code
					+" has been deleted succefully");
			
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
			
		}
		return "redirect:/users";
	}
	
	@GetMapping("/users/{code}/activate-user")
	public String activateUser(@PathVariable("code") String code, Model model, 
			RedirectAttributes redirectAttributes) {
		
		try {
			
			userService.updateUserStatus(code, true);
			
			redirectAttributes.addFlashAttribute("successMessage", "The user code " + code
					+" has been activated succefully");
			
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
			
		}
		return "redirect:/users";
	}
	
	@GetMapping("/users/{code}/deactivate-user")
	public String deActivateUser(@PathVariable("code") String code, Model model, 
			RedirectAttributes redirectAttributes) {
		
		try {
			
			userService.updateUserStatus(code, false);
			
			redirectAttributes.addFlashAttribute("successMessage", "The user code " + code
					+" has been deactivated succefully");
			
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
			
		}
		return "redirect:/users";
	}
	
}
