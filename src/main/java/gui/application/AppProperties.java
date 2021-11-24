package gui.application;


import gui.model.Personals;

public class AppProperties {
    public static Personals personals;
    public static String host;

    public static String getHost() {
        return host;
    }

    public static Personals getUser() {
        return personals;
    }

    public static void setHost(String host) {
        AppProperties.host = host;
    }
}
