package tn.reclamations.com.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tn.reclamations.com.dto.ClientDTO;
import tn.reclamations.com.entities.Client;

@Component
public class ClientMapper {

    @Autowired
    private ModelMapper mmMapper;

    public ClientDTO toDto(Client client) {
        return mmMapper.map(client, ClientDTO.class);
    }

    public Client fromDto(ClientDTO dto) {
        return mmMapper.map(dto, Client.class);
    }

    public List<ClientDTO> toListDto(List<Client> listeClients) {
        return listeClients.stream()
                .map(c -> mmMapper.map(c, ClientDTO.class))
                .collect(Collectors.toList());
    }
}
