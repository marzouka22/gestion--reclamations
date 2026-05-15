package tn.reclamations.com.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ReclamationDTO {

    private Long id;
    private String description;
    private String statut;
    private LocalDate date;
    private Integer note;
    private String produit;

    // Données du client (aplaties, sans entité Client)
    private Long clientId;
    private String clientNom;
    private String clientEmail;

    // Données de l'agent (aplaties, sans entité AgentSAV)
    private Long agentId;
    private String agentNom;
}
