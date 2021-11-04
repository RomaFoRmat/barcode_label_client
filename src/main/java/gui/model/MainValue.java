package gui.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data

public class MainValue {

    private MainValuePrimaryKey mainValuePrimaryKey;
    private String value;
    private Float numberValue;

    @Data
    public static class MainValuePrimaryKey implements Serializable {

        private Long idGroup;
        private Long idHead;

    }

}

