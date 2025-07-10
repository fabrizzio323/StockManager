package com.stockmanager.controller;

import com.stockmanager.dto.ClientDTO;
import com.stockmanager.exception.CustomException;
import com.stockmanager.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClienteService service;

    @GetMapping("/")
    public ResponseEntity<?> getAllClients() {
        try {
            List<ClientDTO> clients = service.listAllClients();
            return new ResponseEntity<>(clients, HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable Long id) {
        try {
            ClientDTO client = service.getClientById(id);
            return new ResponseEntity<>(client, HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createClient(@RequestBody ClientDTO clientDTO) {
        try {
            service.createClient(clientDTO);
            return new ResponseEntity<>("Client created successfully", HttpStatus.CREATED);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(@PathVariable Long id, @RequestBody ClientDTO clientUpdatedDTO) {
        try {
            service.updateClient(id, clientUpdatedDTO);
            return new ResponseEntity<>("Client updated successfully", HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        try {
            service.deleteClient(id);
            return new ResponseEntity<>("Client deleted successfully", HttpStatus.NO_CONTENT);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
