package tn.reclamations.com.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

// Ignore les champs internes du proxy Hibernate lors de la sérialisation JSON
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "agent_sav")
public class AgentSAV {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Le nom est obligatoire")
    @Column(nullable = false, length = 50)
    private String nom;

    @Column(length = 100)
    private String competence;

    @OneToMany(mappedBy = "agent", fetch = FetchType.LAZY)
    @JsonIgnore  // Évite la récursion infinie AgentSAV → Reclamation → AgentSAV → ...
    private List<Reclamation> reclamations = new ArrayList<>();

    public AgentSAV() {}

    public AgentSAV(String nom, String competence) {
        this.nom = nom;
        this.competence = competence;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getCompetence() { return competence; }
    public void setCompetence(String competence) { this.competence = competence; }

    public List<Reclamation> getReclamations() { return reclamations; }
    public void setReclamations(List<Reclamation> reclamations) {
        this.reclamations = reclamations;
    }
}