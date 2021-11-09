package gui.model;

import lombok.Data;


import java.time.LocalDateTime;

@Data
public class MainGroup {

    private Long idGroup;
    private Conversion idConversion;
    private LocalDateTime dateCreate;

}
