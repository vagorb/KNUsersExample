package com.example.knexample.repository;

import com.example.knexample.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByEmail(String email);
    List<User> findByUsernameContaining(String username);
    List<User> findByUsernameContaining(String username, Sort sort);
    Page<User> findByUsernameContaining(String username, Pageable pageable);
}
