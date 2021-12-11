package io.rachidassouani.eshopbackend.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {

	@Test
	public void testEncodePassword() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		
		final String testPassword = "Rachid";
		final String encodedTestPassword = bCryptPasswordEncoder.encode(testPassword);
		
		boolean matches = bCryptPasswordEncoder.matches(testPassword, encodedTestPassword);
		
		assertThat(matches).isTrue();
	}
}
