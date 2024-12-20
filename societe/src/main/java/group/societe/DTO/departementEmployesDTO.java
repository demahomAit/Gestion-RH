package group.societe.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class departementEmployesDTO {
    private String iddept; // ID du département
    private String nomdept; // Nom du département
    private List<employeDTO> employes; // Liste des employés du département
}