package gui.model;

import lombok.Data;

@Data
public class TestValueDTO {
    private Long idForeignGroup;
    private MainGroup mainGroup;
    private String textValue;
    private Double value;
    private Long idConversion;
    private Long idTestHead;
}
