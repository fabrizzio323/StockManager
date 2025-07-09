package com.stockmanager.serviceImplementation;

import com.stockmanager.dto.ClientDTO;
import com.stockmanager.exception.CustomException;
import com.stockmanager.mapper.ClientMapper;
import com.stockmanager.model.Client;
import com.stockmanager.repository.ClienteRepository;
import com.stockmanager.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImplementation implements ClienteService {

    @Autowired
    private ClienteRepository repository;
    @Autowired
    private ClientMapper clientMapper;

    @Override
    public List<ClientDTO> listAllClients() throws CustomException {

        List<Client> clients = repository.findByStatus(true);
        if (clients.isEmpty()) {
            throw new CustomException("No active clients found.");
        } else {
            return clientMapper.toClientDTOs(clients);
        }

    }

    @Override
    public ClientDTO getClientById(Long id) throws CustomException {
        try{
            List<Client> clients = repository.findByStatus(true);
            Optional<Client> client = clients.stream()
                    .filter(x -> x.getId().equals(id))
                    .findFirst();
            return clientMapper.toClientDTO(client.get());
        }catch(Exception e){
            throw new CustomException("Error while fetching client by ID: " + id + " - " + e.getMessage());
        }
    }

    @Override
    public void createClient(ClientDTO clientDTO) throws CustomException {
     try{
         Client newClient = clientMapper.toClient(clientDTO);
         if(!findEmail(newClient.getEmail())){
             newClient.setStatus(true);
             repository.save(newClient);
         }else{
                throw new CustomException("Email already exists: " + newClient.getEmail());
         }
     }catch(Exception e){
         throw new CustomException("Error while creating client: " + e.getMessage());
     }
    }

    @Override
    public void deleteClient(Long id) throws CustomException {
      try{
          Optional<Client> client = repository.findById(id);
          if(client.isPresent()){
              Client clientToDelete = client.get();
              clientToDelete.setStatus(false);
              repository.save(clientToDelete);
          }else{
              throw new CustomException("Client not found with ID: " + id);
          }
      }catch(Exception e){
          throw new CustomException("Error while deleting client with ID: " + id + " - " + e.getMessage());
      }
          }

    @Override
    public void updateClient(Long idCLient, ClientDTO clientUpdatedDTO) throws CustomException {
      try{
          Optional<Client> client = repository.findById(idCLient);
          if(client.isPresent()) {
              Client existingClient = client.get();
              Client clientUpdated = clientMapper.toClient(clientUpdatedDTO);
              if (clientUpdated.getName() != null) {
                  existingClient.setName(clientUpdated.getName());
              }
              if(clientUpdated.getLast_name() != null){
                  existingClient.setLast_name(clientUpdated.getLast_name());
              }
              if (clientUpdated.getEmail() != null && !findEmail(clientUpdated.getEmail())) {
                  existingClient.setEmail(clientUpdated.getEmail());
              }
              repository.save(existingClient);
          }else{
                throw new CustomException("Client not found with ID: " + idCLient);
          }
      }catch(Exception e){
          throw new CustomException("Error while updating client with ID: " + idCLient + " - " + e.getMessage());
      }
    }

    @Override
    public boolean findEmail(String email) {
        List<Client> clients = repository.findByStatus(true);
        Optional<Client> client = clients.stream()
                .filter(x-> x.getEmail().equals(email))
                .findFirst();
        return client.isPresent();

    }
}
