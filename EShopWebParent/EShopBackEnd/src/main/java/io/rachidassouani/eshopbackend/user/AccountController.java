package io.rachidassouani.eshopbackend.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.rachidassouani.eshopbackend.security.UserDetailsImpl;
import io.rachidassouani.eshopbackend.util.Constant;
import io.rachidassouani.eshopcommon.model.User;

@Controller
public class AccountController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);
	
	private final UserService userService;

	public AccountController(UserService userService) {
		this.userService = userService;
	}
	
	
	@GetMapping("account")
	public String viewAccountDetails(@AuthenticationPrincipal UserDetailsImpl loggedUSer, Model model) {

		LOGGER.info("viewing account details");
		
		// get the userName of the logged user
		String email = loggedUSer.getUsername();
		
		// find user by it's userName
		User user = userService.findUserByEmail(email);
		
		// sent the founded user in the model
		model.addAttribute("user", user);
		
		// return the form 
		return "accountForm";
	}
	
	@PostMapping("/account/update")
	public String updateAccountDetails(User user, 
			RedirectAttributes redirectAttributes, 
			@AuthenticationPrincipal UserDetailsImpl loggedUser) throws UserNotFoundException {

		// calling update user method in order to update the logged in user
		userService.updateUser(user);

		// set the new values (first and last Name) of the logged user
		loggedUser.setFirstName(user.getFirstName());
		loggedUser.setLastName(user.getLastName());
		
		redirectAttributes.addFlashAttribute("successMessage", Constant.ACCOUNT_DETAILS_UPDATED_SUCCESSFULLY);

		// return the form 
		return "redirect:/account";
	}
}
