package tn.reclamations.com.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.reclamations.com.dto.SuiviReclamationDTO;
import tn.reclamations.com.entities.SuiviReclamation;
import tn.reclamations.com.services.SuiviReclamationService;

import java.util.List;

@RestController
@RequestMapping("/api/suivis")
@CrossOrigin(origins = "*")
@Tag(name = "Suivis Réclamations", description = "API de suivi et historique des réclamations")
public class SuiviReclamationController {

    @Autowired
    private SuiviReclamationService suiviService;

    @GetMapping
    @Operation(summary = "Lister tous les suivis")
    public ResponseEntity<List<SuiviReclamationDTO>> getAllSuivis() {
        return ResponseEntity.ok(suiviService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un suivi par son ID")
    public ResponseEntity<?> getSuiviById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(suiviService.findById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/reclamation/{reclamationId}")
    @Operation(summary = "Ajouter un suivi à une réclamation")
    public ResponseEntity<?> addSuivi(@PathVariable Long reclamationId,
                                       @RequestParam(required = false) Long employeId,
                                       @Valid @RequestBody SuiviReclamation suivi) {
        try {
            SuiviReclamationDTO saved = suiviService.ajouterSuivi(suivi, reclamationId, employeId);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un suivi")
    public ResponseEntity<?> updateSuivi(@PathVariable Long id, @Valid @RequestBody SuiviReclamation suivi) {
        try {
            return ResponseEntity.ok(suiviService.update(id, suivi));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un suivi")
    public ResponseEntity<?> deleteSuivi(@PathVariable Long id) {
        try {
            suiviService.delete(id);
            return ResponseEntity.ok("Suivi supprimé avec succès");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/reclamation/{reclamationId}")
    @Operation(summary = "Historique des suivis d'une réclamation")
    public ResponseEntity<List<SuiviReclamationDTO>> getSuivisParReclamation(@PathVariable Long reclamationId) {
        return ResponseEntity.ok(suiviService.findByReclamation(reclamationId));
    }

    @GetMapping("/employe/{employeId}")
    @Operation(summary = "Suivis effectués par un agent")
    public ResponseEntity<List<SuiviReclamationDTO>> getSuivisParEmploye(@PathVariable Long employeId) {
        return ResponseEntity.ok(suiviService.findByEmploye(employeId));
    }
}
