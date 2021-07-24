package com.bijay.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bijay.models.Attendance;
import com.bijay.models.LeaveTypes;
import com.bijay.models.Teams;
import com.bijay.responses.CalendarObj;
import com.bijay.service.AuthService;
import com.bijay.service.JobInfoService;
import com.bijay.service.LeaveService;
import com.bijay.service.PersonalInfoService;
import com.sun.istack.Nullable;

@Controller
@RequestMapping("/admin")
public class AdminController {
	boolean b = false;

	@Autowired AuthService authservice;
	@Autowired LeaveService leaveService;
	@Autowired JobInfoService jobInfoService;
	@Autowired PersonalInfoService personalinfoservice;
	
	@GetMapping("")
	public ModelAndView getIndex() {
		ModelAndView mav = new ModelAndView("indexpage");
		mav.addObject("users", authservice.getAllUsers());
		return mav;
	}

	@GetMapping("/deactivate/{query}")
	public void deactivateUser(@PathVariable("query") UUID id, HttpServletResponse response) throws IOException {
		authservice.deactivateUserById(id);
		response.sendRedirect("/admin");
	}
	

	@GetMapping("/activate/{query}")
	public void activateUser(@PathVariable("query") UUID id, HttpServletResponse response) throws IOException {
		authservice.activateUserById(id);
		response.sendRedirect("/admin");
	}
	
//	LeaveTypes Controls

	@GetMapping("/leaves")
	public ModelAndView getLeaves() {
		ModelAndView mav = new ModelAndView("LeaveTypePage");
		mav.addObject("leaves", authservice.getAllLeaves());
		mav.addObject("allleaves", leaveService.findAllAppLeaves());
		return mav;
	}
	
	@PostMapping("/leaves/add")
	public void addLeave(HttpServletResponse resp, 
			@RequestParam("leaveName") String leaveName) throws IOException {
		leaveService.addNewLeave(new LeaveTypes(leaveName));
		resp.sendRedirect("/admin/leaves");
	}
	
	@GetMapping("/leaves/edit/{id}")
	public ModelAndView getLeaveEditPage(@PathVariable("id") long id) {
		ModelAndView mv = new ModelAndView("leaveEdit"); 
		mv.addObject("leave", leaveService.findLeaveBYid(id));
		return mv;
	}
	
	@PostMapping("/leaves/edit/{id}")
	public void editLeave(HttpServletResponse resp, 
			@RequestParam("leaveName") String leaveName, 
			@PathVariable("id") long id) throws IOException {
		leaveService.editLeaveById(id, leaveName);
		resp.sendRedirect("/admin/leaves");
	}
	
	@GetMapping("/leaves/delete/{id}")
	public void deleteLeave(HttpServletResponse resp, 
			@PathVariable("id") long id) throws IOException {
		leaveService.deleteLeaveById(id);
		resp.sendRedirect("/admin/leaves");
	}
	
	@GetMapping("/add/team")
	public ModelAndView addTeam() {
		ModelAndView mv = new ModelAndView("admin/addteam");
		mv.addObject("teams",jobInfoService.getAllTeams());
		return mv;
	}
	
	@PostMapping("/add/team")
	public void addNewTeam(HttpServletResponse resp, @RequestParam("teamName") String teamName) throws IOException {
		jobInfoService.saveTeam(new Teams(teamName));
		resp.sendRedirect("/admin/add/team");
	}
	
	@GetMapping("/assignjob/{id}")
	public ModelAndView getassignjob(@PathVariable("id") UUID id) {
		ModelAndView mv = new ModelAndView("admin/jobassign");
		mv.addObject("teams",jobInfoService.getAllTeams());
		mv.addObject("user",authservice.findUserById(id));
		return mv;
	}
	
	@PostMapping("/update/job/{id}")
	public void update(HttpServletResponse resp,
			@PathVariable("id") UUID id,
			@RequestParam("teams") List<UUID> ids,
			@RequestParam("officeName") String officeName,
			@RequestParam("branch") String branch,
			@RequestParam("level") String level,
			@RequestParam("position") String position,
			@RequestParam("department") String department,
			@RequestParam("joinedDate") String joinedDate,
			@RequestParam("pDate") String pDate,
			@RequestParam("yos") String yos) throws IOException, ParseException {
		jobInfoService.updateJob(id, ids, officeName, branch, level, position, department, joinedDate, pDate, yos);
		resp.sendRedirect("/admin");
	}
	
