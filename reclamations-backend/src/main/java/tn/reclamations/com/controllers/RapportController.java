package tn.reclamations.com.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.reclamations.com.services.RapportService;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/rapports")
@CrossOrigin(origins = "*")
@Tag(name = "Rapports", description = "API de génération de rapports de satisfaction client")
public class RapportController {

    @Autowired
    private RapportService rapportService;

    @GetMapping("/satisfaction")
    @Operation(summary = "Rapport complet de satisfaction client")
    public ResponseEntity<Map<String, Object>> getRapportSatisfaction() {
        return ResponseEntity.ok(rapportService.getRapportSatisfaction());
    }

    @GetMapping("/stats/statut")
    @Operation(summary = "Statistiques des réclamations par statut")
    public ResponseEntity<Map<String, Long>> getStatsParStatut() {
        return ResponseEntity.ok(rapportService.getStatsParStatut());
    }

    @GetMapping("/satisfaction/moyenne")
    @Operation(summary = "Moyenne des notes de satisfaction")
    public ResponseEntity<Map<String, Object>> getMoyenneNotes() {
        Double moyenne = rapportService.getMoyenneNotes();
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("moyenneNotesSatisfaction",
                moyenne != null ? String.format("%.2f / 5", moyenne) : "Aucune évaluation");
        result.put("valeur", moyenne);
        return ResponseEntity.ok(result);
    }
}
