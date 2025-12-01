package org.example.fazzaa.Service;

import lombok.RequiredArgsConstructor;
import org.example.fazzaa.Model.Client;
import org.example.fazzaa.Model.EventRequest;
import org.example.fazzaa.Repository.ClientRepository;
import org.example.fazzaa.Repository.EventRequestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventRequestService {


    // connect to database
    private final EventRequestRepository eventRequestRepository;
    private final ClientRepository clientRepository;
    private final AiService aiService;


    // get all event requests from database
    public List<EventRequest> getAllEventRequests() {
        return eventRequestRepository.findAll();
    }

    // add event request to the database
    public int addEventRequest(EventRequest eventRequest) {

        // check if client exists
        Client client = clientRepository.findClientByClientId(eventRequest.getClientId());
        if (client == null) {
            return 1;
        }

        // check if event date is in the past
        if (eventRequest.getEventDate().isBefore(LocalDate.now())) {
            return 2;
        }

        // set status to PENDING by default
        eventRequest.setRequestStatus("PENDING");

        // save
        eventRequestRepository.save(eventRequest);
        return 3;
    }

    // update event request within the database
    public int updateEventRequest(Integer requestId, EventRequest eventRequest) {

        // check if request exists
        EventRequest oldRequest = eventRequestRepository.findEventRequestByRequestId(requestId);
        if (oldRequest == null) {
            return 1;
        }

        // check if client exists
        Client client = clientRepository.findClientByClientId(eventRequest.getClientId());
        if (client == null) {
            return 2;
        }

        // update
        oldRequest.setEventType(eventRequest.getEventType());
        oldRequest.setEventDate(eventRequest.getEventDate());
        oldRequest.setTheme(eventRequest.getTheme());
        oldRequest.setBudget(eventRequest.getBudget());
        oldRequest.setPlace(eventRequest.getPlace());
        oldRequest.setEstimatedGuests(eventRequest.getEstimatedGuests());
        oldRequest.setDescription(eventRequest.getDescription());
        oldRequest.setClientId(eventRequest.getClientId());

        // save
        eventRequestRepository.save(oldRequest);
        return 3;
    }

    // delete event request from the database
    public int deleteEventRequest(Integer requestId) {

        // check if request exists
        EventRequest request = eventRequestRepository.findEventRequestByRequestId(requestId);
        if (request == null) {
            return 2;
        }

        // delete
        eventRequestRepository.delete(request);
        return 1;
    }

    ///  Extra endpoint

    //4- get all pending requests
    public List<EventRequest> getPendingRequests() {
        return eventRequestRepository.findEventRequestsByRequestStatusIgnoreCase("PENDING");
    }

    //5- get requests by status (APPROVED / REJECTED / COMPLETED / WITHDRAWN)
    public List<EventRequest> getRequestsByStatus(String status) {
        return eventRequestRepository.findEventRequestsByRequestStatusIgnoreCase(status);
    }

    //6- get requests between two dates
    public List<EventRequest> getRequestsByDateRange(LocalDate startDate, LocalDate endDate) {
        return eventRequestRepository.findEventRequestsByEventDateBetween(startDate, endDate);
    }

    //7- get requests in a budget range
    public List<EventRequest> getRequestsByBudgetRange(Double minBudget, Double maxBudget) {
        return eventRequestRepository.findEventRequestsByBudgetBetween(minBudget, maxBudget);
    }

    //8- change request status (used by management workflow)
    public boolean changeRequestStatus(Integer requestId, String newStatus) {

        EventRequest request = eventRequestRepository.findEventRequestByRequestId(requestId);
        if (request == null) {
            return false;
        }

        request.setRequestStatus(newStatus);
        eventRequestRepository.save(request);
        return true;
    }

    //9- get suggest event idea or plan with Ai
    public String getAiEventPlanSuggestion(Integer requestId) {

        EventRequest request = eventRequestRepository.findEventRequestByRequestId(requestId);
        if (request == null) {
            return "error : request not found !";
        }

        String prompt =
                "Suggest a creative event plan based on this data:\n" +
                        "Event type: " + request.getEventType() + "\n" +
                        "Theme: " + request.getTheme() + "\n" +
                        "Budget: " + request.getBudget() + "\n" +
                        "Place: " + request.getPlace() + "\n" +
                        "Estimated guests: " + request.getEstimatedGuests() + "\n" +
                        "Description: " + request.getDescription();

        return aiService.generateResponse(prompt);
    }


    // 21  get Event trend Suggestion from Ai
    public String getAiEventTrends() {

        List<EventRequest> requests = eventRequestRepository.findAll();

        if (requests.isEmpty()) {
            return "no event requests yet, no trends available !";
        }


        Map<String, Integer> countMap = new HashMap<>();
        for (EventRequest r : requests) {
            countMap.put(r.getEventType(), countMap.getOrDefault(r.getEventType(), 0) + 1);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Analyze these event trends:\n");
        for (String type : countMap.keySet()) {
            sb.append("Event: ").append(type).append(" | Count: ").append(countMap.get(type)).append("\n");
        }

        sb.append("\nSuggest creative ways to improve these events, ").append("increase fun, and boost client satisfaction.");

        return aiService.generateResponse(sb.toString());
    }



}
