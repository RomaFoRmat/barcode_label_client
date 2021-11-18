package gui.model.dto;

import gui.model.Code;
import lombok.Data;

@Data
public class MainValueDTO {
    private Long idGroup;
    private Long idHead;
    private String value;
    private Double numberValue;
    private Code code;
}
