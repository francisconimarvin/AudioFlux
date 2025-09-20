package com.example.audioflux.repository;

import com.example.audioflux.model.SalaLogging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaLoggingRepository extends JpaRepository <SalaLogging, Integer> {
}
