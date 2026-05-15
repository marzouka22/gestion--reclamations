package tn.reclamations.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.reclamations.com.entities.Client;

import java.util.Optional;

/**
 * Repository Client - Couche DAO (Spring Data JPA)
 * Hérite de JpaRepository qui fournit automatiquement les opérations CRUD
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    // Recherche par email
    Optional<Client> findByEmail(String email);

    // Vérification existence par email
    boolean existsByEmail(String email);

    // Recherche par nom (contient)
    java.util.List<Client> findByNomContainingIgnoreCase(String nom);
}
