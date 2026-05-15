package tn.reclamations.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.reclamations.com.entities.AgentSAV;

import java.util.List;

/**
 * Repository AgentSAV - Couche DAO (Spring Data JPA)
 */
@Repository
public interface AgentSAVRepository extends JpaRepository<AgentSAV, Long> {

    // Recherche par compétence
    List<AgentSAV> findByCompetenceContainingIgnoreCase(String competence);

    // Recherche par nom
    List<AgentSAV> findByNomContainingIgnoreCase(String nom);
}
