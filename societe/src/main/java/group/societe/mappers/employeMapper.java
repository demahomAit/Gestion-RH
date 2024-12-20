package group.societe.mappers;


import group.societe.DTO.employeDTO;
import group.societe.enteties.Employe;

public class employeMapper {

    // Convertir une entité vers un DTO
    public static employeDTO toDTO(Employe employe) {
        if (employe == null) return null;

        return employeDTO.builder()
                .id(employe.getId())
                .nomEmp(employe.getNomEmp())
                .salaire(employe.getSalaire())
                .refDeptId(employe.getRefDept() != null ? employe.getRefDept().getIddept() : null)
                .build();
    }

    // Convertir un DTO vers une entité
    public static Employe toEntity(employeDTO dto) {
        if (dto == null) return null;

        return Employe.builder()
                .id(dto.getId())
                .nomEmp(dto.getNomEmp())
                .salaire(dto.getSalaire())
                .build(); // Le département devra être ajouté séparément
    }
}
