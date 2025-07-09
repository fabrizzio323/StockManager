package com.stockmanager.mapper;

import com.stockmanager.dto.ClientDTO;
import com.stockmanager.model.Client;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClientMapper {

    @Mapping(source ="id", target = "id")
    @Mapping(source="name", target="name")
    @Mapping(source="last_name", target="last_name")
    @Mapping(source="email",target="email")
    ClientDTO toClientDTO(Client client);

    @InheritConfiguration
    Client toClient(ClientDTO clientDTO);
    List<ClientDTO> toClientDTOs(List<Client> clients);
    List<Client> toClients(List<Client> clientDTOs);

}
