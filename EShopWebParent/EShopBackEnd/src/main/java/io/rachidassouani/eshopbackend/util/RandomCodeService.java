package io.rachidassouani.eshopbackend.util;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomCodeService {

	public static String generatCode() {
		final String randomString = RandomStringUtils.random(50);
		return randomString;
	}
}
