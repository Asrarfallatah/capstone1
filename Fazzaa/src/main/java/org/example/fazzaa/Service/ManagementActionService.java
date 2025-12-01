package org.example.fazzaa.Service;

import lombok.RequiredArgsConstructor;
import org.example.fazzaa.Model.Management;
import org.example.fazzaa.Model.ManagementAction;
import org.example.fazzaa.Model.EventRequest;
import org.example.fazzaa.Repository.EventRequestRepository;
import org.example.fazzaa.Repository.ManagementActionRepository;
import org.example.fazzaa.Repository.ManagementRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagementActionService {

    // connect to the database
    private final ManagementActionRepository managementActionRepository;
    private final ManagementRepository managementRepository;
    private final EventRequestRepository eventRequestRepository;

    // get all management actions from database
    public List<ManagementAction> getAllManagementActions() {
        return managementActionRepository.findAll();
    }

    // add management action to database
    public int addManagementAction(ManagementAction action) {

        // check if management exists
        Management management = managementRepository.findManagementByManagementId(action.getManagementId());
        if (management == null) {
            return 2;
        }

        // check if request exists
        EventRequest request = eventRequestRepository.findEventRequestByRequestId(action.getRequestId());
        if (request == null) {
            return 3;
        }


        action.setActionDate(LocalDateTime.now());

        // add
        managementActionRepository.save(action);
        return 1;
    }

    // update management action in database
    public int updateManagementAction(Integer actionId, ManagementAction action) {

        // check if action exists
        ManagementAction existingAction = managementActionRepository.findManagementActionByActionId(actionId);
        if (existingAction == null) {
            return 1;
        }

        // check if management exists
        Management management = managementRepository.findManagementByManagementId(action.getManagementId());
        if (management == null) {
            return 2;
        }

        // check if request exists
        EventRequest request = eventRequestRepository.findEventRequestByRequestId(action.getRequestId());
        if (request == null) {
            return 3;
        }

        // update
        existingAction.setManagementId(action.getManagementId());
        existingAction.setRequestId(action.getRequestId());
        existingAction.setActionType(action.getActionType());
        existingAction.setStatement(action.getStatement());
        existingAction.setActionDate(LocalDateTime.now());

        // save
        managementActionRepository.save(existingAction);
        return 4;
    }



    // delete management action from database
    public boolean deleteManagementAction(Integer actionId) {

        // check if action exists
        ManagementAction action = managementActionRepository.findManagementActionByActionId(actionId);
        if (action == null) {
            return false;
        }

        managementActionRepository.delete(action);
        return true;
    }

    /// extra endpoint


    //11- get all actions for a specific request
    public List<ManagementAction> getActionsForRequest(Integer requestId) {
        return managementActionRepository.findManagementActionsByRequestId(requestId);
    }

    //12- get all actions done by a specific management
    public List<ManagementAction> getActionsByManagement(Integer managementId) {
        return managementActionRepository.findManagementActionsByManagementId(managementId);
    }

    // 13-  APPROVE / REJECT
    public int approveOrRejectRequest(Integer managementId, Integer requestId, String actionType, String statement) {

        // check if management exists
        Management management = managementRepository.findManagementByManagementId(managementId);
        if (management == null) {
            return 2;
        }

        //check if request exists
        EventRequest request = eventRequestRepository.findEventRequestByRequestId(requestId);
        if (request == null) {
            return 3;
        }

        // make action
        ManagementAction action = new ManagementAction();
        action.setManagementId(managementId);
        action.setRequestId(requestId);
        action.setActionType(actionType);
        action.setStatement(statement);
        action.setActionDate(LocalDateTime.now());

        managementActionRepository.save(action);

        // change request status
        if (actionType.equalsIgnoreCase("APPROVE")) {
            request.setRequestStatus("APPROVED");
        } else if (actionType.equalsIgnoreCase("REJECT")) {
            request.setRequestStatus("REJECTED");
        }

        eventRequestRepository.save(request);

        return 1;
    }
}
