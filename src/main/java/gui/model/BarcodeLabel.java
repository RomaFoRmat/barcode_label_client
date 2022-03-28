package gui.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BarcodeLabel {

    private Long idForeignGroup;
    private Long idGroup;
    private String numberSpool;
    @JsonProperty("timestamp")
    private LocalDateTime dateCreate;
    private String code;
    private String construct;
    private String consumerCode;
    private String lot;
    private String part;
    private String rl;
//    private String typeSpool;
    private String length;
    private Integer welds;
    private Double torsion;
//    private Integer container;
//    private String personalRope;
//    private Integer numberRopeMachine;

}
