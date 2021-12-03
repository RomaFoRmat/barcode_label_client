package gui.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccessPersonal {

    private AccessPersonalPrimaryKey accessPersonalPrimaryKey;

    private String password;
    private Boolean accessInterData;
    private Boolean accessEditMainTable;
    private Boolean accessEditTextList;
    private Boolean accessEditForeignTable;
    private Boolean editMainTable;
    private Boolean editForeignTable;
    private Boolean passwordAccess;

    @Override
    public String toString() {
        return accessPersonalPrimaryKey.idLaboratory.toString();

    }

    @Data
    public static class AccessPersonalPrimaryKey implements Serializable {
        private Long idLaboratory;
        private Long idConversion;
        private Personals personals;
    }
}
