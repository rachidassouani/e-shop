package io.rachidassouani.eshopbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"io.rachidassouani.eshopcommon.model"})
public class EShopBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(EShopBackEndApplication.class, args);
	}

}
