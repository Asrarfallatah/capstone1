package org.example.fazzaa.Service;

import lombok.RequiredArgsConstructor;
import org.example.fazzaa.Model.Management;
import org.example.fazzaa.Repository.ManagementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagementService {

    // connect to the
    private final ManagementRepository managementRepository;

    // get all managements information from the database
    public List<Management> getAllManagements() {
        return managementRepository.findAll();
    }

    // add management information to the database
    public void addManagement(Management management) {

        managementRepository.save(management);

    }

    // update management information in the database
    public boolean updateManagement(Integer managementId, Management management) {

        // check if management exists
        Management oldManagement = managementRepository.findManagementByManagementId(managementId);
        if (oldManagement == null) {
            return false;
        }

        oldManagement.setManagementType(management.getManagementType());

        managementRepository.save(oldManagement);
        return true;
    }

    // delete
    public boolean deleteManagement(Integer managementId) {

        Management management = managementRepository.findManagementByManagementId(managementId);
        if (management == null) {
            return false;
        }

        managementRepository.delete(management);
        return true;
    }

    /// extra endpoint
    // 10 -  get management by type
    public Management getManagementByType(String type) {
        return managementRepository.findManagementByManagementTypeIgnoreCase(type);
    }
}
