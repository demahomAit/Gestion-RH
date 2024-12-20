package group.societe.mappers;

import group.societe.DTO.departementDTO;
import group.societe.enteties.Departement;

public class departementMapper {

    // Convertir une entité vers un DTO
    public static departementDTO toDTO(Departement departement) {
        if (departement == null) return null;

        return departementDTO.builder()
                .iddept(departement.getIddept())
                .nomdept(departement.getNomdept())
                .build();
    }

    // Convertir un DTO vers une entité
    public static Departement toEntity(departementDTO dto) {
        if (dto == null) return null;

        return Departement.builder()
                .iddept(dto.getIddept())
                .nomdept(dto.getNomdept())
                .build(); // Les employés devront être ajoutés manuellement si nécessaire
    }
}
