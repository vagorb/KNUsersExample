package com.example.knexample.repository;

import com.example.knexample.model.Data;
import com.example.knexample.model.ManyUsersOneData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManyUsersOneDataRepository extends JpaRepository<ManyUsersOneData, Long> {
}
