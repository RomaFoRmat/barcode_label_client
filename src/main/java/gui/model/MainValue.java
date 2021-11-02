package gui.model;

import lombok.Data;

import java.io.Serializable;

@Data

public class MainValue {

    private MainValuePrimaryKey mainValuePrimaryKey;
    private String value;
    private Float numberValue;

    public static class MainValuePrimaryKey implements Serializable {

        private Long idGroup;
        private Long idHead;

    }

}

