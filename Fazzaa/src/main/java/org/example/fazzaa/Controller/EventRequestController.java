package org.example.fazzaa.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.fazzaa.ApiResponse.ApiResponse;
import org.example.fazzaa.Model.EventRequest;
import org.example.fazzaa.Service.EventRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/event-request")
@RequiredArgsConstructor
public class EventRequestController {


    private final EventRequestService eventRequestService;

    // get
    @GetMapping("/get")
    public ResponseEntity<?> getAllRequests() {

        if (eventRequestService.getAllEventRequests().isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("error no event requests is found yet in the database to show their information !"));
        }

        return ResponseEntity.status(200).body(eventRequestService.getAllEventRequests());
    }

    // add
    @PostMapping("/add")
    public ResponseEntity<?> addRequest(@Valid @RequestBody EventRequest request, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        int result = eventRequestService.addEventRequest(request);

        if (result == 1) {
            return ResponseEntity.status(400).body(new ApiResponse("error no client found with the that ID in the database!"));
        }
        if (result == 2) {
            return ResponseEntity.status(400).body(new ApiResponse("error event date can not be in the past !"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("event request added successfully !"));
    }

    // update
    @PutMapping("/update/{requestId}")
    public ResponseEntity<?> updateRequest(@PathVariable Integer requestId, @Valid @RequestBody EventRequest request, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        int result = eventRequestService.updateEventRequest(requestId, request);
        if (result == 1) {
            return ResponseEntity.status(400).body(new ApiResponse("error no event request found with the that ID in the database!"));
        }else if (result == 2) {
            return ResponseEntity.status(400).body(new ApiResponse("error no client found with the that ID in the database!"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("event request has been updated in the database successfully !"));
    }

    // delete
    @DeleteMapping("/delete/{requestId}")
    public ResponseEntity<?> deleteRequest(@PathVariable Integer requestId) {

        int result = eventRequestService.deleteEventRequest(requestId);

        if (result == 2) {
            return ResponseEntity.status(400).body(new ApiResponse("error event request not found !"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("event request deleted successfully !"));
    }

    ///  Extra endpoint
    ///


    // 4- get pending requests
    @GetMapping("/get/pending")
    public ResponseEntity<?> getPendingRequests() {

        if (eventRequestService.getPendingRequests().isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("error no pending event requests is found in the database to show their information !"));
        }

        return ResponseEntity.status(200).body(eventRequestService.getPendingRequests());
    }

    // 5- get by status
    @GetMapping("/get/status/{status}")
    public ResponseEntity<?> getRequestsByStatus(@PathVariable String status) {

        if (eventRequestService.getRequestsByStatus(status).isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("error no event requests with status "+status+" is found in the database to show their information !"));
        }

        return ResponseEntity.status(200).body(eventRequestService.getRequestsByStatus(status));
    }

    //6- get by date range
    @GetMapping("/get/date-range/{start}/{end}")
    public ResponseEntity<?> getByDateRange(@PathVariable String start, @PathVariable String end) {

        if (eventRequestService.getRequestsByDateRange(LocalDate.parse(start), LocalDate.parse(end)).isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("error no event requests between dates "+start+" and "+end+" is found in the database to show their information !"));
        }

        return ResponseEntity.status(200).body(eventRequestService.getRequestsByDateRange(LocalDate.parse(start), LocalDate.parse(end)));
    }

    //7- get by budget range
    @GetMapping("/get/budget/{min}/{max}")
    public ResponseEntity<?> getByBudgetRange(@PathVariable Double min, @PathVariable Double max) {

        if (eventRequestService.getRequestsByBudgetRange(min, max).isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("error no event requests in the budget range "+min+" to "+max+" is found in the database to show their information !"));
        }

        return ResponseEntity.status(200).body(eventRequestService.getRequestsByBudgetRange(min, max));
    }

    //8- change status
    @PutMapping("/status/{requestId}/{newStatus}")
    public ResponseEntity<?> changeStatus(@PathVariable Integer requestId, @PathVariable String newStatus) {

        boolean result = eventRequestService.changeRequestStatus(requestId, newStatus);

        if (!result ) {
            return ResponseEntity.status(400).body(new ApiResponse("error request not found !"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("status changed successfully !"));
    }

    // 9- event plan suggestion from Ai
    @GetMapping("/ai-suggestion/{requestId}")
    public ResponseEntity<?> getAiSuggestion(@PathVariable Integer requestId) {

        String response = eventRequestService.getAiEventPlanSuggestion(requestId);

        if (response.startsWith("error")) {
            return ResponseEntity.status(400).body(new ApiResponse(response));
        }

        return ResponseEntity.status(200).body(response);
    }

    // // 21- get event trends among clients
    @GetMapping("/ai-trends")
    public ResponseEntity<?> getEventTrends() {

        if (eventRequestService.getAiEventTrends().isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("error no event trends data is found to show !"));
        }

        return ResponseEntity.status(200).body(eventRequestService.getAiEventTrends());
    }
}
