package com.example.knexample.repository;

import com.example.knexample.model.ManyToManyRel;
import com.example.knexample.model.ManyUsersOneData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManyToManyRelRepository extends JpaRepository<ManyToManyRel, Long> {
}
