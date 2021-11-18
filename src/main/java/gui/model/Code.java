package gui.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Code {

//    private String idCode;

    private CodePrimaryKey codePrimaryKey;
    private String code;
    private String description;
    private Boolean visible;

    @Data
    public static class CodePrimaryKey implements Serializable {

        private Long idCode;
        private Conversion conversion;
    }

    @Override
    public String toString() {
        return code + " - " + description;
    }
}
