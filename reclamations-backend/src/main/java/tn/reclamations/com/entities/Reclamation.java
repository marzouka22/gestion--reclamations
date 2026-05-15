package tn.reclamations.com.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reclamation")
public class Reclamation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "La description est obligatoire")
    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false, length = 20)
    private String statut = "EN_ATTENTE";

    @Column(nullable = false)
    private LocalDate date = LocalDate.now();

    @Min(value = 1, message = "Note minimum : 1")
    @Max(value = 5, message = "Note maximum : 5")
    private Integer note;

    @Column(length = 100)
    private String produit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id")
    private AgentSAV agent;

    @OneToMany(mappedBy = "reclamation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore  // Évite la récursion infinie Reclamation → SuiviReclamation → Reclamation → ...
    private List<SuiviReclamation> suivis = new ArrayList<>();

    public Reclamation() {}

    public Reclamation(String description, String produit, Client client) {
        this.description = description;
        this.produit = produit;
        this.client = client;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Integer getNote() { return note; }
    public void setNote(Integer note) { this.note = note; }

    public String getProduit() { return produit; }
    public void setProduit(String produit) { this.produit = produit; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public AgentSAV getAgent() { return agent; }
    public void setAgent(AgentSAV agent) { this.agent = agent; }

    public List<SuiviReclamation> getSuivis() { return suivis; }
    public void setSuivis(List<SuiviReclamation> suivis) { this.suivis = suivis; }
}