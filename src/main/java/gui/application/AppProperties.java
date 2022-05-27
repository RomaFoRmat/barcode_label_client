package gui.application;


import gui.model.Personals;

public class AppProperties {
    public static Personals personals;
    public static String host;
    public static String version;
    public static String ip;

    public static String getHost() {
        return host;
    }

    public static Personals getUser() {
        return personals;
    }

    public static void setHost(String host) {
        AppProperties.host = host;
    }

    public static String getVersion() {
        return version;
    }

    public static void setVersion(String version) {
        AppProperties.version = version;
    }

    public static String getIp() {
        return ip;
    }

    public static void setIp(String ip) {
        AppProperties.ip = ip;
    }
}
