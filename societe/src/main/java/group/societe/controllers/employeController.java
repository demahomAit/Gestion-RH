package group.societe.controllers;

import group.societe.DTO.departementEmployesDTO;
import group.societe.DTO.employeDTO;
import group.societe.exception.DepartementNotFoundException;
import group.societe.exception.EmployeNotFoundException;
import group.societe.services.employeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/employes")


public class employeController {

    private final employeService employeService;

    // Injection du service employeService via le constructeur
    public employeController(employeService employeService) {
        this.employeService = employeService;
    }

    /**
     * Endpoint pour calculer la masse salariale de l'entreprise.
     * @return La masse salariale totale de l'entreprise.
     */
    @GetMapping("/masse-salariale/entreprise")
    public ResponseEntity<Double> calculerMasseSalarialeEntreprise() {
        try {
            double masseSalariale = employeService.calculerMasseSalarialeEntreprise();
            return ResponseEntity.ok(masseSalariale);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Endpoint pour calculer la masse salariale d'un département.
     * @param departementIdOrName L'ID ou le nom du département.
     * @return La masse salariale du département.
     */
    @GetMapping("/masse-salariale/departement/{departementIdOrName}")
    public ResponseEntity<Double> calculerMasseSalarialeDepartement(
            @PathVariable String departementIdOrName) {
        try {
            double masseSalariale = employeService.calculerMasseSalarialeDepartement(departementIdOrName);
            return ResponseEntity.ok(masseSalariale);
        } catch (DepartementNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Endpoint pour obtenir la liste des employés d'un département.
     * @param departementIdOrName L'ID ou le nom du département.
     * @return Un DTO contenant les informations du département et la liste des employés.
     */
    @GetMapping("/departement/{departementIdOrName}")
    public ResponseEntity<departementEmployesDTO> getEmployesByDepartement(@PathVariable String departementIdOrName) {
        departementEmployesDTO departementEmployesDTO = employeService.getEmployesByDepartement(departementIdOrName);
        return ResponseEntity.ok(departementEmployesDTO);
    }

    /**
     * Endpoint pour ajouter un nouvel employé.
     * @param employeDTO Le DTO contenant les informations de l'employé à ajouter.
     * @return Un message de confirmation.
     */
    @PostMapping("/add")
    public ResponseEntity<String> addEmploye(@RequestBody employeDTO employeDTO) {
        try {
            employeService.addEmploye(employeDTO);
            return ResponseEntity.ok("Employé ajouté avec succès.");
        } catch (DepartementNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout de l'employé.");
        }
    }

    /**
     * Endpoint pour mettre à jour un employé existant.
     * @param employeId L'ID de l'employé à mettre à jour.
     * @param employeDTO Le DTO contenant les nouvelles informations de l'employé.
     * @return Un message de confirmation.
     */
    @PutMapping("/update/{employeId}")
    public ResponseEntity<String> updateEmploye(
            @PathVariable Integer employeId,
            @RequestBody employeDTO employeDTO) {
        try {
            employeService.updateEmploye(employeId, employeDTO);
            return ResponseEntity.ok("Employé mis à jour avec succès.");
        } catch (EmployeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DepartementNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la mise à jour de l'employé.");
        }
    }

    /**
     * Endpoint pour supprimer un employé.
     * @param employeId L'ID de l'employé à supprimer.
     * @return Un message de confirmation.
     */
    @DeleteMapping("/delete/{employeId}")
    public ResponseEntity<String> deleteEmploye(@PathVariable Integer employeId) {
        try {
            employeService.deleteEmploye(employeId);
            return ResponseEntity.ok("Employé supprimé avec succès.");
        } catch (EmployeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la suppression de l'employé.");
        }
    }

    /**
     * Endpoint pour obtenir le nombre total d'employés dans l'entreprise.
     * @return Le nombre total d'employés.
     */
    @GetMapping("/total")
    public ResponseEntity<Long> getTotalEmployes() {
        long totalEmployes = employeService.getTotalEmployes();
        return ResponseEntity.ok(totalEmployes);
    }

    /**
     * Endpoint pour réaffecter un employé à un nouveau département.
     * @param employeId L'ID de l'employé à réaffecter.
     * @param nouveauDepartementId L'ID du nouveau département.
     * @return Un message de confirmation.
     */
    @PutMapping("/reaffecter/{employeId}/{nouveauDepartementId}")
    public ResponseEntity<String> reaffecterEmploye(
            @PathVariable Integer employeId,
            @PathVariable String nouveauDepartementId) {
        try {
            employeService.reaffecterEmploye(employeId, nouveauDepartementId);
            return ResponseEntity.ok("Employé réaffecté avec succès.");
        } catch (EmployeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DepartementNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la réaffectation de l'employé.");
        }
    }

    /**
     * Endpoint pour obtenir la liste de tous les employés.
     * @return Une liste de DTO contenant les informations de tous les employés.
     */
    @GetMapping
    public ResponseEntity<List<employeDTO>> getAllEmployes() {
        System.out.println("GET: Fetching all employes");
        List<employeDTO> employes = employeService.getAllEmployes();
        System.out.println("GET: Found " + employes.size() + " employes");
        return ResponseEntity.ok(employes);
    }

    /**
     * Endpoint pour obtenir un employé par son ID.
     * @param id L'ID de l'employé à récupérer.
     * @return Le DTO de l'employé correspondant à l'ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<employeDTO> getEmployeById(@PathVariable Integer id) {
        System.out.println("GET: Fetching employe with ID - " + id);
        employeDTO employe = employeService.getEmployeById(id);
        System.out.println("GET: Employe fetched successfully - " + employe);
        return ResponseEntity.ok(employe);
    }

    /**
     * Endpoint pour obtenir la liste des employés ayant un salaire supérieur à une valeur donnée.
     * @param salaire Le salaire minimum.
     * @return Une liste de DTO contenant les informations des employés correspondants.
     */
    @GetMapping("/salaire-greater-than/{salaire}")
    public ResponseEntity<List<employeDTO>> getEmployesWithSalaryGreaterThan(@PathVariable Float salaire) {
        System.out.println("GET: Fetching employes with salary greater than - " + salaire);
        List<employeDTO> employes = employeService.getEmployesWithSalaryGreaterThan(salaire);
        System.out.println("GET: Found " + employes.size() + " employes with salary above " + salaire);
        return ResponseEntity.ok(employes);
    }
}