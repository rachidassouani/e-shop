package io.rachidassouani.eshopbackend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.rachidassouani.eshopcommon.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{}
