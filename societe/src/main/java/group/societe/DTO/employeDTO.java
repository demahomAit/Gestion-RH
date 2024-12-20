package group.societe.DTO;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class employeDTO {
    private Integer id;
    private String nomEmp;
    private Float salaire;
    private String refDeptId; // ID du département associé
}
