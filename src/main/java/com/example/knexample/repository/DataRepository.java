package com.example.knexample.repository;

import com.example.knexample.model.Data;
import com.example.knexample.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends JpaRepository<Data, Long> {
}
