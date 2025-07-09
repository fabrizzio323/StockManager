package com.stockmanager.service;


import com.stockmanager.dto.ClientDTO;
import com.stockmanager.exception.CustomException;


import java.util.List;

public interface ClienteService {

    public List<ClientDTO> listAllClients() throws CustomException;
    public ClientDTO getClientById(Long id) throws CustomException;
    public void createClient(ClientDTO clientDTO) throws CustomException;
    public void deleteClient(Long id) throws CustomException;
    public void updateClient(Long idCLient, ClientDTO clientUpdatedDTO) throws CustomException;
    public boolean findEmail(String email);
}
