package tn.reclamations.com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.reclamations.com.dto.SuiviReclamationDTO;
import tn.reclamations.com.entities.AgentSAV;
import tn.reclamations.com.entities.Reclamation;
import tn.reclamations.com.entities.SuiviReclamation;
import tn.reclamations.com.mapper.SuiviReclamationMapper;
import tn.reclamations.com.repositories.AgentSAVRepository;
import tn.reclamations.com.repositories.ReclamationRepository;
import tn.reclamations.com.repositories.SuiviReclamationRepository;

@Service
@Transactional
public class SuiviReclamationService {

    @Autowired
    private SuiviReclamationRepository suiviRepository;

    @Autowired
    private ReclamationRepository reclamationRepository;

    @Autowired
    private AgentSAVRepository agentSAVRepository;

    @Autowired
    private SuiviReclamationMapper suiviMapper;

    public List<SuiviReclamationDTO> findAll() {
        return suiviMapper.toListDto(suiviRepository.findAll());
    }

    public SuiviReclamationDTO findById(Long id) {
        SuiviReclamation suivi = suiviRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Suivi non trouvé avec l'id : " + id));
        return suiviMapper.toDto(suivi);
    }

    /**
     * Ajouter un suivi à une réclamation
     */
    public SuiviReclamationDTO ajouterSuivi(SuiviReclamation suivi, Long reclamationId, Long employeId) {
        Reclamation reclamation = reclamationRepository.findById(reclamationId)
                .orElseThrow(() -> new RuntimeException("Réclamation non trouvée avec l'id : " + reclamationId));
        suivi.setReclamation(reclamation);

        if (employeId != null) {
            AgentSAV employe = agentSAVRepository.findById(employeId)
                    .orElseThrow(() -> new RuntimeException("Agent SAV non trouvé avec l'id : " + employeId));
            suivi.setEmploye(employe);
        }
        return suiviMapper.toDto(suiviRepository.save(suivi));
    }

    public SuiviReclamationDTO update(Long id, SuiviReclamation suiviDetails) {
        SuiviReclamation suivi = suiviRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Suivi non trouvé avec l'id : " + id));
        suivi.setMessage(suiviDetails.getMessage());
        suivi.setAction(suiviDetails.getAction());
        return suiviMapper.toDto(suiviRepository.save(suivi));
    }

    public void delete(Long id) {
        SuiviReclamation suivi = suiviRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Suivi non trouvé avec l'id : " + id));
        suiviRepository.delete(suivi);
    }

    public List<SuiviReclamationDTO> findByReclamation(Long reclamationId) {
        return suiviMapper.toListDto(suiviRepository.findByReclamationIdOrderByDateAsc(reclamationId));
    }

    public List<SuiviReclamationDTO> findByEmploye(Long employeId) {
        return suiviMapper.toListDto(suiviRepository.findByEmployeId(employeId));
    }
}
