package tn.reclamations.com.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tn.reclamations.com.dto.SuiviReclamationDTO;
import tn.reclamations.com.entities.SuiviReclamation;

@Component
public class SuiviReclamationMapper {

    @Autowired
    private ModelMapper mmMapper;

    public SuiviReclamationDTO toDto(SuiviReclamation suivi) {
        SuiviReclamationDTO dto = mmMapper.map(suivi, SuiviReclamationDTO.class);

        // Mapping manuel des attributs aplatis
        if (suivi.getReclamation() != null) {
            dto.setReclamationId(suivi.getReclamation().getId());
        }
        if (suivi.getEmploye() != null) {
            dto.setEmployeId(suivi.getEmploye().getId());
            dto.setEmployeNom(suivi.getEmploye().getNom());
        }
        return dto;
    }

    public SuiviReclamation fromDto(SuiviReclamationDTO dto) {
        return mmMapper.map(dto, SuiviReclamation.class);
    }

    public List<SuiviReclamationDTO> toListDto(List<SuiviReclamation> listeSuivis) {
        return listeSuivis.stream()
                .map(s -> toDto(s))
                .collect(Collectors.toList());
    }
}
