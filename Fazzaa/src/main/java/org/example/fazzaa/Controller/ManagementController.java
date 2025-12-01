package org.example.fazzaa.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.fazzaa.ApiResponse.ApiResponse;
import org.example.fazzaa.Model.Management;
import org.example.fazzaa.Service.ManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/management")
@RequiredArgsConstructor
public class ManagementController {

    private final ManagementService managementService;

    //get
    @GetMapping("/get")
    public ResponseEntity<?> getAllManagements() {
        return ResponseEntity.status(200).body(managementService.getAllManagements());
    }

    // add
    @PostMapping("/add")
    public ResponseEntity<?> addManagement(@Valid @RequestBody Management management, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.status(400)
                    .body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        managementService.addManagement(management);
        return ResponseEntity.status(200).body(new ApiResponse("management added successfully !"));
    }

    // update
    @PutMapping("/update/{managementId}")
    public ResponseEntity<?> updateManagement(@PathVariable Integer managementId, @Valid @RequestBody Management management, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        boolean result = managementService.updateManagement(managementId, management);

        if (!result ) {
            return ResponseEntity.status(400).body(new ApiResponse("error no mangement with this id found in the database to update it !"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("management updated successfully !"));
    }

    // delete
    @DeleteMapping("/delete/{managementId}")
    public ResponseEntity<?> deleteManagement(@PathVariable Integer managementId) {

        boolean result = managementService.deleteManagement(managementId);

        if (!result) {
            return ResponseEntity.status(400).body(new ApiResponse("error no mangement with this id found in the database to delete it !"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("management deleted successfully !"));
    }

    /// extra endpoint
    // 10 -  get management by type
    @GetMapping("/type/{type}")
    public ResponseEntity<?> getManagementByType(@PathVariable String type) {

        if (managementService.getManagementByType(type) == null) {
            return ResponseEntity.status(400).body(new ApiResponse("error no management with this type is found in the database !"));
        }

        return ResponseEntity.status(200).body(managementService.getManagementByType(type));
    }
}
