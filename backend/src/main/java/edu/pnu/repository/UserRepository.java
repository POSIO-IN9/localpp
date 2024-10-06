package edu.pnu.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.pnu.domain.UserEntity;

import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<UserEntity, String> {
//	boolean existsByEmailAndPassword(String email, String password);
	Optional<UserEntity> findByEmail(String email);
}