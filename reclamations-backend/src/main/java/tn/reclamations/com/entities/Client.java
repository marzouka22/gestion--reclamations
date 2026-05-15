package tn.reclamations.com.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Le nom est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    @Column(nullable = false, length = 50)
    private String nom;

    @NotEmpty(message = "L'email est obligatoire")
    @Email(message = "Email invalide")
    @Column(nullable = false, length = 70, unique = true)
    private String email;

    @Pattern(regexp = "[0-9]{8}", message = "Téléphone invalide (8 chiffres)")
    @Column(length = 8)
    private String telephone;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore  // Évite la récursion infinie Client → Reclamation → Client → ...
    private List<Reclamation> reclamations = new ArrayList<>();

    public Client() {}

    public Client(String nom, String email, String telephone) {
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public List<Reclamation> getReclamations() { return reclamations; }
    public void setReclamations(List<Reclamation> reclamations) {
        this.reclamations = reclamations;
    }
}