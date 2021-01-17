package com.ferri.expensestrackerapi.controller;

import com.ferri.expensestrackerapi.aspect.PermissionCheck;
import com.ferri.expensestrackerapi.exception.ResourceNotFoundException;
import com.ferri.expensestrackerapi.model.Employee;
import com.ferri.expensestrackerapi.repository.EmployeeRepository;
import com.ferri.expensestrackerapi.security.utils.Workspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	// get Employees
	@GetMapping("/employees")
	@PermissionCheck(workspace = {Workspace.EMPLOYEE},read = true)
	public List<Employee> getAllEmployee() {
		return employeeRepository.findAll();
	}

	// get Employee by id
	@GetMapping("/employees/{id}")
	@PermissionCheck(workspace = {Workspace.EMPLOYEE},read = true)
	public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id " + employeeId));
		return ResponseEntity.ok().body(employee);
	}

	// save Employee
	@PostMapping("/employees")
	@PermissionCheck(workspace = {Workspace.EMPLOYEE},write = true)
	public Employee createEmployee(@RequestBody Employee employee) {
		return this.employeeRepository.save(employee);
	}

	// update Employee
	@PutMapping("/employees/{id}")
	@PermissionCheck(workspace = {Workspace.EMPLOYEE},write = true)
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
			@Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id " + employeeId));
		employee.setEmail(employeeDetails.getEmail());
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());

		return ResponseEntity.ok(this.employeeRepository.save(employee));
	}

	// delete Employee
	@DeleteMapping("/employees/{id}")
	@PermissionCheck(workspace = {Workspace.EMPLOYEE},delete = true)
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id " + employeeId));
		this.employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);

		return response;
	}

}
