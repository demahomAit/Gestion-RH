package group.societe.controllers;

import group.societe.enteties.HistoriqueAffectation;
import group.societe.services.HistoriqueAffectationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/historique")
public class HistoriqueAffectationController {

    @Autowired
    private HistoriqueAffectationService historiqueAffectationService;

    @GetMapping("/employe/{employeId}")
    public ResponseEntity<List<HistoriqueAffectation>> getHistoriqueByEmployeId(@PathVariable Integer employeId) {
        List<HistoriqueAffectation> historique = historiqueAffectationService.getHistoriqueByEmployeId(employeId);
        return ResponseEntity.ok(historique);
    }

    @GetMapping("/departement/{departementId}")
    public ResponseEntity<List<HistoriqueAffectation>> getHistoriqueByDepartementId(@PathVariable String departementId) {
        List<HistoriqueAffectation> historique = historiqueAffectationService.getHistoriqueByDepartementId(departementId);
        return ResponseEntity.ok(historique);
    }
}