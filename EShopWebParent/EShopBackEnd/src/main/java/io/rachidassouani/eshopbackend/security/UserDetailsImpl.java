package io.rachidassouani.eshopbackend.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.rachidassouani.eshopcommon.model.Role;
import io.rachidassouani.eshopcommon.model.User;

public class UserDetailsImpl implements UserDetails {

	private User user;
	
	public UserDetailsImpl(User user) {
		this.user = user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Role> roles = user.getRoles();
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		System.out.println("pass:" + user.getPassword());
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.isEnabled();
	}
	
	public String getFullName() {
		return user.getFirstName() + " " + user.getLastName();
	}
	
	public void setFirstName(String firstName) {
		this.user.setFirstName(firstName);
	}
	
	public void setLastName(String firstName) {
		this.user.setLastName(firstName);
	}
	
}