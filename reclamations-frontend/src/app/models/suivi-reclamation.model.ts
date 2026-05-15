export interface SuiviReclamation {
  id?: number;
  message: string;
  action: string;
  date?: string;

  // Champs aplatis depuis le DTO
  reclamationId?: number;
  employeId?: number;
  employeNom?: string;
}
