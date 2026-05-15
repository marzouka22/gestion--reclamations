package tn.reclamations.com.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tn.reclamations.com.dto.AgentSAVDTO;
import tn.reclamations.com.entities.AgentSAV;

@Component
public class AgentSAVMapper {

    @Autowired
    private ModelMapper mmMapper;

    public AgentSAVDTO toDto(AgentSAV agent) {
        return mmMapper.map(agent, AgentSAVDTO.class);
    }

    public AgentSAV fromDto(AgentSAVDTO dto) {
        return mmMapper.map(dto, AgentSAV.class);
    }

    public List<AgentSAVDTO> toListDto(List<AgentSAV> listeAgents) {
        return listeAgents.stream()
                .map(a -> mmMapper.map(a, AgentSAVDTO.class))
                .collect(Collectors.toList());
    }
}
