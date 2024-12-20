package group.societe.services;


import group.societe.DTO.departementDTO;
import group.societe.DTO.employeDTO;


import java.util.List;
import java.util.Map;

public interface departementService {
    departementDTO addDepartement(departementDTO departementDTO);
    departementDTO updateDepartement(String id, departementDTO departementDTO);
    void deleteDepartement(String id);
    List<departementDTO> getAllDepartements();
    departementDTO getDepartementById(String id);
    // Méthode pour obtenir le département ayant le plus grand nombre d'employés
    departementDTO getDepartementWithMostEmployes();
    // Méthode pour obtenir le nombre d'employés par département
    Map<String, Integer> getNombreEmployesParDepartement();

}
