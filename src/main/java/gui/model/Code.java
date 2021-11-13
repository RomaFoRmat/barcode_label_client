package gui.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Code {

//    private String idCode;
private Long idCode;
    private String code;
    private String description;
    private Conversion conversion;
    private Boolean visible;
}
