package com.ferri.expensestrackerapi.controller;

import com.ferri.expensestrackerapi.aspect.PermissionCheck;
import com.ferri.expensestrackerapi.security.utils.Workspace;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content. bro";
	}
	
	@GetMapping("/user")
	@PermissionCheck(workspace = {Workspace.INDEX},read = true)
	public String userAccess() {
		return "INDEX READ.";
	}

	@PostMapping("/user")
	@PermissionCheck(workspace = {Workspace.INDEX},write = true)
	public String moderatorAccess() {
		return "INDEX WRITE.";
	}

	@DeleteMapping("/user")
	@PermissionCheck(workspace = {Workspace.INDEX},delete = true)
	public String adminAccess() {
		return "INDEX DELETE.";
	}
}
