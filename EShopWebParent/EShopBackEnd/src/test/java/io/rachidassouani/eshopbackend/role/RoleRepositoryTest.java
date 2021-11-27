package io.rachidassouani.eshopbackend.role;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import io.rachidassouani.eshopcommon.model.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTest {

	@Autowired
	private RoleRepository roleRepository;

	@BeforeEach
	public void cleanup() {
		roleRepository.deleteAll();
	}

	@Test
	public void testSaveAllRoles() {

		Role adminRole = new Role("Admin", "Manage Everything");

		Role salesPersonRole = new Role("SalesPerson",
				"Manage Product Price, " + "customers, Shipping, Orders and Sales Report");

		Role editorRole = new Role("Editor", "Manage Categories, Brands, " + "Products, Articles and Menus");

		Role shipperRole = new Role("Shipper", "View Products, View Orders and update orders status");

		Role assistantRole = new Role("Assistant", "Manage questions and reviews");

		List<Role> savedRoles = roleRepository
				.saveAll(List.of(adminRole, salesPersonRole, editorRole, shipperRole, assistantRole));

		assertThat(savedRoles.size()).isEqualTo(5);
	}
}
