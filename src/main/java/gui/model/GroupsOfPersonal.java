package gui.model;

import lombok.Data;

@Data
public class GroupsOfPersonal {

    private Long idGroup;
    private String nameGroup;

    @Override
    public String toString() {
        return String.valueOf(idGroup);
    }
}
