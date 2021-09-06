package gui.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class TestLabel {
    private Long id_group;

    private Date date_create;

    private String code;

    private int lot;

    private String part;

    private String rl;

    private String typeSpool;

    private String personal_rope;

    private int length;

    private String numberSpool;

    private int welds;

    private Integer straightforwardness1;

    private Integer straightforwardness2;

    private Integer straightforwardness3;

    private Integer straightforwardness4;

    private Integer straightforwardness5;

    private Integer straightforwardnessAvg;

    private Integer torsRope;

    private Integer straightforwardnessRope;

    private Integer straightforwardness400;

    private Integer torsion;

    private String codeDefect;


}
