package gui.model;

import lombok.Data;

@Data
public class TemplatesLabels {
    private Long idTemplate;
    private Long idCode;
    public Boolean languageLabel;
    public Boolean construct;
    public Boolean code;
    public Boolean lr;
    public Boolean numberSpool;
    public Boolean datePrint;
    public Boolean part;
    public Boolean lot;
    public Boolean welds;
    public Boolean lengthSpool;
}
