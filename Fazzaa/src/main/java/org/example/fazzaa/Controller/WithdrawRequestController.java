package org.example.fazzaa.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.fazzaa.ApiResponse.ApiResponse;
import org.example.fazzaa.Model.WithdrawRequest;
import org.example.fazzaa.Service.WithdrawRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/withdraw")
@RequiredArgsConstructor
public class WithdrawRequestController {

    private final WithdrawRequestService withdrawRequestService;

    // get
    @GetMapping("/get")
    public ResponseEntity<?> getAllWithdrawRequests() {

        if (withdrawRequestService.getAllWithdrawRequests().isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("error no withdraw requests found in the database yet !"));
        }

        return ResponseEntity.status(200).body(withdrawRequestService.getAllWithdrawRequests());
    }

    // add
    @PostMapping("/add")
    public ResponseEntity<?> addWithdrawRequest(@Valid @RequestBody WithdrawRequest withdrawRequest, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.status(400)
                    .body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        boolean result = withdrawRequestService.addWithdrawRequest(withdrawRequest);

        if (!result) {
            return ResponseEntity.status(400).body(new ApiResponse("error no event request wiht that id is found in the database to withdraw it !"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("withdraw request added has been successfully !"));
    }

    // update
    @PutMapping("/update/{withdrawId}")
    public ResponseEntity<?> updateWithdrawRequest(@PathVariable Integer withdrawId, @Valid @RequestBody WithdrawRequest withdrawRequest, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        boolean result = withdrawRequestService.updateWithdrawRequest(withdrawId, withdrawRequest);
        if (!result) {
            return ResponseEntity.status(400).body(new ApiResponse("error withdraw request not found and withdraw request can not be updated !"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("withdraw request can not be updated !"));
    }




    // delete
    @DeleteMapping("/delete/{withdrawId}")
    public ResponseEntity<?> deleteWithdrawRequest(@PathVariable Integer withdrawId) {

        boolean result = withdrawRequestService.deleteWithdrawRequest(withdrawId);

        if (!result) {
            return ResponseEntity.status(400).body(new ApiResponse("error withdraw request not found withdraw request can not be deleted !"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("withdraw request can not be deleted !"));
    }

    ///  extra endpoints

    //17-  get withdraw requests by client
    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getWithdrawsByClient(@PathVariable Integer clientId) {

        if (withdrawRequestService.getWithdrawsByClient(clientId).isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("error no withdraw requests found for this client id !"));
        }

        return ResponseEntity.status(200).body(withdrawRequestService.getWithdrawsByClient(clientId));
    }

    //18-  get withdraw requests by request
    @GetMapping("/request/{requestId}")
    public ResponseEntity<?> getWithdrawsByRequest(@PathVariable Integer requestId) {

        if (withdrawRequestService.getWithdrawsByRequest(requestId).isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("error no withdraw requests found for this request id !"));
        }

        return ResponseEntity.status(200).body(withdrawRequestService.getWithdrawsByRequest(requestId));
    }
}
