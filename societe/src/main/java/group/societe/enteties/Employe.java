package group.societe.enteties;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employe {
    @Id
    @GeneratedValue
    private Integer id ;

    private String nomEmp;

    private Float salaire;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "refDept")
    private Departement refDept;

    // Méthode equals basée sur id uniquement (attribut immuable après génération)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employe employee = (Employe) o;
        return Objects.equals(id, employee.id);
    }

    // Méthode hashCode basée sur id
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
