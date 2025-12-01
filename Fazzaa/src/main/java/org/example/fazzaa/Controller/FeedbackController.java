package org.example.fazzaa.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.fazzaa.ApiResponse.ApiResponse;
import org.example.fazzaa.Model.Feedback;
import org.example.fazzaa.Service.FeedbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    // get
    @GetMapping("/get")
    public ResponseEntity<?> getAllFeedback() {

        if (feedbackService.getAllFeedbacks().isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("no feedbacks is found yet in the database to show their information  !"));
        }

        return ResponseEntity.status(200).body(feedbackService.getAllFeedbacks());
    }

    // add
    @PostMapping("/add/{clientId}")
    public ResponseEntity<?> addFeedback(@PathVariable Integer clientId , @Valid @RequestBody Feedback feedback, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        int  result = feedbackService.addFeedback(feedback);

        if (result == 1) {
            return ResponseEntity.status(400).body(new ApiResponse("error no request with this id exists !"));
        }else if (result == 2) {
            return ResponseEntity.status(400).body(new ApiResponse("error you are not the owner of this request to make the feedback for this request !"));
        }else if (result == 3) {
            return ResponseEntity.status(400).body(new ApiResponse("error you have already made feedback for this request !"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("feedback has been added successfully to the database !"));
    }

    // update
    @PutMapping("/update/{feedbackId}")
    public ResponseEntity<?> updateFeedback(@PathVariable Integer feedbackId , @Valid @RequestBody Feedback feedback, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        boolean isUpdated = feedbackService.updateFeedback(feedbackId, feedback);

        if (!isUpdated) {
            return ResponseEntity.status(400).body(new ApiResponse("error no feedback with this id exists to update it !"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("feedback updated successfully !"));
    }

    // delete
    @DeleteMapping("/delete/{feedbackId}")
    public ResponseEntity<?> deleteFeedback(@PathVariable Integer feedbackId) {

        boolean result = feedbackService.deleteFeedback(feedbackId);

        if (!result) {
            return ResponseEntity.status(400).body(new ApiResponse("error no feedback with this id exists to delete it !"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("feedback deleted successfully !"));
    }

    /// extra endpoints

    // 19 get all feedbacks for a specific request
    @GetMapping("/request/{requestId}")
    public ResponseEntity<?> getFeedbacksByRequest(@PathVariable Integer requestId) {

        if (feedbackService.getFeedbacksByRequest(requestId).isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("no feedbacks is found for this request id in the database to show their information  !"));
        }

        return ResponseEntity.status(200).body(feedbackService.getFeedbacksByRequest(requestId));
    }

    // 20- summarize feedbacks for a spesific request with Ai
    @GetMapping("/ai-summary/{requestId}")
    public ResponseEntity<?> getAiFeedbackSummary(@PathVariable Integer requestId) {

        if (feedbackService.getFeedbacksByRequest(requestId).isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("no feedbacks is found for this request id in the database to generate AI summary  !"));
        }

        String response = feedbackService.getAiFeedbackSummary(requestId);
        return ResponseEntity.status(200).body(response);
    }

    //22 detect bad feedback and suggest improvements with Ai
    @GetMapping("/ai-negative-analysis")
    public ResponseEntity<?> getAiNegativeFeedbackAnalysis() {

        if (feedbackService.getAllFeedbacks().isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("no feedbacks is found in the database to generate AI negative feedback analysis  !"));
        }

        return ResponseEntity.status(200).body(feedbackService.getAiNegativeFeedbackAnalysis());
    }

}
