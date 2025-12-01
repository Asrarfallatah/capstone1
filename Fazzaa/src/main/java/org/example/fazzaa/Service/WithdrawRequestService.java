package org.example.fazzaa.Service;

import lombok.RequiredArgsConstructor;
import org.example.fazzaa.Model.EventRequest;
import org.example.fazzaa.Model.WithdrawRequest;
import org.example.fazzaa.Repository.EventRequestRepository;
import org.example.fazzaa.Repository.WithdrawRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WithdrawRequestService {

    // connnect to the datbase
    private final WithdrawRequestRepository withdrawRequestRepository;
    private final EventRequestRepository eventRequestRepository;

    // get all withdraw requests from the database
    public List<WithdrawRequest> getAllWithdrawRequests() {
        return withdrawRequestRepository.findAll();
    }

    // add a withdraw request to the database
    public boolean addWithdrawRequest(WithdrawRequest withdrawRequest) {

        // check if  if the event request exists
        EventRequest request = eventRequestRepository.findEventRequestByRequestId(withdrawRequest.getRequestId());
        if (request == null) {
            return false;
        }

        // save the withdraw request to the database
        withdrawRequestRepository.save(withdrawRequest);

        // change request status to WITHDRAWN
        request.setRequestStatus("WITHDRAWN");
        eventRequestRepository.save(request);

        return true;
    }

    // update withdraw request in the database
    public boolean updateWithdrawRequest(Integer withdrawId, WithdrawRequest withdrawRequest) {

        WithdrawRequest existingWithdrawRequest = withdrawRequestRepository.findWithdrawRequestByWithdrawId(withdrawId);
        if (existingWithdrawRequest == null) {
            return false ;
        }

        return true;
    }


    // delete withdraw request from the database
    public boolean deleteWithdrawRequest(Integer withdrawId) {

        WithdrawRequest withdrawRequest = withdrawRequestRepository.findWithdrawRequestByWithdrawId(withdrawId);
        if (withdrawRequest == null) {
            return false ;
        }


        return true;
    }

    ///  extra endpoints

    //17-  get withdraw requests by client
    public List<WithdrawRequest> getWithdrawsByClient(Integer clientId) {
        return withdrawRequestRepository.findWithdrawRequestsByClientId(clientId);
    }

    //18-  get withdraw requests by request
    public List<WithdrawRequest> getWithdrawsByRequest(Integer requestId) {
        return withdrawRequestRepository.findWithdrawRequestsByRequestId(requestId);
    }
}
