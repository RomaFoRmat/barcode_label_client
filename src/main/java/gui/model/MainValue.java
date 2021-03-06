package gui.model;

import lombok.Data;


import java.io.Serializable;

@Data

public class MainValue {

    private MainValuePrimaryKey mainValuePrimaryKey;
    private String value;
    private Double numberValue;

    @Data
    public static class MainValuePrimaryKey implements Serializable {

        private Long idGroup;
        private Long idHead;

    }

    @Override
    public String toString() {
        return String.valueOf(numberValue);
    }
}

