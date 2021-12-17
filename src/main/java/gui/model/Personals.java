package gui.model;

import lombok.Data;

@Data
public class Personals {
    private Long idPersonal;

    private String fio;

    private Integer personnelNumber;

    private String password;

    private GroupsOfPersonal groupsOfPersonal;

    private Laboratory laboratory;


    @Override
    public String toString() {
        return "idPersonal=" + idPersonal +
                ", ФИО=" + fio +
                ", Персональный=" + personnelNumber +
                ", Пароль= " + password +
                ", groupsOfPersonal=" + groupsOfPersonal.getNameGroup() +
                " (idGroups=" + groupsOfPersonal.getIdGroup() + ")" +
                ", laboratory= " + laboratory;
    }
}
