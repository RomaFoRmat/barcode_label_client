package gui.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MainGroup {

    private Long idGroup;
    private Conversion idConversion;
    private LocalDate dateCreate;

}
