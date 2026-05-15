package tn.reclamations.com.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tn.reclamations.com.dto.ReclamationDTO;
import tn.reclamations.com.entities.Reclamation;

@Component
public class ReclamationMapper {

    @Autowired
    private ModelMapper mmMapper;

    public ReclamationDTO toDto(Reclamation reclamation) {
        ReclamationDTO dto = mmMapper.map(reclamation, ReclamationDTO.class);

        // Mapping manuel des attributs aplatis (client et agent)
        if (reclamation.getClient() != null) {
            dto.setClientId(reclamation.getClient().getId());
            dto.setClientNom(reclamation.getClient().getNom());
            dto.setClientEmail(reclamation.getClient().getEmail());
        }
        if (reclamation.getAgent() != null) {
            dto.setAgentId(reclamation.getAgent().getId());
            dto.setAgentNom(reclamation.getAgent().getNom());
        }
        return dto;
    }

    public Reclamation fromDto(ReclamationDTO dto) {
        return mmMapper.map(dto, Reclamation.class);
    }

    public List<ReclamationDTO> toListDto(List<Reclamation> listeReclamations) {
        return listeReclamations.stream()
                .map(r -> toDto(r))
                .collect(Collectors.toList());
    }
}
