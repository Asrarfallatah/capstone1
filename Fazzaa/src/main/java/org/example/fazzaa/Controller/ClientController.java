package org.example.fazzaa.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.fazzaa.ApiResponse.ApiResponse;
import org.example.fazzaa.Model.Client;
import org.example.fazzaa.Service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientController {




    private final ClientService clientService;


    @GetMapping("/get")
    public ResponseEntity<?> getAllClients() {

        if(clientService.getAllClients().isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("no clients yet in the database to show their information !"));
        }

        return ResponseEntity.status(200).body(clientService.getAllClients());
    }

    // add
    @PostMapping("/add")
    public ResponseEntity<?> addClient(@Valid @RequestBody Client client, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        boolean result = clientService.addClient(client);

        if (!result) {
            return ResponseEntity.status(400).body(new ApiResponse("error there is a client that use the same email you want to use !"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("client added successfully !"));
    }

   // update
    @PutMapping("/update/{clientId}")
    public ResponseEntity<?> updateClient(@PathVariable Integer clientId, @Valid @RequestBody Client client, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.status(400)
                    .body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        int result = clientService.updateClient(clientId, client);
        if (result == 1) {
            return ResponseEntity.status(400).body(new ApiResponse("error no client  with that id is found in the database to update its information !"));
        } else if (result == 2) {
            return ResponseEntity.status(400).body(new ApiResponse("error there is a client that use the same email you want to use !"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("client information has been updated successfully in the database !"));
    }

    // delete
    @DeleteMapping("/delete/{clientId}")
    public ResponseEntity<?> deleteClient(@PathVariable Integer clientId) {

        boolean result = clientService.deleteClient(clientId);

        if (!result) {
            return ResponseEntity.status(400).body(new ApiResponse("error no client  with that id is found in the database to delete its information !"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("client information has been deleted successfully from the database !"));
    }

    /// extra endpoints

    // 1- get client by specific email
    @GetMapping("/search/email/{email}")
    public ResponseEntity<?> getClientByEmail(@PathVariable String email) {

        if (clientService.getClientByEmail(email) == null) {
            return ResponseEntity.status(400).body(new ApiResponse("error no client with that email is found in the database !"));
        }

        return ResponseEntity.status(200).body(clientService.getClientByEmail(email));
    }

    // 2- count how many requests a specific client has made
    @GetMapping("/count/requests/{clientId}")
    public ResponseEntity<?> countClientRequests(@PathVariable Integer clientId) {

        int count = clientService.countClientRequests(clientId);

        if (count == -1) {
            return ResponseEntity.status(400).body(new ApiResponse("error no client with that id is found in the database !"));
        }

        return ResponseEntity.status(200).body(count);
    }

    //3-  count how many feedbacks a specific client has written
    @GetMapping("/count/feedback/{clientId}")
    public ResponseEntity<?> countClientFeedback(@PathVariable Integer clientId) {

        int count = clientService.countClientFeedbacks(clientId);

        if (count == -1) {
            return ResponseEntity.status(400).body(new ApiResponse("error no client with that id is found in the database !"));
        }

        return ResponseEntity.status(200).body(count);
    }
}
