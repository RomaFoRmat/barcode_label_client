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
    private int length;
    private String numberSpool;
    private int welds;
    private Double straightforwardness1;
    private Double straightforwardness2;
    private Double straightforwardness3;
    private Double straightforwardness4;
    private Double straightforwardness5;
    private Double straightforwardnessAvg;
    private Double torsRope;
    private Double straightforwardnessRope;
    private Double straightforwardness300;
    private Double torsion;
    private String codeDefect;


}
