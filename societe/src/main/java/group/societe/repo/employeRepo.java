package group.societe.repo;

import group.societe.enteties.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface employeRepo extends JpaRepository<Employe, Integer> {
    // Trouver les employés par nom
    List<Employe> findByNomEmp(String nomEmp);

    // Trouver les employés par département
    List<Employe> findByRefDept_Iddept(String idDept);

    // Trouver les employés avec un salaire supérieur à une certaine valeur
    List<Employe> findBySalaireGreaterThan(Float salaire);

    // Trouver les employés triés par salaire dans l'ordre décroissant
    List<Employe> findByRefDept_IddeptOrderBySalaireDesc(String idDept);
}
