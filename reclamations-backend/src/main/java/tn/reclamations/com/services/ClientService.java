package tn.reclamations.com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.reclamations.com.dto.ClientDTO;
import tn.reclamations.com.entities.Client;
import tn.reclamations.com.mapper.ClientMapper;
import tn.reclamations.com.repositories.ClientRepository;

@Service
@Transactional
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper clientMapper;

    public List<ClientDTO> findAll() {
        return clientMapper.toListDto(clientRepository.findAll());
    }

    public ClientDTO findById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé avec l'id : " + id));
        return clientMapper.toDto(client);
    }

    public ClientDTO save(ClientDTO dto) {
        if (clientRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Un client avec cet email existe déjà : " + dto.getEmail());
        }
        Client client = clientMapper.fromDto(dto);
        return clientMapper.toDto(clientRepository.save(client));
    }

    public ClientDTO update(Long id, ClientDTO dto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé avec l'id : " + id));

        if (!client.getEmail().equals(dto.getEmail())
                && clientRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Cet email est déjà utilisé : " + dto.getEmail());
        }

        client.setNom(dto.getNom());
        client.setEmail(dto.getEmail());
        client.setTelephone(dto.getTelephone());
        return clientMapper.toDto(clientRepository.save(client));
    }

    public void delete(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé avec l'id : " + id));
        clientRepository.delete(client);
    }
}
