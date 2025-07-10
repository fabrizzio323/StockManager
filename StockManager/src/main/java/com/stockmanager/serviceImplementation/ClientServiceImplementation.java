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
    public ClientDTO getClientById(Long idClient) throws CustomException {
        try{
            Client client = repository.findByIdAndStatus(idClient,true).orElseThrow(() -> new CustomException("Client not found with ID: " + idClient));
            return clientMapper.toClientDTO(client);

        }catch(Exception e){
            throw new CustomException("Error while fetching client by ID: " + idClient + " - " + e.getMessage());
        }
    }

    @Override
    public void createClient(ClientDTO clientDTO) throws CustomException {
     try{
         Client newClient = clientMapper.toClient(clientDTO);
         if(!EmailExists(newClient.getEmail())){
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
    public void deleteClient(Long idClient) throws CustomException {
      try{
          Client client = repository.findById(idClient)
                  .orElseThrow(() -> new CustomException("Client not found with ID: " + idClient));
           if(!client.isStatus()){
                throw new CustomException("Client with ID: " + idClient + " is already inactive.");
              } else {
                client.setStatus(false);
                repository.save(client);
           }
      }catch(Exception e){
          throw new CustomException("Error while deleting client with ID: " + idClient + " - " + e.getMessage());
      }
          }

    @Override
    public void updateClient(Long idCLient, ClientDTO clientUpdatedDTO) throws CustomException {
         Client client = repository.findByIdAndStatus(idCLient,true).orElseThrow(() -> new CustomException("Client not found with ID: " + idCLient));

         if(clientUpdatedDTO.getName() != null) {
             client.setName(clientUpdatedDTO.getName());
         }
            if(clientUpdatedDTO.getLast_name() != null) {
                client.setLast_name(clientUpdatedDTO.getLast_name());
            }
            if(clientUpdatedDTO.getEmail() != null) {
                if(!EmailExists(clientUpdatedDTO.getEmail())) {
                    client.setEmail(clientUpdatedDTO.getEmail());
                } else {
                    throw new CustomException("Email already exists: " + clientUpdatedDTO.getEmail());
                }
            }
            repository.save(client);
    }

    @Override
    public boolean EmailExists(String email) {
        List<Client> clients = repository.findByStatus(true);
        Optional<Client> client = clients.stream()
                .filter(x-> x.getEmail().equals(email))
                .findFirst();
        return client.isPresent();

    }
}
