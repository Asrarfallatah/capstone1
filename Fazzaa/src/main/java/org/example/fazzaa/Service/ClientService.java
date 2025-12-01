package org.example.fazzaa.Service;

import lombok.RequiredArgsConstructor;
import org.example.fazzaa.Model.Client;
import org.example.fazzaa.Model.EventRequest;
import org.example.fazzaa.Model.Feedback;
import org.example.fazzaa.Repository.ClientRepository;
import org.example.fazzaa.Repository.EventRequestRepository;
import org.example.fazzaa.Repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    // connect to the database
    private final ClientRepository clientRepository;
    private final EventRequestRepository eventRequestRepository;
    private final FeedbackRepository feedbackRepository;

    // get all clients from the database
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // add a client to the database
    public boolean addClient(Client client) {

        // check if email is not used
        Client existingClientEmail = clientRepository.findClientByClientEmail(client.getClientEmail());
        if (existingClientEmail == null) {
            return false;
        }

        // add it
        clientRepository.save(client);
        return true;
    }

    // update client within the database
    public int updateClient(Integer clientId, Client client) {

        // check if client exist
        Client oldClient = clientRepository.findClientByClientId(clientId);
        if (oldClient == null) {
            return 1;
        }

        // check if email is not used by another client
        Client emailOwner = clientRepository.findClientByClientEmail(client.getClientEmail());
        if (emailOwner != null && !emailOwner.getClientId().equals(clientId)) {
            return 2;
        }

        // update
        oldClient.setClientName(client.getClientName());
        oldClient.setClientEmail(client.getClientEmail());
        oldClient.setClientPhone(client.getClientPhone());

        // save
        clientRepository.save(oldClient);
        return 3;
    }

    // delete client from the database
    public boolean deleteClient(Integer clientId) {

        // check if client exist
        Client client = clientRepository.findClientByClientId(clientId);
        if (client == null) {
            return false;
        }

        // delete
        clientRepository.delete(client);
        return true;
    }

    /// extra endpoints

    // 1- get client by specific email
    public Client getClientByEmail(String email) {
        return clientRepository.findClientByClientEmail(email);
    }

    // 2- count how many requests a specific client has made
    public int countClientRequests(Integer clientId) {
        List<EventRequest> requests = eventRequestRepository.findEventRequestsByClientId(clientId);
        return requests.size();
    }

    //3-  count how many feedbacks a specific client has written
    public int countClientFeedbacks(Integer clientId) {
        List<Feedback> feedbacks = feedbackRepository.findFeedbacksByClientId(clientId);
        return feedbacks.size();
    }
}