	@GetMapping("/tf-promo/{id}")
	public ModelAndView getTFpage(@PathVariable UUID id) {
		ModelAndView mv = new ModelAndView("admin/maketransfer");
		mv.addObject("id",id);
		return mv;
	}
	
	@PostMapping("/promo/{id}")
	public void addTfOrPromo(@PathVariable UUID id,
			HttpServletResponse response,
			@RequestParam("info") String info,
			@RequestParam("promotionName") String promotionName) throws IOException {
		jobInfoService.addTforPromo(id, info, promotionName);
		response.sendRedirect("/admin");
	}
	
	@PostMapping("/leave/approve/{id}")
	public void approveLeave(@PathVariable UUID id,
			HttpServletResponse response,
			@RequestParam("isApproved") boolean isApproved,
			@RequestParam("approvedDate") String approvedDate,
			@RequestParam("lastDate") String lastDate) throws IOException, ParseException {
			System.out.println(isApproved);
			System.out.println(approvedDate+"  "+lastDate);
			leaveService.updateLeaveReq(id,isApproved,approvedDate, lastDate);
		response.sendRedirect("/admin/leaves");
	}
	
	@GetMapping("/attendance")
	public ModelAndView getAttendancePage() {
		ModelAndView mv = new ModelAndView("attendance");
		mv.addObject("users", authservice.getAllUsers());
		return mv;
	}

	@GetMapping("/markattendance/{id}")
	public ModelAndView getMarkAttendancePage(@PathVariable UUID id) {
		ModelAndView mv = new ModelAndView("admin/attendancemark");
		mv.addObject("obj", personalinfoservice.findMyAttendaneForToday(id));
		return mv;
	}
	
	@GetMapping("/viewattendance/{id}")
	public ModelAndView viewAttendancePage(@PathVariable UUID id) {
		ModelAndView mv = new ModelAndView("admin/viewAttendance");
		List<Attendance> a = personalinfoservice.getAllAttendanceForMe(id);
		mv.addObject("obj", a);
		Calendar c = Calendar.getInstance();
		List<Integer> i = new ArrayList<Integer>();
		
		int cDay = c.get(Calendar.DATE);
		int cMonth = c.get(Calendar.MONTH);
		int cYear = c.get(Calendar.YEAR);
		
		GregorianCalendar gc =new GregorianCalendar(cYear,cMonth,1);
		int days = gc.getActualMaximum(Calendar.DATE);
		int startinweek = gc.getActualMaximum(Calendar.WEEK_OF_MONTH);
		int count = 1;
		
		gc = new GregorianCalendar(cYear,cMonth,days);
		int totalWEeks = gc.getActualMaximum(Calendar.WEEK_OF_MONTH);
		
		List<CalendarObj> calendarObjs  = new ArrayList<CalendarObj>();
		String[] s = {"Sun","Mon","Tue","Wed","Thur","Fri","Sat","Sun"};
		String[] m = {"Jan", "Feb", "Mar", "Apr","May", "Jun","Jul","Aug","Sep","Nov","Dec"};
		for(int k=1;k<=totalWEeks;k++) {
			for(int l=1;l<=7;l++) {
				if(count <startinweek || (count - startinweek+1)>31) {
					System.out.print(startinweek+" ");
				}else {
					System.out.print(count - startinweek+1+" "+s[l]);
					for(Attendance f:a){
						if(f.getClockIn().getMonth() ==cMonth && f.getClockIn().getYear() ==2021 && f.getClockIn().getDay() ==count-startinweek+1) {
							b =true;
						}
					};
					calendarObjs.add(new CalendarObj(2021+" ",count - startinweek+1 +" "+s[l-1],m[cMonth] +" ", b));
				}
				count++;
			}
			System.out.println(calendarObjs);
		}
		
		/*
		 * for(int j=1;j<=c.getActualMaximum(Calendar.DAY_OF_MONTH);j++) { i.add(j); }
		 */
		mv.addObject("dates",calendarObjs);
		return mv;
	}
	@PostMapping("/attendance/clkIn/{id}")
	public void MarkAttendanceClkin(@PathVariable UUID id, HttpServletResponse response
			) throws IOException, ParseException {
		personalinfoservice.markAttendance(id,true,false);
		response.sendRedirect("/admin/attendance");
	}
	
	@PostMapping("/attendance/clkOut/{id}")
	public void MarkAttendanceClkOut(@PathVariable UUID id, HttpServletResponse response
			) throws IOException, ParseException {
		personalinfoservice.markAttendance(id,false,true);
		response.sendRedirect("/admin/attendance");
	}
}
