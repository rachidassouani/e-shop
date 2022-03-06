package io.rachidassouani.eshopbackend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.rachidassouani.eshopcommon.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findUserByEmail(String email);

	User findUserByCode(String code);
	
	@Query("DELETE FROM User u WHERE u.code=:code")
	void deleteUserByCode(@Param("code") String code); 
}
