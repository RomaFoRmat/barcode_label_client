package gui.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class DateTable {

    private DateTableForeignKey dateTableForeignKey;

    private Long laboratory;

    private LocalDateTime dateCreate;

    private String whoCreate;

    private String ipAddressCreate;

    private LocalDateTime dateEdit;

    private String whoLastEdit;

    private String ipAddressEdit;

    @Data
    public static class DateTableForeignKey implements Serializable {

        private MainGroup mainGroup;

        private ForeignGroup foreignGroup;
    }
}
