package com.example.demo.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@RestController
@RequestMapping("/api")
public class EmployeeController {

	//create object of employee repository
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	//we send data wthin database we use post mapping
	@PostMapping("/employees")
	public ResponseEntity<String> createNewEmployee(@RequestBody Employee employee) {
	    employeeRepository.save(employee);
	    return new ResponseEntity<>("Employee inserted successfully", HttpStatus.CREATED);
	}

	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getAllEmployee()
	{
		List<Employee> empList= new ArrayList<>();
		employeeRepository.findAll().forEach(empList::add);
		return new  ResponseEntity<List<Employee>>(empList,HttpStatus.OK);
		
	}
	
	//retrive data from database according to id
	@GetMapping("/employees/{empid}")
	public ResponseEntity<Employee>getEmployeeById(@PathVariable long empid)
	{
		Optional<Employee> emp=employeeRepository.findById(empid);
		if(emp.isPresent())
		{
			return new ResponseEntity<Employee>(emp.get(),HttpStatus.FOUND);
		}else {
			return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
		}
	}
	
	//if we want to upadet details of employee
	
	@PutMapping("/employees/{empid}")
	
	public String updateEmployeeDetails(@PathVariable long empid,@RequestBody Employee employee)
	{
		Optional<Employee> emp=employeeRepository.findById(empid);
		if(emp.isPresent())
		{
			Employee existEmployee=emp.get();
			existEmployee.setEmp_salary(employee.getEmp_salary());
			existEmployee.setEmp_city(employee.getEmp_city());
			
			employeeRepository.save(existEmployee);
			return "Employee Details against id"+empid+"updated";
		}else {
			return "Employee id does not exist";
		}
	}
	
	//Delete employee deatils Successfully
	
	@DeleteMapping("/employees/{empid}")
	
	public String employeeDeleated(@PathVariable long empid)
	{
		employeeRepository.deleteById(empid);
		return "Employee Deleted Successfully";
	}
	
	//delete all data from repository means database
	
	@DeleteMapping("/employees")
	public String deleteAllEmployee()
	{
		employeeRepository.deleteAll();
		return "All Employee Deleted Successfully";
	}
	
	
	
	
	
	
	
}
