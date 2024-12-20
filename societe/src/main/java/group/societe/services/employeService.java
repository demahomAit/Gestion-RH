package group.societe.services;

import group.societe.DTO.departementEmployesDTO;
import group.societe.DTO.employeDTO;

import java.util.List;

public interface employeService {
    void addEmploye(employeDTO employeDTO);
    void updateEmploye(Integer id, employeDTO employeDTO);
    void deleteEmploye(Integer id);
    // Méthode pour obtenir le nombre total d'employés
    long getTotalEmployes();
    List<employeDTO> getAllEmployes();
    employeDTO getEmployeById(Integer id);

    List<employeDTO> getEmployesWithSalaryGreaterThan(Float salaire);
    // Méthode pour afficher les employés d'un département
    departementEmployesDTO getEmployesByDepartement(String departementIdOrName);
    // Méthode pour réaffecter un employé vers un nouveau département
    void reaffecterEmploye(Integer employeId, String nouveauDepartementId);
    // Méthode pour calculer la masse salariale de l'entreprise
    double calculerMasseSalarialeEntreprise();

    // Méthode pour calculer la masse salariale d'un département
    double calculerMasseSalarialeDepartement(String departementIdOrName);
}
