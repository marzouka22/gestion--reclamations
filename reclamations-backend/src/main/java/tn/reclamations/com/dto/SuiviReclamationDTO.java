package tn.reclamations.com.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SuiviReclamationDTO {

    private Long id;
    private String message;
    private String action;
    private LocalDateTime date;

    // Données de la réclamation (aplaties, sans entité Reclamation)
    private Long reclamationId;

    // Données de l'employé (aplaties, sans entité AgentSAV)
    private Long employeId;
    private String employeNom;
}
