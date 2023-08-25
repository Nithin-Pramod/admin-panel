package com.adminpanel.repository;

import com.adminpanel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);

	List<User> findByFirstNameContaining(String firstName);
	List<User> findByLastNameContaining(String lastName);
	List<User> findByEmailContaining(String email);

}
