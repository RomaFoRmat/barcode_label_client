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
        return "Personals:" +
                "idPersonal=" + idPersonal +
                ", ФИО='" + fio + '\'' +
                ", Персональный=" + personnelNumber +
                ", пароль='" + password + '\'' +
                ", groupsOfPersonal=" + groupsOfPersonal +
                ", laboratory=" + laboratory;
    }
}
