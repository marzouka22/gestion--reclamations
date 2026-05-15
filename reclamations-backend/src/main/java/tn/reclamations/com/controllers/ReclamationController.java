package tn.reclamations.com.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.reclamations.com.dto.ReclamationDTO;
import tn.reclamations.com.entities.Reclamation;
import tn.reclamations.com.services.ReclamationService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reclamations")
@CrossOrigin(origins = "*")
@Tag(name = "Réclamations", description = "API de gestion des réclamations clients")
public class ReclamationController {

    @Autowired
    private ReclamationService reclamationService;

    @GetMapping
    @Operation(summary = "Lister toutes les réclamations")
    public ResponseEntity<List<ReclamationDTO>> getAllReclamations() {
        return ResponseEntity.ok(reclamationService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir une réclamation par son ID")
    public ResponseEntity<?> getReclamationById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(reclamationService.findById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/client/{clientId}")
    @Operation(summary = "Enregistrer une nouvelle réclamation pour un client")
    public ResponseEntity<?> createReclamation(@PathVariable Long clientId,
                                                @Valid @RequestBody Reclamation reclamation) {
        try {
            ReclamationDTO saved = reclamationService.enregistrer(reclamation, clientId);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une réclamation")
    public ResponseEntity<?> updateReclamation(@PathVariable Long id,
                                                @RequestParam(required = false) Long clientId,
                                                @Valid @RequestBody Reclamation reclamation) {
        try {
            return ResponseEntity.ok(reclamationService.update(id, reclamation, clientId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une réclamation")
    public ResponseEntity<?> deleteReclamation(@PathVariable Long id) {
        try {
            reclamationService.delete(id);
            return ResponseEntity.ok("Réclamation supprimée avec succès");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/affecter/{agentId}")
    @Operation(summary = "Affecter un agent SAV à une réclamation")
    public ResponseEntity<?> affecterAgent(@PathVariable Long id, @PathVariable Long agentId) {
        try {
            return ResponseEntity.ok(reclamationService.affecterAgent(id, agentId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/statut")
    @Operation(summary = "Changer le statut d'une réclamation")
    public ResponseEntity<?> changerStatut(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            String statut = body.get("statut");
            return ResponseEntity.ok(reclamationService.changerStatut(id, statut));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/evaluer")
    @Operation(summary = "Evaluer une réclamation (note de satisfaction 1 à 5)")
    public ResponseEntity<?> evaluer(@PathVariable Long id, @RequestBody Map<String, Integer> body) {
        try {
            Integer note = body.get("note");
            return ResponseEntity.ok(reclamationService.evaluer(id, note));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/client/{clientId}")
    @Operation(summary = "Réclamations d'un client")
    public ResponseEntity<List<ReclamationDTO>> getByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(reclamationService.findByClient(clientId));
    }

    @GetMapping("/agent/{agentId}")
    @Operation(summary = "Réclamations affectées à un agent")
    public ResponseEntity<List<ReclamationDTO>> getByAgent(@PathVariable Long agentId) {
        return ResponseEntity.ok(reclamationService.findByAgent(agentId));
    }

    @GetMapping("/non-affectees")
    @Operation(summary = "Réclamations sans agent affecté")
    public ResponseEntity<List<ReclamationDTO>> getNonAffectees() {
        return ResponseEntity.ok(reclamationService.findNonAffectees());
    }

    @GetMapping("/statut/{statut}")
    @Operation(summary = "Réclamations par statut")
    public ResponseEntity<List<ReclamationDTO>> getByStatut(@PathVariable String statut) {
        return ResponseEntity.ok(reclamationService.findByStatut(statut));
    }
}
