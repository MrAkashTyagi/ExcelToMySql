package com.batch.excel.repository;

import com.batch.excel.entities.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepo extends JpaRepository<Guest, Integer> {
}
