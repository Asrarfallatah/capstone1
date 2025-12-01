package org.example.fazzaa.Service;

import lombok.RequiredArgsConstructor;
import org.example.fazzaa.Model.AssignedRepresentative;
import org.example.fazzaa.Model.EventRequest;
import org.example.fazzaa.Model.RepresentativeTeam;
import org.example.fazzaa.Repository.AssignedRepresentativeRepository;
import org.example.fazzaa.Repository.EventRequestRepository;
import org.example.fazzaa.Repository.RepresentativeTeamRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignedRepresentativeService {


    // connect to the database
    private final AssignedRepresentativeRepository assignedRepresentativeRepository;
    private final EventRequestRepository eventRequestRepository;
    private final RepresentativeTeamRepository representativeTeamRepository;

    // get all assigned representatives
    public List<AssignedRepresentative> getAllAssignRepresentatives() {
        return assignedRepresentativeRepository.findAll();
    }

    // add an assigned representative to the database
    public int addAssignedRepresentative(AssignedRepresentative assignedRepresentative) {

        // check if the request
        EventRequest request = eventRequestRepository.findEventRequestByRequestId(assignedRepresentative.getRequestId());
        if (request == null) {
            return 1;
        }

        // check if the team exists
        RepresentativeTeam team = representativeTeamRepository.findRepresentativeTeamByRepTeamId(assignedRepresentative.getRepTeamId());
        if (team == null) {
            return 2;
        }

        // set the assigned date to today
        assignedRepresentative.setAssignedDate(LocalDate.now());

        //add
        assignedRepresentativeRepository.save(assignedRepresentative);
        return 3;
    }

    // update an assigned representative in the database
    public int updateAssignedRepresentative(Integer assignedId, AssignedRepresentative assignedRepresentative) {
        AssignedRepresentative existingAssignment = assignedRepresentativeRepository.findAssignedRepresentativeByAssignedId(assignedId);
        if (existingAssignment == null) {
            return 1;
        }

        // check if the request exists
        EventRequest request = eventRequestRepository.findEventRequestByRequestId(assignedRepresentative.getRequestId());
        if (request == null) {
            return 2;
        }

        // check if the team exists
        RepresentativeTeam team = representativeTeamRepository.findRepresentativeTeamByRepTeamId(assignedRepresentative.getRepTeamId());
        if (team == null) {
            return 3;
        }

        // update fields
        existingAssignment.setRequestId(assignedRepresentative.getRequestId());
        existingAssignment.setRepTeamId(assignedRepresentative.getRepTeamId());


        // save updated assignment
        assignedRepresentativeRepository.save(existingAssignment);
        return 4;
    }

    // delete an assigned representative from the database
    public boolean deleteAssignedRepresentative(Integer assignedId) {

        // check if the assignment exists
        AssignedRepresentative assignedRepresentative = assignedRepresentativeRepository.findAssignedRepresentativeByAssignedId(assignedId);
        if (assignedRepresentative == null) {
            return false;
        }

        assignedRepresentativeRepository.delete(assignedRepresentative);
        return true;
    }

    ///  extra endpoints

    //15- get all representative assignments for a specific request
    public List<AssignedRepresentative> getAssignmentsByRequest(Integer requestId) {
        return assignedRepresentativeRepository.findAssignedRepresentativesByRequestId(requestId);
    }

    // (16) get all assignments for a specific representative team
    public List<AssignedRepresentative> getAssignmentsByTeam(Integer repTeamId) {
        return assignedRepresentativeRepository.findAssignedRepresentativesByRepTeamId(repTeamId);
    }
}
