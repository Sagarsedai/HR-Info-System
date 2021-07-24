package com.bijay.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bijay.models.LeaveRequest;
import com.bijay.models.LeaveTypes;
import com.bijay.models.PersonalInfo;
import com.bijay.models.Users;
import com.bijay.repos.LeaveReqRepo;
import com.bijay.repos.LeaveTypeRepo;
import com.bijay.repos.PersonalInfoRepo;
import com.bijay.repos.UserRepository;

@Service
public class LeaveService {

	@Autowired LeaveTypeRepo leaveTypeRepo;
	@Autowired LeaveReqRepo leaveReqRepo;
	@Autowired UserRepository userRepository;
	@Autowired PersonalInfoRepo personalInfoRepo;
	
	public void addNewLeave(LeaveTypes leaveTypes) {
		// TODO Auto-generated method stub
		leaveTypeRepo.save(leaveTypes);
	}

	public LeaveTypes findLeaveBYid(long id) {
		// TODO Auto-generated method stub
		return leaveTypeRepo.findById(id).get();
	}

	public void editLeaveById(long id, String leaveName) {
		// TODO Auto-generated method stub
		LeaveTypes leave = leaveTypeRepo.findById(id).get();
		leave.setLeaveName(leaveName);
		leaveTypeRepo.save(leave);
	}
	
	public void deleteLeaveById(long id) {
		leaveTypeRepo.deleteById(id);
	}

	public List<LeaveTypes> findAllLeaves() {
		// TODO Auto-generated method stub
		return leaveTypeRepo.findAll();
	}

	public List<LeaveRequest> findAllMyLeaves() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Users user =  userRepository.findByEmail(email).get();
		return user.getPersonalInfo().getLeaveRequest();
	}

	public void requestForALeave(long id) {
		// TODO Auto-generated method stub
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		PersonalInfo info=  userRepository.findByEmail(email).get().getPersonalInfo();
		LeaveTypes leave = leaveTypeRepo.findById(id).get();
		if(info.getLeaveRequest().stream().map(m->{
			return m.getLeaveTypes();
		}).collect(Collectors.toList()).contains(leave) ==false) {
			LeaveRequest leaveRequest = new LeaveRequest(false, new Date(), null, null, leave);
			leaveReqRepo.save(leaveRequest);
			info.getLeaveRequest().add(leaveRequest);
			personalInfoRepo.save(info);
		}
	}

	public List<Map<String, Object>> findAllAppLeaves() {
		// TODO Auto-generated method stub
		List<PersonalInfo> pi= userRepository.findAll().stream().map(m->m.getPersonalInfo()).filter(f -> f!=null).collect(Collectors.toList());
		List<Map<String, Object>> obj = new ArrayList<Map<String,Object>>();
		
		obj = pi.stream().map(m->{ 
			Map< String, Object> map = new HashMap<>();
			map.put("user_id", m.getUser().getId().toString());
			map.put("email",m.getUser().getEmail());
			map.put("username", m.getUser().getUsername());
			map.put("leaves", m.getLeaveRequest()); 
			return map;
		}).collect(Collectors.toList());
		 	
		return obj;
	}

	public void updateLeaveReq(UUID id, boolean isApproved, String approvedDate, String lastDate) throws ParseException {
		// TODO Auto-generated method stub
		LeaveRequest leaveRequest = leaveReqRepo.findById(id).get();
		leaveRequest.setApproved(isApproved);
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date datee = (Date)format.parse(approvedDate);
		
		leaveRequest.setApprovedDate(datee);
//		date = dateFormat.parse(dateFormat.format(lastDate));
		
		datee = (Date)format.parse(lastDate);
		leaveRequest.setLastDate(datee);
		leaveReqRepo.save(leaveRequest);
	}
	
	
}
