package gui.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class TestValue {

    private TestValuePrimaryKey testValuePrimaryKey;
    private String textValue;
    private Double value;
    private Long idConversion;
    private LocalDate dateValue;

    @Data
    public static class TestValuePrimaryKey implements Serializable {

        private Long idForeign;
        private Long idTestHead;
    }

}
