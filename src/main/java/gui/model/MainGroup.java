package gui.model;

import lombok.Data;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class MainGroup {

    private Long idGroup;
    private Conversion idConversion;
    private LocalDateTime dateCreate;


//    @Override
//    public String toString() {
//        return  idGroup + " / " + "ДАТА: " + dateCreate;
//    }

    @Override
    public String toString() throws NullPointerException {
        return idGroup + " / " + "ДАТА: "
                + dateCreate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
}
