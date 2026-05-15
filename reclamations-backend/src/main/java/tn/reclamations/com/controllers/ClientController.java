package tn.reclamations.com.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.reclamations.com.dto.ClientDTO;
import tn.reclamations.com.services.ClientService;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "*")
@Tag(name = "Clients", description = "API de gestion des clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    @Operation(summary = "Lister tous les clients")
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un client par son ID")
    public ResponseEntity<?> getClientById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(clientService.findById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau client")
    public ResponseEntity<?> createClient(@Valid @RequestBody ClientDTO dto) {
        try {
            ClientDTO saved = clientService.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un client")
    public ResponseEntity<?> updateClient(@PathVariable Long id, @Valid @RequestBody ClientDTO dto) {
        try {
            return ResponseEntity.ok(clientService.update(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un client")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        try {
            clientService.delete(id);
            return ResponseEntity.ok("Client supprimé avec succès");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
