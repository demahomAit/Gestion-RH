package group.societe.controllers;

import group.societe.DTO.departementDTO;
import group.societe.exception.NoDepartementsFoundException;
import group.societe.services.departementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class departementController {

    private final departementService ds;

    // Injection du service departementService via le constructeur
    public departementController(departementService departementService) {
        this.ds = departementService;
    }

    /**
     * Endpoint pour obtenir le nombre d'employés par département.
     * @return Une map contenant l'ID du département comme clé et le nombre d'employés comme valeur.
     */
    @GetMapping("/employes-par-departement")
    public ResponseEntity<Map<String, Integer>> getNombreEmployesParDepartement() {
        try {
            // Appel du service pour obtenir le nombre d'employés par département
            Map<String, Integer> nombreEmployesParDepartement = ds.getNombreEmployesParDepartement();
            return ResponseEntity.ok(nombreEmployesParDepartement);
        } catch (Exception e) {
            // Gestion des erreurs générales
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Endpoint pour obtenir le département ayant le plus grand nombre d'employés.
     * @return Le DTO du département ayant le plus grand nombre d'employés.
     */
    @GetMapping("/most-employes")
    public ResponseEntity<departementDTO> getDepartementWithMostEmployes() {
        try {
            // Appel du service pour obtenir le département avec le plus grand nombre d'employés
            departementDTO departementDTO = ds.getDepartementWithMostEmployes();
            return ResponseEntity.ok(departementDTO);
        } catch (NoDepartementsFoundException e) {
            // Gestion de l'exception si aucun département n'est trouvé
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            // Gestion des erreurs générales
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Endpoint pour ajouter un nouveau département.
     * @param departementDTO Le DTO contenant les informations du département à ajouter.
     * @return Le DTO du département ajouté.
     */
    @PostMapping("/departements")
    public ResponseEntity<departementDTO> addDepartement(@RequestBody departementDTO departementDTO) {
        System.out.println("POST: Adding departement - " + departementDTO);
        // Appel du service pour ajouter le département
        departementDTO savedDepartement = ds.addDepartement(departementDTO);
        System.out.println("POST: Departement saved successfully");
        return ResponseEntity.ok(savedDepartement);
    }

    /**
     * Endpoint pour mettre à jour un département existant.
     * @param id L'ID du département à mettre à jour.
     * @param departementDTO Le DTO contenant les nouvelles informations du département.
     * @return Le DTO du département mis à jour.
     */
    @PutMapping("/departements/{id}")
    public ResponseEntity<departementDTO> updateDepartement(
            @PathVariable String id,
            @RequestBody departementDTO departementDTO
    ) {
        System.out.println("PUT: Updating departement with ID - " + id);
        // Appel du service pour mettre à jour le département
        departementDTO updatedDepartement = ds.updateDepartement(id, departementDTO);
        System.out.println("PUT: Departement updated successfully");
        return ResponseEntity.ok(updatedDepartement);
    }

    /**
     * Endpoint pour supprimer un département.
     * @param id L'ID du département à supprimer.
     * @return Une réponse HTTP 204 No Content si la suppression est réussie.
     */
    @DeleteMapping("/departements/{id}")
    public ResponseEntity<Void> deleteDepartement(@PathVariable String id) {
        System.out.println("DELETE: Deleting departement with ID - " + id);
        // Appel du service pour supprimer le département
        ds.deleteDepartement(id);
        System.out.println("DELETE: Departement deleted successfully");
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint pour obtenir la liste de tous les départements.
     * @return Une liste de DTO contenant les informations de tous les départements.
     */
    @GetMapping("/departements")
    public ResponseEntity<List<departementDTO>> getAllDepartements() {
        System.out.println("GET: Fetching all departements");
        // Appel du service pour obtenir tous les départements
        List<departementDTO> departements = ds.getAllDepartements();
        System.out.println("GET: Found " + departements.size() + " departements");
        return ResponseEntity.ok(departements);
    }

    /**
     * Endpoint pour obtenir un département par son ID.
     * @param id L'ID du département à récupérer.
     * @return Le DTO du département correspondant à l'ID.
     */
    @GetMapping("/departements/{id}")
    public ResponseEntity<departementDTO> getDepartementById(@PathVariable String id) {
        System.out.println("GET: Fetching departement with ID - " + id);
        // Appel du service pour obtenir le département par ID
        departementDTO departement = ds.getDepartementById(id);
        System.out.println("GET: Departement fetched successfully - " + departement);
        return ResponseEntity.ok(departement);
    }
}