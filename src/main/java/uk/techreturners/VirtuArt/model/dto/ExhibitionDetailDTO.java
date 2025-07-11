package uk.techreturners.VirtuArt.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ExhibitionDetailDTO(
        String id,
        String title,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<ExhibitionItemDTO> exhibitionItems
) {
}