package gui.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class MainGroup {

    private Long idGroup;
    private Conversion idConversion;
//    @JsonProperty("timestamp")
    private LocalDateTime dateCreate;

    @Override
    public String toString() throws NullPointerException {
        if (dateCreate != null) {
            return "ID: " + idGroup + " / " + "ДАТА: "
                    + dateCreate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        } else return idGroup.toString();
    }
}
