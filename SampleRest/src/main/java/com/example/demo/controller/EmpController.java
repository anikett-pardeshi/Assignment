package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.repo.EmpRepository;

@RestController
@RequestMapping(value = "/")
public class EmpController {

	@Autowired
	EmpRepository empRepo;
	
	@GetMapping
	public String appStarting() {
		System.out.println("In public String appStarting()");
		return "Employee App Started";
	}
	
	@GetMapping("/employees")
	public List<Employee> getEmployees(){
		System.out.println("In public List<Employee> getEmployees");
		return empRepo.findAll();
	}
	
	
}
