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
    private Long idConversion;

    @Data
    public static class TestValuePrimaryKey implements Serializable {

        private Long idForeign;
        private Long idTestHead;
    }

}
