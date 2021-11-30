package gui.model.dto;

import gui.model.Code;
import gui.model.DateTable;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MainValueDTO {
    private Long idGroup;
    private Long idHead;
    private String value;
    private Double numberValue;
    private Code code;
    private LocalDateTime dateCreate;
    private String whoCreate;
    private Long laboratory;
}
