package com.example.audioflux.repository;

import com.example.audioflux.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaRepository extends JpaRepository <Sala, Integer> {
}
