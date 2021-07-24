package com.bijay.repos;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bijay.models.LeaveRequest;

@Repository
public interface LeaveReqRepo extends JpaRepository<LeaveRequest, UUID>{

}
