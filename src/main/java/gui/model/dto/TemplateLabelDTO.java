package gui.model.dto;

import gui.model.TemplatesLabels;
import lombok.Data;

@Data
public class TemplateLabelDTO {
    private TemplatesLabels templatesLabels;
    private String kod;

    public String getLanguageLabel(){
        return convertBooleanToString(templatesLabels.getLanguageLabel());
    }
    public String getConstruct(){
        return convertBooleanToString(templatesLabels.getConstruct());
    }
    public String getCode(){
        return convertBooleanToString(templatesLabels.getCode());
    }
    public String getLr(){
        return convertBooleanToString(templatesLabels.getLr());
    }
    public String getNumberSpool(){
        return convertBooleanToString(templatesLabels.getNumberSpool());
    }
    public String getDatePrint(){
        return convertBooleanToString(templatesLabels.getDatePrint());
    }
    public String getPart(){
        return convertBooleanToString(templatesLabels.getPart());
    }
    public String getLot(){
        return convertBooleanToString(templatesLabels.getLot());
    }
    public String getWelds(){
        return convertBooleanToString(templatesLabels.getWelds());
    }
    public String getLengthSpool(){
        return convertBooleanToString(templatesLabels.getLengthSpool());
    }


    private String convertBooleanToString(Boolean value) {
        if(value.equals(Boolean.FALSE)){
            return "-";
        } else return "+";
    }
}
