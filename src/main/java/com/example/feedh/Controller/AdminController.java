package com.example.feedh.Controller;

import com.example.feedh.ApiResponse.ApiResponse;
import com.example.feedh.Model.Admin;
import com.example.feedh.Service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admins")
public class AdminController {
private final AdminService adminService;

    @GetMapping("/get")
    public ResponseEntity getAllAdmins(){
        return ResponseEntity.status(200).body(adminService.getAllAdmin());
    }

    @PostMapping("/add")
    public ResponseEntity addAdmin(@RequestBody @Valid Admin admin) {
        adminService.addAdmin(admin);
        return ResponseEntity.status(200).body(new ApiResponse("Admin has been added successfully"));
    }

    @PutMapping("/update/{adminId}")
    public ResponseEntity updateAdmin(@PathVariable Integer adminId, @RequestBody @Valid Admin admin) {
        adminService.updateAdmin(adminId, admin);
        return ResponseEntity.status(200).body(new ApiResponse("Admin with ID: " + adminId + " has been updated successfully"));
    }

    @DeleteMapping("/delete/{adminId}")
    public ResponseEntity deleteAdmin(@PathVariable Integer adminId) {
        adminService.deleteAdmin(adminId);
        return ResponseEntity.status(200).body(new ApiResponse("Admin with ID: " + adminId + " has been deleted successfully"));
    }

    @PutMapping("/approve/{eventId}/{adminId}")
    public ResponseEntity approveParticipation(@PathVariable Integer eventId, @PathVariable Integer adminId) {
        adminService.approveParticipation(eventId, adminId);
        return ResponseEntity.status(200).body(new ApiResponse("Participation approved successfully"));
    }

    @PutMapping("/reject/{eventId}/{adminId}")
    public ResponseEntity rejectParticipation(@PathVariable Integer eventId, @PathVariable Integer adminId) {
        adminService.rejectParticipation(eventId, adminId);
        return ResponseEntity.status(200).body(new ApiResponse("Participation rejected successfully"));
    }
}
