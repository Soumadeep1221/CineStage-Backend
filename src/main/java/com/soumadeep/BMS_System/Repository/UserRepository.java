package com.soumadeep.BMS_System.Repository;

import com.soumadeep.BMS_System.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // select * from users where email = ?1
    Optional<User> findByEmail(String email);

    //select * from users where email = ?1
    boolean existsByEmail(String email);
}
