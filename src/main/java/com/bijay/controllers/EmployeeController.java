package com.bijay.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bijay.models.Attendance;
import com.bijay.models.CarrerInfo;
import com.bijay.models.PersonalInfo;
import com.bijay.responses.CIReturns;
import com.bijay.responses.CalendarObj;
import com.bijay.responses.PIReturns;
import com.bijay.service.LeaveService;
import com.bijay.service.PersonalInfoService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	boolean b= false;
	@Autowired PersonalInfoService personalinfoservice;
	@Autowired LeaveService leaveService;
	
	@GetMapping("")
	public ModelAndView getDash() {
		ModelAndView mv  = new ModelAndView("users/dashboard");
		return mv;
	}
	@GetMapping("/personalinfo")
	public ModelAndView getPersonalInfo() {
		ModelAndView mv  = new ModelAndView("users/personalinfo");
		PersonalInfo info = personalinfoservice.findMyInfo();
		PIReturns piReturns = new PIReturns(
				info.getId(),info.getOfficeContact(),info.getPersonalContact(),info.getParentName(),
				info.getPermanentAddress(),info.getTemporaryAddress(),info.getHealthStatus(),info.getUser()
				);
		if(piReturns.getCarrerInfos() == null) {
			piReturns.setCarrerInfos(new HashSet<CIReturns>());
		}
		info.getCarrerInfos().forEach(f -> {
			piReturns.getCarrerInfos().add(					
					new CIReturns(f.getId(), new String(f.getFile()), f.getName(), f.getDetails(), f.getFileType())
					);
		});
		mv.addObject("personalinfo",info);
		return mv;
	}
	@GetMapping("/jobinfo")
	public ModelAndView getJobInfo() {
		ModelAndView mv  = new ModelAndView("users/jobinfo");
		mv.addObject("info", personalinfoservice.findMyInfo().getJobInfo());
		return mv;
	}
	@GetMapping("/leaveinfo")
	public ModelAndView getLeaveInfo() {
		ModelAndView mv  = new ModelAndView("users/leaveInfo");
		mv.addObject("leaves", leaveService.findAllLeaves());
		mv.addObject("myLeaves", leaveService.findAllMyLeaves());
		return mv;
	}
	@GetMapping("/personalinfo/edit/{id}")
	public ModelAndView getPersonalInfoEditPage(@PathVariable("id") UUID id) {
		ModelAndView mv = new ModelAndView("users/editpersonalinfo");
		mv.addObject("personalinfo",personalinfoservice.findInfoById(id));
		return mv;
	}
	@PostMapping("/personalinfo/edit/{id}")
	public void updatePersonalInfo(
			@PathVariable("id") UUID id,
			HttpServletResponse resp,
			@RequestParam("officeContact") String oc,
			@RequestParam("personalContact") String pc,
			@RequestParam("permanentAddress") String pa,
			@RequestParam("temporaryAddress") String ta,
			@RequestParam("parentName") String pn,
			@RequestParam("healthStatus") String hs
			) throws IOException {
		personalinfoservice.updateMyPersonalInfo(id,oc,pc,pa,ta,pn,hs);
		resp.sendRedirect("/employee/personalinfo");
	}

	@PostMapping("/personalinfo/add/")
	public void updateCarrerInfo(
			HttpServletResponse resp,
			@RequestParam("name") String name,
			@RequestParam("details") String details,
			@RequestPart("file") MultipartFile file
			) throws IOException {
		
		personalinfoservice.addnewcarrerinfo(new CarrerInfo(file.getBytes(),name,details, file.getContentType()));
		resp.sendRedirect("/employee/personalinfo");
	}
	@GetMapping("/carrerinfo/delete/{id}")
	public void deleteCarrerInfo(
			HttpServletResponse resp,
			@PathVariable("id") UUID id
			) throws IOException {	
		personalinfoservice.deleteCarrerInfoById(id);
		resp.sendRedirect("/employee/personalinfo");
	}
	
	@GetMapping("/downloads/file/{id}")
	ResponseEntity<byte[]> downLoadSingleFile(@PathVariable("id") UUID id, HttpServletRequest request) {

	        CarrerInfo doc = personalinfoservice.findCiByFileId(id);

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(doc.getFileType()))
//	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName="+resource.getFilename())
	                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + doc.getId())
	                .body(doc.getFile());
	    }
	
	@GetMapping("/apply/leave/{id}")
	public void requestALeave(
			HttpServletResponse resp,
			@PathVariable("id") long id
			) throws IOException {	
		leaveService.requestForALeave(id);
		resp.sendRedirect("/employee/personalinfo");
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
	
}
