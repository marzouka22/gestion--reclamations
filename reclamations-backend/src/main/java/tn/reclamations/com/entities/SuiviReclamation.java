package tn.reclamations.com.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "suivi_reclamation")
public class SuiviReclamation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Le message est obligatoire")
    @Size(min = 5, max = 1000, message = "Le message doit contenir entre 5 et 1000 caractères")
    @Column(nullable = false, length = 1000)
    private String message;

    @NotEmpty(message = "L'action est obligatoire")
    @Size(min = 2, max = 200, message = "L'action doit contenir entre 2 et 200 caractères")
    @Column(nullable = false, length = 200)
    private String action;

    @Column(nullable = false)
    private LocalDateTime date;

    // Association vers la réclamation concernée (ignorée côté suivi pour éviter la récursion)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reclamation_id", nullable = false)
    @JsonIgnore
    private Reclamation reclamation;

    // L'agent SAV qui a effectué ce suivi (employé)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employe_id")
    private AgentSAV employe;

    @PrePersist
    public void prePersist() {
        if (date == null) {
            date = LocalDateTime.now();
        }
    }

    public SuiviReclamation() {}

    public SuiviReclamation(String message, String action, Reclamation reclamation, AgentSAV employe) {
        this.message = message;
        this.action = action;
        this.reclamation = reclamation;
        this.employe = employe;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public Reclamation getReclamation() { return reclamation; }
    public void setReclamation(Reclamation reclamation) { this.reclamation = reclamation; }

    public AgentSAV getEmploye() { return employe; }
    public void setEmploye(AgentSAV employe) { this.employe = employe; }
}
