package gui.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Limit {
    private LimitUniqueKey limitUniqueKey;
    private Boolean visible;

    //    @Data
//    public static class LimitUniqueKey implements Serializable {
//        private TestHead testHead;
//        private Code.CodePrimaryKey codePK;
//    }
    @Data
    public static class LimitUniqueKey implements Serializable {
        private Long idTestHead;
        private Long idCode;
        private Long idConversion;
    }

    @Override
    public String toString() {
        return "idCode:" + limitUniqueKey.idCode + ", " + "idTestHead:" + limitUniqueKey.idTestHead + ", " +
                ", visible=" + visible ;
    }
}
