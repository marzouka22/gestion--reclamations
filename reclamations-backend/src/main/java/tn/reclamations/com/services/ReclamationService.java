package tn.reclamations.com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.reclamations.com.dto.ReclamationDTO;
import tn.reclamations.com.entities.AgentSAV;
import tn.reclamations.com.entities.Client;
import tn.reclamations.com.entities.Reclamation;
import tn.reclamations.com.mapper.ReclamationMapper;
import tn.reclamations.com.repositories.AgentSAVRepository;
import tn.reclamations.com.repositories.ClientRepository;
import tn.reclamations.com.repositories.ReclamationRepository;

@Service
@Transactional
public class ReclamationService {

    @Autowired
    private ReclamationRepository reclamationRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AgentSAVRepository agentSAVRepository;

    @Autowired
    private ReclamationMapper reclamationMapper;

    public List<ReclamationDTO> findAll() {
        return reclamationMapper.toListDto(reclamationRepository.findAll());
    }

    public ReclamationDTO findById(Long id) {
        Reclamation reclamation = reclamationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réclamation non trouvée avec l'id : " + id));
        return reclamationMapper.toDto(reclamation);
    }

    /**
     * Enregistrement d'une nouvelle réclamation pour un client
     */
    public ReclamationDTO enregistrer(Reclamation reclamation, Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client non trouvé avec l'id : " + clientId));
        reclamation.setClient(client);
        reclamation.setStatut("EN_ATTENTE");
        return reclamationMapper.toDto(reclamationRepository.save(reclamation));
    }

    public ReclamationDTO update(Long id, Reclamation reclamationDetails, Long clientId) {
        Reclamation reclamation = reclamationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réclamation non trouvée avec l'id : " + id));

        if (clientId != null) {
            Client client = clientRepository.findById(clientId)
                    .orElseThrow(() -> new RuntimeException("Client non trouvé avec l'id : " + clientId));
            reclamation.setClient(client);
        }
        reclamation.setProduit(reclamationDetails.getProduit());
        reclamation.setDescription(reclamationDetails.getDescription());
        if (reclamationDetails.getStatut() != null) {
            reclamation.setStatut(reclamationDetails.getStatut());
        }
        return reclamationMapper.toDto(reclamationRepository.save(reclamation));
    }

    public void delete(Long id) {
        Reclamation reclamation = reclamationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réclamation non trouvée avec l'id : " + id));
        reclamationRepository.delete(reclamation);
    }

    /**
     * Affecter un agent SAV à une réclamation — passe automatiquement le statut à EN_COURS
     */
    public ReclamationDTO affecterAgent(Long reclamationId, Long agentId) {
        Reclamation reclamation = reclamationRepository.findById(reclamationId)
                .orElseThrow(() -> new RuntimeException("Réclamation non trouvée avec l'id : " + reclamationId));
        AgentSAV agent = agentSAVRepository.findById(agentId)
                .orElseThrow(() -> new RuntimeException("Agent SAV non trouvé avec l'id : " + agentId));
        reclamation.setAgent(agent);
        if ("EN_ATTENTE".equals(reclamation.getStatut())) {
            reclamation.setStatut("EN_COURS");
        }
        return reclamationMapper.toDto(reclamationRepository.save(reclamation));
    }

    /**
     * Changer le statut : EN_ATTENTE, EN_COURS, RESOLUE, FERMEE, ANNULEE
     */
    public ReclamationDTO changerStatut(Long reclamationId, String nouveauStatut) {
        Reclamation reclamation = reclamationRepository.findById(reclamationId)
                .orElseThrow(() -> new RuntimeException("Réclamation non trouvée avec l'id : " + reclamationId));
        reclamation.setStatut(nouveauStatut);
        return reclamationMapper.toDto(reclamationRepository.save(reclamation));
    }

    /**
     * Evaluer une réclamation (note 1 à 5) — ferme automatiquement la réclamation
     */
    public ReclamationDTO evaluer(Long reclamationId, Integer note) {
        Reclamation reclamation = reclamationRepository.findById(reclamationId)
                .orElseThrow(() -> new RuntimeException("Réclamation non trouvée avec l'id : " + reclamationId));
        if (note < 1 || note > 5) {
            throw new RuntimeException("La note doit être entre 1 et 5");
        }
        reclamation.setNote(note);
        reclamation.setStatut("FERMEE");
        return reclamationMapper.toDto(reclamationRepository.save(reclamation));
    }

    public List<ReclamationDTO> findByClient(Long clientId) {
        return reclamationMapper.toListDto(reclamationRepository.findByClientId(clientId));
    }

    public List<ReclamationDTO> findByAgent(Long agentId) {
        return reclamationMapper.toListDto(reclamationRepository.findByAgentId(agentId));
    }

    public List<ReclamationDTO> findNonAffectees() {
        return reclamationMapper.toListDto(reclamationRepository.findByAgentIsNull());
    }

    public List<ReclamationDTO> findByStatut(String statut) {
        return reclamationMapper.toListDto(reclamationRepository.findByStatut(statut));
    }
}
