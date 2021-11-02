package gui.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
public class TestValue {

    private TestValuePrimaryKey testValuePrimaryKey;
    private String textValue;
    private Double value;
    private Integer idConversion;

    public static class TestValuePrimaryKey implements Serializable {

        private Long idForeign;
        private Long idTestHead;
    }

}
