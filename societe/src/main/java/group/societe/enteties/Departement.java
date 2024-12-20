package group.societe.enteties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Departement {

    @Id
    private String iddept ;

    private String nomdept;

    @OneToMany(mappedBy = "refDept", fetch = FetchType.LAZY)
    private List<Employe> employeList = new ArrayList<>();

    // Méthode equals basée sur iddept uniquement (attribut immuable)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Departement that = (Departement) o;
        return Objects.equals(iddept, that.iddept);
    }

    // Méthode hashCode basée sur iddept
    @Override
    public int hashCode() {
        return Objects.hash(iddept);
    }
}
