package gui.util;

import com.jcraft.jsch.*;
import gui.application.AppProperties;
import gui.application.Main;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Sftp {
    public static class Connection {

        public static void check(String host, Integer port, String user, String password, File sourceFile) {
            try {
                JSch jsch = new JSch();

                Session session = jsch.getSession(user, host, port);
                session.setUserInfo(new MyUserInfo(password));
                session.connect();

                System.out.println("session connected");

                Channel channel = session.openChannel("sftp");
                channel.connect();

                System.out.println("channel connected");

//                ChannelSftp channelSftp = (ChannelSftp) channel;
//                try {
//                    System.out.println("start");
//                    time0 = System.currentTimeMillis();
//                    channelSftp.get(sourceFile, localFile, new MyProgressMonitor(sourceFile), ChannelSftp.OVERWRITE);
//                } catch (SftpException cause) {
//                    cause.printStackTrace();
//                }
//
//                channelSftp.exit();

                displayAllVersionsInDirectory(sourceFile);

                session.disconnect();
            } catch (Exception cause) {
                cause.printStackTrace();
            }
        }

        /**
         * Класс для авторизации на SSH-сервере.
         */
        private static class MyUserInfo implements UserInfo, UIKeyboardInteractive {
            private String password;

            public MyUserInfo(String password) {
                this.password = password;
            }

            public String getPassword() {
                return password;
            }

            public boolean promptYesNo(String str) {
                return true;
            }

            public String getPassphrase() {
                return null;
            }

            public boolean promptPassphrase(String message) {
                return true;
            }

            public boolean promptPassword(String message) {
                return true;
            }

            public void showMessage(String message) {
            }

            public String[] promptKeyboardInteractive(String destination, String name,
                                                      String instruction, String[] prompt, boolean[] echo) {
                return new String[]{password};
            }
        }


        private static void displayAllVersionsInDirectory(File dir) throws IOException {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    String[] versions = file.getName().split("-");
                    String res = versions[versions.length - 1];
                    System.out.println(res.replaceAll("\\.jar",""));
                    if (res != AppProperties.getVersion()){
                        TextFieldUtil.alertInformation("Приложение будет обновлено и запущено сразу после обновления!");
                        Platform.exit();
                        System.exit(0);
                        Desktop.getDesktop().open(new File("updater.jar"));
                    }
                }
            }
        }
    }
}
