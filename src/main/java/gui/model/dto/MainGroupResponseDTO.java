package gui.model.dto;

import gui.model.DateTable;
import gui.model.MainGroup;
import lombok.Data;

@Data
public class MainGroupResponseDTO {
    private MainGroup mainGroup;
    private DateTable dateTable;
}
