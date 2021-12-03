package gui.model.dto;

import gui.model.DateTable;
import gui.model.ForeignGroup;
import lombok.Data;

@Data
public class ForeignGroupResponseDTO {
    private ForeignGroup foreignGroup;
    private DateTable dateTable;
}
