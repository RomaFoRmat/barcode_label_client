package gui.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Code {

    private String idCode;
    private String code;
    private String description;
    private Conversion conversion;
    private LocalDate dateEdit;
    private String codeSap;
    private Boolean visible;
}
