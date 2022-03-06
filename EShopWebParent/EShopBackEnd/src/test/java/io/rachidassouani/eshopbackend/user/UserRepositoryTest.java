package io.rachidassouani.eshopbackend.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import io.rachidassouani.eshopbackend.user.UserRepository;
import io.rachidassouani.eshopcommon.model.Role;
import io.rachidassouani.eshopcommon.model.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	@BeforeEach
	public void cleanup() {
		userRepository.deleteAll();
	}
	
	@Test
	public void testCreatedUser() {
		Role adminRole = testEntityManager.find(Role.class, 1);
		
		User user = new User("123GHKJHF", "rachid@gmail.com", "pass", "rachid", "assouani", false);
		user.getRoles().add(adminRole);
		
		userRepository.save(user);
		
		assertThat(userRepository.findAll().size()).isEqualTo(1);
	}
	
	@Test
	public void testCreatedUserWithManyRoles() {
		Role shipperRole = testEntityManager.find(Role.class, 4);
		Role assistantRole = testEntityManager.find(Role.class, 5);
		
		User user = new User("HKJD46456", "shipper-assistant@gmail.com", "pass", "shipper-assistant-rachid",
				"shipper-assistant-assouani", false);
		user.getRoles().add(shipperRole);
		user.getRoles().add(assistantRole);
		
		userRepository.save(user);
			
		assertThat(userRepository.findAll().size()).isEqualTo(1);
	}
	
	@Test
	public void testFindUserByCode() {
		createUser();
		
		User foundedUser = userRepository.findUserByCode("123GHKJHF");
		
		assertThat(foundedUser).isNotNull();
	}
	
	
	private void createUser() {
		
		Role adminRole = testEntityManager.find(Role.class, 1);
		
		User user = new User("123GHKJHF", "rachid3@gmail.com", "pass", "rachid", "assouani", false);
		user.setId(33);
		user.getRoles().add(adminRole);
		
		userRepository.save(user);
	}
}
