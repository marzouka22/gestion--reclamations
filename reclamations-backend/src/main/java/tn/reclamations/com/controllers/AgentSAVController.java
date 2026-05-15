package tn.reclamations.com.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.reclamations.com.dto.AgentSAVDTO;
import tn.reclamations.com.services.AgentSAVService;

import java.util.List;

@RestController
@RequestMapping("/api/agents")
@CrossOrigin(origins = "*")
@Tag(name = "Agents SAV", description = "API de gestion des agents du service après-vente")
public class AgentSAVController {

    @Autowired
    private AgentSAVService agentService;

    @GetMapping
    @Operation(summary = "Lister tous les agents SAV")
    public ResponseEntity<List<AgentSAVDTO>> getAllAgents() {
        return ResponseEntity.ok(agentService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un agent par son ID")
    public ResponseEntity<?> getAgentById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(agentService.findById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Créer un nouvel agent SAV")
    public ResponseEntity<?> createAgent(@Valid @RequestBody AgentSAVDTO dto) {
        try {
            AgentSAVDTO saved = agentService.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un agent SAV")
    public ResponseEntity<?> updateAgent(@PathVariable Long id, @Valid @RequestBody AgentSAVDTO dto) {
        try {
            return ResponseEntity.ok(agentService.update(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un agent SAV")
    public ResponseEntity<?> deleteAgent(@PathVariable Long id) {
        try {
            agentService.delete(id);
            return ResponseEntity.ok("Agent SAV supprimé avec succès");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
