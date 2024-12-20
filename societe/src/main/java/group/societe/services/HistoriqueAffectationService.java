package group.societe.services;

import group.societe.enteties.HistoriqueAffectation;

import java.util.List;

public interface HistoriqueAffectationService {
    List<HistoriqueAffectation> getHistoriqueByEmployeId(Integer employeId);
    List<HistoriqueAffectation> getHistoriqueByDepartementId(String departementId);
}