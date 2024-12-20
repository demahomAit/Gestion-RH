package group.societe.repo;



import group.societe.enteties.HistoriqueAffectation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoriqueAffectationRepository extends JpaRepository<HistoriqueAffectation, Integer> {
}