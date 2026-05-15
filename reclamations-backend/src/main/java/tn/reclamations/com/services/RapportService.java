package tn.reclamations.com.services;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.reclamations.com.repositories.ReclamationRepository;

@Service
@Transactional(readOnly = true)
public class RapportService {

    @Autowired
    private ReclamationRepository reclamationRepository;

    public Map<String, Object> getRapportSatisfaction() {
        Map<String, Object> rapport = new LinkedHashMap<>();

        rapport.put("totalReclamations", reclamationRepository.count());
        rapport.put("moyenneNotes", reclamationRepository.moyenneNotes());
        rapport.put("reclamationsEvaluees", reclamationRepository.findEvaluees().size());

        // Répartition par statut
        Map<String, Long> parStatut = new LinkedHashMap<>();
        List<Object[]> statsStatut = reclamationRepository.countByStatut();
        for (Object[] row : statsStatut) {
            parStatut.put((String) row[0], (Long) row[1]);
        }
        rapport.put("repartitionParStatut", parStatut);

        // Répartition par agent
        Map<String, Long> parAgent = new LinkedHashMap<>();
        List<Object[]> statsAgent = reclamationRepository.countByAgent();
        for (Object[] row : statsAgent) {
            parAgent.put((String) row[0], (Long) row[1]);
        }
        rapport.put("repartitionParAgent", parAgent);

        // Réclamations par client
        Map<String, Long> parClient = new LinkedHashMap<>();
        List<Object[]> statsClient = reclamationRepository.countByClient();
        for (Object[] row : statsClient) {
            parClient.put((String) row[0], (Long) row[1]);
        }
        rapport.put("reclamationsParClient", parClient);

        // Distribution des notes (1 à 5)
        Map<String, Long> distribNotes = new LinkedHashMap<>();
        for (int i = 1; i <= 5; i++) {
            final int note = i;
            long count = reclamationRepository.findEvaluees().stream()
                    .filter(r -> r.getNote() != null && r.getNote() == note)
                    .count();
            distribNotes.put("note_" + note + "_etoile(s)", count);
        }
        rapport.put("distributionNotes", distribNotes);

        // Taux de résolution
        long resolues = parStatut.getOrDefault("RESOLUE", 0L)
                + parStatut.getOrDefault("FERMEE", 0L);
        long total = reclamationRepository.count();
        double tauxResolution = total > 0 ? (double) resolues / total * 100 : 0;
        rapport.put("tauxResolution", String.format("%.1f%%", tauxResolution));

        return rapport;
    }

    public Map<String, Long> getStatsParStatut() {
        Map<String, Long> stats = new LinkedHashMap<>();
        List<Object[]> result = reclamationRepository.countByStatut();
        for (Object[] row : result) {
            stats.put((String) row[0], (Long) row[1]);
        }
        return stats;
    }

    public Double getMoyenneNotes() {
        return reclamationRepository.moyenneNotes();
    }
}
