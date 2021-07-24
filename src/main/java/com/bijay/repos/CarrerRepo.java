package com.bijay.repos;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bijay.models.CarrerInfo;

@Repository
public interface CarrerRepo extends JpaRepository<CarrerInfo, UUID>{

}
