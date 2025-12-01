package org.example.fazzaa.Service;

import lombok.RequiredArgsConstructor;
import org.example.fazzaa.Model.EventRequest;
import org.example.fazzaa.Model.Feedback;
import org.example.fazzaa.Repository.EventRequestRepository;
import org.example.fazzaa.Repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    // connect to the database
    private final FeedbackRepository feedbackRepository;
    private final EventRequestRepository eventRequestRepository;
    private final AiService aiService;

    // get all feedbacks from the database
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    // add feedback to the database
    public int addFeedback(Feedback feedback) {

        // check if the request id exists
        EventRequest request = eventRequestRepository.findEventRequestByRequestId(feedback.getRequestId());
        if (request == null) {
            return 1;
        }

        // check if the client is the owner of the request
        if (!request.getClientId().equals(feedback.getClientId())) {
            return 2;
        }

        // check if the client has already made feedback for this request
        List<Feedback> existingFeedbacks = feedbackRepository.findFeedbacksByRequestId(feedback.getRequestId());
        for (Feedback f : existingFeedbacks) {
            if (f.getClientId().equals(feedback.getClientId())) {
                return 3;
            }
        }

        feedback.setFeedbackDate(LocalDate.now());

        // add feedback
        feedbackRepository.save(feedback);
        return 4;
    }

    // update feedback within the database
    public boolean updateFeedback(Integer feedbackId, Feedback feedback) {

        // check if the feedback exists
        Feedback existingFeedback = feedbackRepository.findFeedbackByFeedbackId(feedbackId);
        if (existingFeedback == null) {
            return false;
        }

        // update
        existingFeedback.setRating(feedback.getRating());
        existingFeedback.setComment(feedback.getComment());

        // save
        feedbackRepository.save(existingFeedback);
        return true;
    }


    // delete feedback from the database
    public boolean deleteFeedback(Integer feedbackId) {

        // check if the feedback exists
        Feedback feedback = feedbackRepository.findFeedbackByFeedbackId(feedbackId);
        if (feedback == null) {
            return false;
        }

        // delete
        feedbackRepository.delete(feedback);
        return true;
    }

    /// extra endpoints

    // 19 get all feedbacks for a specific request
    public List<Feedback> getFeedbacksByRequest(Integer requestId) {
        return feedbackRepository.findFeedbacksByRequestId(requestId);
    }

    // 20- summarize feedbacks for a spesific request with Ai
    public String getAiFeedbackSummary(Integer requestId) {

        List<Feedback> feedbacks = feedbackRepository.findFeedbacksByRequestId(requestId);
        if (feedbacks.isEmpty()) {
            return "no feedback found for this request !";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Summarize these feedback comments and ratings:\n");
        for (Feedback f : feedbacks) {
            sb.append("Rating: ").append(f.getRating())
                    .append(" Comment: ").append(f.getComment())
                    .append("\n");
        }

        return aiService.generateResponse(sb.toString());
    }


    //22 detect bad feedback and suggest improvements with Ai
    public String getAiNegativeFeedbackAnalysis() {

        List<Feedback> feedbacks = feedbackRepository.findAll();

        if (feedbacks.isEmpty()) {
            return "no feedback in the system yet, nothing to analyze !";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Analyze this feedback and detect negative issues:\n\n");

        for (Feedback f : feedbacks) {
            sb.append("Rating: ").append(f.getRating()).append("\n").append("Comment: ").append(f.getComment()).append("\n\n");
        }

        sb.append("Please detect negative patterns and recommend improvements ").append("for the company management and representative teams.");

        return aiService.generateResponse(sb.toString());
    }

}
