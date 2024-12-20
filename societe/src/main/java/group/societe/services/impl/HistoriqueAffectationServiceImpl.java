package group.societe.services.impl;

import group.societe.enteties.HistoriqueAffectation;
import group.societe.repo.HistoriqueAffectationRepository;
import group.societe.services.HistoriqueAffectationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoriqueAffectationServiceImpl implements HistoriqueAffectationService {

    @Autowired
    private HistoriqueAffectationRepository historiqueAffectationRepository;

    @Override
    public List<HistoriqueAffectation> getHistoriqueByEmployeId(Integer employeId) {
        return historiqueAffectationRepository.findAll().stream()
                .filter(historique -> historique.getEmploye().getId().equals(employeId))
                .collect(Collectors.toList());
    }

    @Override
    public List<HistoriqueAffectation> getHistoriqueByDepartementId(String departementId) {
        return historiqueAffectationRepository.findAll().stream()
                .filter(historique -> historique.getDepartement().getIddept().equals(departementId))
                .collect(Collectors.toList());
    }
}