package gui.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;


@Getter
@Setter
@ToString
public class TestLabel {

    private Long id_group;
    private Long id_foreign_group;
    private LocalDate date_create;
    private String code;
    private String construct;
    private String consumer_code;
    private String lot;
    private String part;
    private String rl;
    private String typeSpool;
    private String personal_rope;
    private Integer length;
    private String numberSpool;
    private Integer welds;
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
