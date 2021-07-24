package com.bijay.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bijay.models.JobInfo;
import com.bijay.models.Teams;
import com.bijay.models.TransferHistory;
import com.bijay.models.Users;
import com.bijay.repos.JobInfoRepo;
import com.bijay.repos.PersonalInfoRepo;
import com.bijay.repos.TeamsRepo;
import com.bijay.repos.TransferHistoryRepo;
import com.bijay.repos.UserRepository;

@Service
public class JobInfoService {
	@Autowired UserRepository userRepository;
	@Autowired TeamsRepo teamsRepo;
	@Autowired PersonalInfoRepo personalInfoRepo;
	@Autowired JobInfoRepo jobInfoRepo;
	@Autowired TransferHistoryRepo transferHistoryRepo;
	
	public void saveTeam(Teams teams) {
		// TODO Auto-generated method stub
		teamsRepo.save(teams);
	}

	public List<Teams> getAllTeams() {
		// TODO Auto-generated method stub
		return teamsRepo.findAll();
	}

	@SuppressWarnings("deprecation")
	public void updateJob(UUID id, List<UUID> ids, String officeName, String branch, String level, String position,
			String department, String joinedDate, String pDate, String yos) throws ParseException {
		// TODO Auto-generated method stub
		Users user = userRepository.findById(id).get();
		JobInfo jobInfo= user.getPersonalInfo().getJobInfo();
		
		Set<Teams> teams = new HashSet<Teams>();
		teams = teamsRepo.findAllById(ids).stream().collect(Collectors.toSet());
		jobInfo.setTeamNames(teams);
		jobInfo.setOfficeName(officeName);
		jobInfo.setBranchName(branch);
		jobInfo.setLevel(level);
		jobInfo.setPosition(position);
		jobInfo.setDepartment(department);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date datee = (Date)format.parse(joinedDate);
		jobInfo.setDateJoined(datee);
		datee = (Date) format.parse(pDate);
		jobInfo.setPermanentDate(datee);
		jobInfo.setYearsOfService(yos);
		jobInfo.setPersonalInfo(user.getPersonalInfo());
		jobInfoRepo.save(jobInfo);
		
		user.getPersonalInfo().setJobInfo(jobInfo);
		personalInfoRepo.save(user.getPersonalInfo());
	}

	public void addTforPromo(UUID id, String info, String promotionName) {
		// TODO Auto-generated method stub
		Users user = userRepository.findById(id).get();
		JobInfo jobInfo= user.getPersonalInfo().getJobInfo();
		TransferHistory h= transferHistoryRepo.save(new TransferHistory(info, promotionName));
		jobInfo.getTransferHistories().add(h);
		jobInfoRepo.save(jobInfo);
	}

	
}
