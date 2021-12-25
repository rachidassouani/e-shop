package io.rachidassouani.eshopbackend.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

public class GenerateRandomStringTest {

	@Test
	public void generateRandomString() {
		final String randomString = RandomStringUtils.randomAlphanumeric(30);
		System.out.println(randomString);
		assertThat(randomString.length()).isEqualTo(30);
	}
}
