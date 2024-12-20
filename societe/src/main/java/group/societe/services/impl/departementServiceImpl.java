package group.societe.services.impl;


import group.societe.DTO.departementDTO;
import group.societe.enteties.Departement;
import group.societe.exception.NoDepartementsFoundException;
import group.societe.mappers.departementMapper;
import group.societe.repo.departementRepo;
import group.societe.services.departementService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class departementServiceImpl implements departementService {

    private final departementRepo departementRepo;

    public departementServiceImpl(departementRepo departementRepo) {
        this.departementRepo = departementRepo;
    }

    @Override
    public departementDTO getDepartementWithMostEmployes() {
        // Récupérer tous les départements
        List<Departement> departements = departementRepo.findAll();

        // Trouver le département ayant le plus grand nombre d'employés
        Departement departementWithMostEmployes = departements.stream()
                .max(Comparator.comparingInt(departement -> departement.getEmployeList().size()))
                .orElseThrow(() -> new NoDepartementsFoundException("Aucun département trouvé"));

        // Convertir le département en DTO
        return departementMapper.toDTO(departementWithMostEmployes);
    }
    @Override
    public Map<String, Integer> getNombreEmployesParDepartement() {
        // Récupérer tous les départements
        List<Departement> departements = departementRepo.findAll();

        // Créer une map pour stocker le nombre d'employés par département
        Map<String, Integer> nombreEmployesParDepartement = new HashMap<>();

        // Remplir la map avec le nombre d'employés pour chaque département
        for (Departement departement : departements) {
            nombreEmployesParDepartement.put(departement.getIddept(), departement.getEmployeList().size());
        }

        return nombreEmployesParDepartement;
    }
    @Override
    public departementDTO addDepartement(departementDTO departementDTO) {
        Departement departement = departementMapper.toEntity(departementDTO);
        Departement savedDepartement = departementRepo.save(departement);
        return departementMapper.toDTO(savedDepartement);
    }

    @Override
    public departementDTO updateDepartement(String id, departementDTO departementDTO) {
        Optional<Departement> optionalDepartement = departementRepo.findById(id);
        if (optionalDepartement.isPresent()) {
            Departement departement = optionalDepartement.get();
            departement.setNomdept(departementDTO.getNomdept());
            Departement updatedDepartement = departementRepo.save(departement);
            return departementMapper.toDTO(updatedDepartement);
        }
        throw new RuntimeException("Département introuvable avec l'ID : " + id);
    }

    @Override
    public void deleteDepartement(String id) {
        departementRepo.deleteById(id);
    }

    @Override
    public List<departementDTO> getAllDepartements() {
        return departementRepo.findAll()
                .stream()
                .map(departementMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public departementDTO getDepartementById(String id) {
        return departementRepo.findById(id)
                .map(departementMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Département introuvable avec l'ID : " + id));
    }


}
