package org.example.fazzaa.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.fazzaa.ApiResponse.ApiResponse;
import org.example.fazzaa.Model.AssignedRepresentative;
import org.example.fazzaa.Service.AssignedRepresentativeService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/assigned-rep")
@RequiredArgsConstructor
public class AssignedRepresentativeController {

    private final AssignedRepresentativeService assignedRepresentativeService;

    // gets
    @GetMapping("/get")
    public ResponseEntity<?> getAllAssignRepresentatives() {

        if (assignedRepresentativeService.getAllAssignRepresentatives().isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("error there is no assigned representatives yet to show their information !"));
        }

        return ResponseEntity.status(200).body(assignedRepresentativeService.getAllAssignRepresentatives());
    }

    // add
    @PostMapping("/add")
    public ResponseEntity<?> addAssignment(@Valid @RequestBody AssignedRepresentative assigned, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.status(400)
                    .body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        int result = assignedRepresentativeService.addAssignedRepresentative(assigned);

        if (result == 1) {
            return ResponseEntity.status(400).body(new ApiResponse("error no event request with that id is found to assign a Representative!"));
        }
        if (result == 2) {
            return ResponseEntity.status(400).body(new ApiResponse("error no representative team with that id is found to assign!"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("representative assignment added successfully !"));
    }

    // update
    @PutMapping("/update/{assignedId}")
    public ResponseEntity<?> updateAssignment(@PathVariable Integer assignedId, @Valid @RequestBody AssignedRepresentative assignedRepresentative, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        int result = assignedRepresentativeService.updateAssignedRepresentative(assignedId, assignedRepresentative);
        if (result == 1) {
            return ResponseEntity.status(400).body(new ApiResponse("error no assignment with that id is found to update!"));
        }
        if (result == 2) {
            return ResponseEntity.status(400).body(new ApiResponse("error no event request with that id is found to assign a Representative!"));
        }
        if (result == 3) {
            return ResponseEntity.status(400).body(new ApiResponse("error no representative team with that id is found to assign!"));
        }


        return ResponseEntity.status(200).body(new ApiResponse("representative assignment information has been updated successfully !"));

    }

    // delete
    @DeleteMapping("/delete/{assignedId}")
    public ResponseEntity<?> deleteAssignment(@PathVariable Integer assignedId) {

        boolean result = assignedRepresentativeService.deleteAssignedRepresentative(assignedId);

        if (!result) {
            return ResponseEntity.status(400).body(new ApiResponse("error no assignment with that id is found to delete it !"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("representative assignment has been deleted successfully !"));
    }

    ///  extra endpoints

    //15- get all representative assignments for a specific request
    @GetMapping("/request/{requestId}")
    public ResponseEntity<?> getAssignmentsByRequest(@PathVariable Integer requestId) {

        if(assignedRepresentativeService.getAssignmentsByRequest(requestId).isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("error there is no assignments for the specified request id !"));
        }

        return ResponseEntity.status(200).body(assignedRepresentativeService.getAssignmentsByRequest(requestId));
    }

    // (16) get all assignments for a specific representative team
    @GetMapping("/team/{teamId}")
    public ResponseEntity<?> getAssignmentsByTeam(@PathVariable Integer teamId) {

        if (assignedRepresentativeService.getAssignmentsByTeam(teamId).isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("error there is no assignments for the specified team id !"));
        }

        return ResponseEntity.status(200).body(assignedRepresentativeService.getAssignmentsByTeam(teamId));
    }
}
