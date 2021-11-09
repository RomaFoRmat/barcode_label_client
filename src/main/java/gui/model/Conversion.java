package gui.model;


import lombok.Data;

@Data
public class Conversion {

    private Long idConversion;
    private String nameConversion;
    private GroupConversion idGroup;
}
