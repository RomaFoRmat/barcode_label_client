package gui.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Limit {
    private LimitUniqueKey limitUniqueKey;
    private Boolean visible;

    @Data
    public static class LimitUniqueKey implements Serializable {
        private TestHead testHead;
        private Code.CodePrimaryKey codePK;
    }
}
