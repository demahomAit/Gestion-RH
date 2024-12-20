package group.societe.repo;

import group.societe.enteties.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface departementRepo extends JpaRepository<Departement, String> {
    Optional<Departement> findByIddept(String iddept);
    Optional<Departement> findByNomdept(String nomdept);
}