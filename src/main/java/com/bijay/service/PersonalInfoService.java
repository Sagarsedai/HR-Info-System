package com.bijay.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bijay.models.Attendance;
import com.bijay.models.CarrerInfo;
import com.bijay.models.JobInfo;
import com.bijay.models.PersonalInfo;
import com.bijay.models.Users;
import com.bijay.repos.AttendanceRepo;
import com.bijay.repos.CarrerRepo;
import com.bijay.repos.JobInfoRepo;
import com.bijay.repos.PersonalInfoRepo;
import com.bijay.repos.UserRepository;
import com.bijay.responses.CIReturns;
import com.bijay.security.UsersDetails;

@Service
public class PersonalInfoService {

	@Autowired UserRepository userRepository;
	@Autowired PersonalInfoRepo personalInfoRepo;
	@Autowired CarrerRepo carrerRepo;
	@Autowired JobInfoRepo jobInfoRepo;
	@Autowired AttendanceRepo attendanceRepo;

	public PersonalInfo findMyInfo() {
		// TODO Auto-generated method stub
		String email= SecurityContextHolder.getContext().getAuthentication().getName();
		Users user = userRepository.findByEmail(email).get();
		if(user.getPersonalInfo() == null) {
			JobInfo jobInfo = new JobInfo();
			jobInfoRepo.save(jobInfo);
			
			PersonalInfo info = new PersonalInfo();
			info.setUser(user);
			info.setJobInfo(jobInfo);
			personalInfoRepo.save(info);
			user.setPersonalInfo(info);
			userRepository.save(user);
		}
		return user.getPersonalInfo();
	}

	public PersonalInfo findInfoById(UUID id) {
		// TODO Auto-generated method stub
		return personalInfoRepo.findById(id).get();
	}

	public void updateMyPersonalInfo(UUID id,String oc, String pc, String pa, String ta, String pn, String hs) {
		// TODO Auto-generated method stub
		PersonalInfo info = personalInfoRepo.findById(id).get();
		info.setOfficeContact(oc);
		info.setPersonalContact(pc);
		info.setPermanentAddress(pa);
		info.setTemporaryAddress(ta);
		info.setParentName(pn);
		info.setHealthStatus(hs);
		personalInfoRepo.save(info);
	}

	public void addnewcarrerinfo(CarrerInfo carrerInfo) {
		// TODO Auto-generated method stub
		String email= SecurityContextHolder.getContext().getAuthentication().getName();
		Users user = userRepository.findByEmail(email).get();
		PersonalInfo info = user.getPersonalInfo();
		carrerInfo = carrerRepo.save(carrerInfo);
		info.getCarrerInfos().add(carrerInfo);
		personalInfoRepo.save(info);
	}

	public void deleteCarrerInfoById(UUID id) {
		// TODO Auto-generated method stub
		String email= SecurityContextHolder.getContext().getAuthentication().getName();
		Users user = userRepository.findByEmail(email).get();
		PersonalInfo info = user.getPersonalInfo();
		CarrerInfo carrerInfo = carrerRepo.findById(id).get();
		info.getCarrerInfos().remove(carrerInfo);
		personalInfoRepo.save(info);
		carrerRepo.deleteById(id);
	}

	public CarrerInfo findCiByFileId(UUID id) {
		return carrerRepo.findById(id).get();
	}

	public Attendance findMyAttendaneForToday(UUID id) {
		// TODO Auto-generated method stub
		PersonalInfo info = userRepository.findById(id).get().getPersonalInfo();
		List<Attendance> a =info.getAttendances().stream().filter(f->f.getClockIn().compareTo(new Date())<1).collect(Collectors.toList());
		if(a.size()>0) { return a.get(0); }else { return new Attendance(); }
	}

	public void markAttendance(UUID id,boolean clkIn,boolean clkOut) throws ParseException {
		// TODO Auto-generated method stub
		Attendance a = attendanceRepo.findById(id).get();
		if(clkIn) {
			a.setClockIn(new Date());
		}else if(clkOut) {
			a.setClockOut(new Date());
		}
		attendanceRepo.save(a);
	}

	public List<Attendance> getAllAttendanceForMe(UUID id) {
		// TODO Auto-generated method stub
		List<Attendance> i = userRepository.findById(id).get().getPersonalInfo().getAttendances();
		i.sort((d1,d2) -> d1.getClockIn().compareTo(d2.getClockIn()));
		return i;
	}
	
	
	
}
