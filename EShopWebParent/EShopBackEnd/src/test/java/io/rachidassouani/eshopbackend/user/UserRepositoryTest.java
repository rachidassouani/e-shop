package io.rachidassouani.eshopbackend.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

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
		//userRepository.deleteAll();
	}
	
	@Test
	public void testCreatedUser() {
		Role adminRole = testEntityManager.find(Role.class, 1);
		
		User user = new User("10GGMSJHm", "rachid6@gmail.com", "pass", "rachid", "assouani", false);
		user.getRoles().add(adminRole);
		
		userRepository.save(user);
		
		assertThat(userRepository.findAll().size()).isEqualTo(6);
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
	
	@Test
	public void testDeactivateUser() {
		createUser();
		
		userRepository.updateUserStatus("123GHKJHF", false);
		
	}
	
	@Test
	public void testActivateUser() {
		createUser();
		
		userRepository.updateUserStatus("123GHKJHF", true);
		
	}
	
	
	@Test
	void shouldReturnListFirstPageTest() {
		int pageNumber = 1;
		int pageSize = 4;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> pageUser = userRepository.findAll(pageable);
		
		List<User> listUser = pageUser.getContent();
		
		assertThat(listUser.size()).isEqualTo(pageSize);
	}
	
	private void createUser() {
		
		Role adminRole = testEntityManager.find(Role.class, 1);
		
		User user = new User("123GHKJHF", "rachvid3@gmail.com", "pass", "rachid", "assouani", true);
		user.getRoles().add(adminRole);
		
		userRepository.save(user);
	}
}
