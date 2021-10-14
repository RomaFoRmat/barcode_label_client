package gui.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
public class TestLabel {
    private Long id_group;

    private Long id_foreing_group;

    private LocalDate date_create;

    private String code;

    private String construct;

    private String lot;

    private String part;

    private String rl;

    private String typeSpool;

    private String personal_rope;

    private int length;

    private String numberSpool;

    private int welds;

    private Float straightforwardness1;

    private Float straightforwardness2;

    private Float straightforwardness3;

    private Float straightforwardness4;

    private Float straightforwardness5;

    private Float straightforwardnessAvg;

    private Float torsRope;

    private Float straightforwardnessRope;

    private Float straightforwardness300;

    private Float torsion;

    private String codeDefect;


}
