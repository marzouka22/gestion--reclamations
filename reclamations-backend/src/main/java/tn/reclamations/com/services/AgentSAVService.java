package tn.reclamations.com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.reclamations.com.dto.AgentSAVDTO;
import tn.reclamations.com.entities.AgentSAV;
import tn.reclamations.com.mapper.AgentSAVMapper;
import tn.reclamations.com.repositories.AgentSAVRepository;

@Service
@Transactional
public class AgentSAVService {

    @Autowired
    private AgentSAVRepository agentRepository;

    @Autowired
    private AgentSAVMapper agentMapper;

    public List<AgentSAVDTO> findAll() {
        return agentMapper.toListDto(agentRepository.findAll());
    }

    public AgentSAVDTO findById(Long id) {
        AgentSAV agent = agentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agent non trouvé avec l'id : " + id));
        return agentMapper.toDto(agent);
    }

    public AgentSAVDTO save(AgentSAVDTO dto) {
        AgentSAV agent = agentMapper.fromDto(dto);
        return agentMapper.toDto(agentRepository.save(agent));
    }

    public AgentSAVDTO update(Long id, AgentSAVDTO dto) {
        AgentSAV agent = agentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agent non trouvé avec l'id : " + id));
        agent.setNom(dto.getNom());
        agent.setCompetence(dto.getCompetence());
        return agentMapper.toDto(agentRepository.save(agent));
    }

    public void delete(Long id) {
        AgentSAV agent = agentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agent non trouvé avec l'id : " + id));
        agentRepository.delete(agent);
    }
}
