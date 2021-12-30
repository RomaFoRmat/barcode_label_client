package gui.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
public class TestLabel {

    private Long idGroup;
    private Long idForeignGroup;
    @JsonProperty("timestamp")
    private LocalDateTime dateCreate;
    private LocalDate dateRope;
    private String code;
    private String construct;
    private String consumerCode;
    private String lot;
    private String part;
    private String rl;
    private String typeSpool;
    private String personalRope;
    private Integer length;
    private String numberSpool;
    private Integer welds;
    private Integer numberRopeMachine;
    private Double numberWeldingMachine;
    private Double straightforwardness600_0;
    private Double straightforwardness600_1;
    private Double straightforwardness600_2;
    private Double straightforwardness600_3;
    private Double straightforwardness600_4;
    private Double straightforwardness600_5;
    private Double straightforwardness600Avg;
    private Double torsRope;
    private Double straightforwardnessRope;
    private Double straightforwardness300;
    private Double torsion;
    private String codeDefect;


}
