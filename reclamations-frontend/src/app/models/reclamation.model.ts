export interface Reclamation {
  id?: number;
  description: string;
  produit?: string;
  statut?: string;   // EN_ATTENTE | EN_COURS | RESOLUE | FERMEE | ANNULEE
  date?: string;
  note?: number;     // 1 à 5

  // Champs aplatis depuis le DTO (plus d'objets imbriqués)
  clientId?: number;
  clientNom?: string;
  clientEmail?: string;

  agentId?: number;
  agentNom?: string;
}
