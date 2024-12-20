package group.societe.services.impl;


import group.societe.DTO.departementEmployesDTO;
import group.societe.DTO.employeDTO;
import group.societe.enteties.Departement;
import group.societe.enteties.Employe;
import group.societe.enteties.HistoriqueAffectation;
import group.societe.exception.DepartementNotFoundException;
import group.societe.exception.EmployeNotFoundException;
import group.societe.mappers.employeMapper;
import group.societe.repo.HistoriqueAffectationRepository;
import group.societe.repo.departementRepo;
import group.societe.repo.employeRepo;
import group.societe.services.employeService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class employeServiceImpl implements employeService {

    private final employeRepo repo;
    private final departementRepo drepo;

    private HistoriqueAffectationRepository hrepo;

    public employeServiceImpl(employeRepo repo, departementRepo drepo,HistoriqueAffectationRepository hrepo) {
        this.repo = repo;
        this.drepo = drepo;
        this.hrepo = hrepo;
    }

    @Override
    @Transactional
    public void addEmploye(employeDTO employeDTO) {
        // Récupérer l'ID du département depuis le DTO
        String departementId = employeDTO.getRefDeptId();

        // Recherche du département par ID
        Departement departement = drepo.findById(departementId)
                .orElseThrow(() -> new DepartementNotFoundException("Département non trouvé"));

        // Conversion du DTO en entité Employe
        Employe employe = employeMapper.toEntity(employeDTO);

        // Associer l'employé au département
        employe.setRefDept(departement);

        // Sauvegarder l'employé dans la base de données
        repo.save(employe);

        // Mettre à jour la liste des employés du département
        departement.getEmployeList().add(employe);
        drepo.save(departement); // Optionnel, car l'employé est déjà associé au département
    }

    @Override
    @Transactional
    public void updateEmploye(Integer employeId, employeDTO employeDTO) {
        // Recherche de l'employé par son ID
        Employe employe = repo.findById(employeId)
                .orElseThrow(() -> new EmployeNotFoundException("Employé non trouvé"));

        // Mise à jour des informations de l'employé
        employe.setNomEmp(employeDTO.getNomEmp());
        employe.setSalaire(employeDTO.getSalaire());

        // Si le département est modifié, mettre à jour l'association
        if (employeDTO.getRefDeptId() != null && !employeDTO.getRefDeptId().equals(employe.getRefDept().getIddept())) {
            // Recherche du nouveau département
            Departement nouveauDepartement = drepo.findById(employeDTO.getRefDeptId())
                    .orElseThrow(() -> new DepartementNotFoundException("Département non trouvé"));

            // Mettre à jour le département de l'employé
            employe.setRefDept(nouveauDepartement);
        }

        // Sauvegarder les modifications dans la base de données
        repo.save(employe);
    }

    @Override
    @Transactional
    public void deleteEmploye(Integer employeId) {
        // Recherche de l'employé par son ID
        Employe employe = repo.findById(employeId)
                .orElseThrow(() -> new EmployeNotFoundException("Employé avec l'ID " + employeId + " non trouvé"));

        // Récupérer le département associé à l'employé
        Departement departement = employe.getRefDept();

        // Supprimer l'employé de la base de données
        repo.delete(employe);

        // Mettre à jour la liste des employés du département
        if (departement != null) {
            departement.getEmployeList().remove(employe);
            drepo.save(departement); // Optionnel, car la suppression est déjà gérée par JPA
        }
    }
    @Override
    public long getTotalEmployes() {
        // Compter le nombre total d'employés dans la base de données
        return repo.count();
    }
    @Override
    @Transactional
    public void reaffecterEmploye(Integer employeId, String nouveauDepartementId) {
        // Recherche de l'employé par son ID
        Employe employe = repo.findById(employeId)
                .orElseThrow(() -> new EmployeNotFoundException("Employé avec l'ID " + employeId + " non trouvé"));

        // Recherche du nouveau département par son ID
        Departement nouveauDepartement = drepo.findById(nouveauDepartementId)
                .orElseThrow(() -> new DepartementNotFoundException("Département avec l'ID " + nouveauDepartementId + " non trouvé"));

        // Récupérer l'ancien département de l'employé
        Departement ancienDepartement = employe.getRefDept();

        // Mettre à jour le département de l'employé
        employe.setRefDept(nouveauDepartement);

        // Sauvegarder les modifications dans la base de données
        repo.save(employe);

        // Mettre à jour la liste des employés des départements concernés
        if (ancienDepartement != null) {
            ancienDepartement.getEmployeList().remove(employe);
            drepo.save(ancienDepartement);
        }

        nouveauDepartement.getEmployeList().add(employe);
        drepo.save(nouveauDepartement);

        // Ajouter une entrée dans la table d'historique en utilisant le builder
        HistoriqueAffectation historique = HistoriqueAffectation.builder()
                .employe(employe) // L'employé concerné
                .departement(nouveauDepartement) // Le nouveau département
                .dateAffectation(LocalDate.now()) // Date actuelle
                .build();

        hrepo.save(historique);
    }

    @Override
    public double calculerMasseSalarialeEntreprise() {
        // Récupérer tous les employés de l'entreprise
        List<Employe> employes = repo.findAll();

        // Calculer la somme des salaires de tous les employés
        return employes.stream()
                .mapToDouble(Employe::getSalaire)
                .sum();
    }

    @Override
    public double calculerMasseSalarialeDepartement(String departementIdOrName) {
        // Recherche du département par ID ou par nom
        Departement departement = drepo.findById(departementIdOrName)
                .orElseGet(() -> drepo.findByNomdept(departementIdOrName)
                        .orElseThrow(() -> new DepartementNotFoundException("Département non trouvé")));

        // Récupérer la liste des employés du département
        List<Employe> employes = departement.getEmployeList();

        // Vérifier si la liste d'employés est vide
        if (employes == null || employes.isEmpty()) {
            throw new EmployeNotFoundException("Aucun employé trouvé dans ce département");
        }

        // Calculer la somme des salaires des employés du département
        return employes.stream()
                .mapToDouble(Employe::getSalaire)
                .sum();
    }


    @Override
    public List<employeDTO> getAllEmployes() {
        return repo.findAll()
                .stream()
                .map(employeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public employeDTO getEmployeById(Integer id) {
        return repo.findById(id)
                .map(employeMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Employé introuvable avec l'ID : " + id));
    }


    @Override
    public List<employeDTO> getEmployesWithSalaryGreaterThan(Float salaire) {
        return repo.findBySalaireGreaterThan(salaire)
                .stream()
                .map(employeMapper::toDTO)
                .collect(Collectors.toList());
    }
    @Override
    public departementEmployesDTO getEmployesByDepartement(String departementIdOrName) {
        // Recherche du département par ID ou par nom
        Departement departement = drepo.findByIddept(departementIdOrName)
                .orElseGet(() -> drepo.findByNomdept(departementIdOrName)
                        .orElseThrow(() -> new DepartementNotFoundException("Département non trouvé")));

        // Vérifier si le département a des employés
        List<Employe> employes = departement.getEmployeList();
        if (employes == null || employes.isEmpty()) {
            throw new EmployeNotFoundException("Aucun employé trouvé dans ce département");
        }

        // Conversion des employés en DTO
        List<employeDTO> employeDTOList = employes.stream()
                .map(employe -> employeMapper.toDTO(employe)) // Utilisation de la méthode toDTO
                .collect(Collectors.toList());

        // Création du DTO pour la réponse
        return departementEmployesDTO.builder()
                .iddept(departement.getIddept())
                .nomdept(departement.getNomdept())
                .employes(employeDTOList)
                .build();
    }

}
