package io.rachidassouani.eshopbackend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.rachidassouani.eshopcommon.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findUserByEmail(String email);

	User findUserByCode(String code);
}
