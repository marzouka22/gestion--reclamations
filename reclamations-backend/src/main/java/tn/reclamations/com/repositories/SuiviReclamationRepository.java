package tn.reclamations.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.reclamations.com.entities.SuiviReclamation;

import java.util.List;

/**
 * Repository SuiviRéclamation - Couche DAO (Spring Data JPA)
 */
@Repository
public interface SuiviReclamationRepository extends JpaRepository<SuiviReclamation, Long> {

    // Tous les suivis d'une réclamation
    List<SuiviReclamation> findByReclamationIdOrderByDateAsc(Long reclamationId);

    // Tous les suivis d'un employé (agent SAV)
    List<SuiviReclamation> findByEmployeId(Long employeId);
}
