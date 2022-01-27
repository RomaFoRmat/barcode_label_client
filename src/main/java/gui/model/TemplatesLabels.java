package gui.model;

import lombok.Data;

@Data
public class TemplatesLabels {
    private Long idTemplate;
    private Long idCode;
    private Boolean languageLabel;
    private Boolean construct;
    private Boolean code;
    private Boolean lr;
    private Boolean numberSpool;
    private Boolean datePrint;
    private Boolean part;
    private Boolean lot;
    private Boolean welds;
    private Boolean lengthSpool;
}
