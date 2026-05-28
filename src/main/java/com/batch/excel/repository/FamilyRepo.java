package com.batch.excel.repository;

import com.batch.excel.entities.Family;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyRepo extends JpaRepository<Family, Integer> {
}
