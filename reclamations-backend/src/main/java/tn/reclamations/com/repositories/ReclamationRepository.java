package tn.reclamations.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.reclamations.com.entities.Reclamation;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository Réclamation - Couche DAO (Spring Data JPA)
 * Le champ "statut" est un String, le champ "agent" est de type AgentSAV
 */
@Repository
public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {

    // Réclamations par client
    List<Reclamation> findByClientId(Long clientId);

    // Réclamations par agent SAV (champ 'agent')
    List<Reclamation> findByAgentId(Long agentId);

    // Réclamations sans agent affecté
    List<Reclamation> findByAgentIsNull();

    // Réclamations par statut (String)
    List<Reclamation> findByStatut(String statut);

    // Réclamations par produit
    List<Reclamation> findByProduitContainingIgnoreCase(String produit);

    // Réclamations par période
    List<Reclamation> findByDateBetween(LocalDate debut, LocalDate fin);

    // Nombre de réclamations par statut (pour rapport)
    @Query("SELECT r.statut, COUNT(r) FROM Reclamation r GROUP BY r.statut")
    List<Object[]> countByStatut();

    // Moyenne des notes de satisfaction
    @Query("SELECT AVG(r.note) FROM Reclamation r WHERE r.note IS NOT NULL")
    Double moyenneNotes();

    // Réclamations évaluées (avec note)
    @Query("SELECT r FROM Reclamation r WHERE r.note IS NOT NULL ORDER BY r.date DESC")
    List<Reclamation> findEvaluees();

    // Nombre de réclamations par agent
    @Query("SELECT a.nom, COUNT(r) FROM Reclamation r JOIN r.agent a GROUP BY a.nom")
    List<Object[]> countByAgent();

    // Nombre de réclamations par client
    @Query("SELECT c.nom, COUNT(r) FROM Reclamation r JOIN r.client c GROUP BY c.nom ORDER BY COUNT(r) DESC")
    List<Object[]> countByClient();
}
