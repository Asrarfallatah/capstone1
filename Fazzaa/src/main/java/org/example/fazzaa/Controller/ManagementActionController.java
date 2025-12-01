package org.example.fazzaa.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.fazzaa.ApiResponse.ApiResponse;
import org.example.fazzaa.Model.ManagementAction;
import org.example.fazzaa.Service.ManagementActionService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/management-action")
@RequiredArgsConstructor
public class ManagementActionController {

    private final ManagementActionService managementActionService;


    // get
    @GetMapping("/get")
    public ResponseEntity<?> getAllActions() {

        if (managementActionService.getAllManagementActions().isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("no management actions found !"));
        }

        return ResponseEntity.status(200).body(managementActionService.getAllManagementActions());
    }

    // add
    @PostMapping("/add")
    public ResponseEntity<?> addAction(@Valid @RequestBody ManagementAction action, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.status(400)
                    .body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        int result = managementActionService.addManagementAction(action);

        if (result == 1) {
            return ResponseEntity.status(400).body(new ApiResponse("error no action with such id found to update it  !"));
        }
        if (result == 2) {
            return ResponseEntity.status(400).body(new ApiResponse("error no management with that information is found in the database !"));
        }
        if (result == 3) {
            return ResponseEntity.status(400).body(new ApiResponse("error no request eith that information is found in the database  !"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("management action added successfully !"));
    }

    // delete
    @DeleteMapping("/delete/{actionId}")
    public ResponseEntity<?> deleteAction(@PathVariable Integer actionId) {

        boolean result = managementActionService.deleteManagementAction(actionId);

        if (!result) {
            return ResponseEntity.status(400).body(new ApiResponse("error no action with such id found to delete it !"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("management action deleted successfully !"));
    }

    /// extra endpoint


    //11- get all actions for a specific request
    @GetMapping("/request/{requestId}")
    public ResponseEntity<?> getActionsForRequest(@PathVariable Integer requestId) {

     if (managementActionService.getActionsForRequest(requestId).isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("no management actions found for this request !"));
        }

        return ResponseEntity.status(200).body(managementActionService.getActionsForRequest(requestId));
    }

    //12- get all actions done by a specific management
    @GetMapping("/management/{managementId}")
    public ResponseEntity<?> getActionsByManagement(@PathVariable Integer managementId) {

        if (managementActionService.getActionsByManagement(managementId).isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("no management actions found for this management !"));
        }

        return ResponseEntity.status(200).body(managementActionService.getActionsByManagement(managementId));
    }

    // 13-  APPROVE / REJECT
    @PostMapping("/decision/{managementId}/{requestId}")
    public ResponseEntity<?> approveOrReject(@PathVariable Integer managementId, @PathVariable Integer requestId, @RequestParam String actionType, @RequestParam(required = false) String statement) {

        int result = managementActionService.approveOrRejectRequest(managementId, requestId, actionType, statement);

        if (result == 2) {
            return ResponseEntity.status(400).body(new ApiResponse("error management not found !"));
        }
        if (result == 3) {
            return ResponseEntity.status(400).body(new ApiResponse("error request not found !"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("action performed successfully !"));
    }
}
