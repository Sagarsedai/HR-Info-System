package com.bijay.repos;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bijay.models.LeaveTypes;

@Repository
public interface LeaveTypeRepo extends JpaRepository<LeaveTypes, Long>{

}
