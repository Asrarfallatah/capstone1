package org.example.fazzaa.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.fazzaa.ApiResponse.ApiResponse;
import org.example.fazzaa.Model.RepresentativeTeam;
import org.example.fazzaa.Service.RepresentativeTeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rep-team")
@RequiredArgsConstructor
public class RepresentativeTeamController {

    private final RepresentativeTeamService representativeTeamService;

    //get
    @GetMapping("/get")
    public ResponseEntity<?> getAllTeams() {

        if (representativeTeamService.getAllRepresentativeTeams().isEmpty()) {
            return ResponseEntity.status(200).body(new ApiResponse("no representative teams is found in the database yet to show their information !"));
        }

        return ResponseEntity.status(200).body(representativeTeamService.getAllRepresentativeTeams());
    }

    //add
    @PostMapping("/add")
    public ResponseEntity<?> addTeam(@Valid @RequestBody RepresentativeTeam team, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.status(400)
                    .body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        representativeTeamService.addRepresentativeTeam(team);
        return ResponseEntity.status(200).body(new ApiResponse("representative team added successfully !"));
    }

    //update
    @PutMapping("/update/{teamId}")
    public ResponseEntity<?> updateTeam(@PathVariable Integer teamId, @Valid @RequestBody RepresentativeTeam team, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        boolean result = representativeTeamService.updateRepresentativeTeam(teamId, team);
        if (!result) {
            return ResponseEntity.status(400).body(new ApiResponse("error no representative team with that information is found !"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("representative team has been updated successfully !"));
    }

    // delete
    @DeleteMapping("/delete/{teamId}")
    public ResponseEntity<?> deleteTeam(@PathVariable Integer teamId) {

        boolean result = representativeTeamService.deleteRepresentativeTeam(teamId);

        if (!result) {
            return ResponseEntity.status(400).body(new ApiResponse("error no representative team with that information is found !"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("representative team has been deleted successfully !"));
    }

    ///  extra endpoints

    // 1 4- get team by name
    @GetMapping("/name/{teamName}")
    public ResponseEntity<?> getTeamByName(@PathVariable String teamName) {

        if (representativeTeamService.getTeamByName(teamName) == null) {
            return ResponseEntity.status(400).body(new ApiResponse("error no representative team with that name is found !"));
        }

        return ResponseEntity.status(200).body(representativeTeamService.getTeamByName(teamName));
    }
}
