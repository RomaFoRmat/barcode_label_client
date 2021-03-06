package gui.util;

import com.jcraft.jsch.*;
import gui.model.Constants;
import java.util.List;

public class SftpUtil {

        public static boolean check(String host, Integer port, String user, String password, String sourceDir) {
            try {
                JSch jsch = new JSch();

                Session session = jsch.getSession(user, host, port);        //создаём сессию
                session.setUserInfo(new MyUserInfo(password));              //авто-подстановка пароля
                session.connect();                                          //соединение
                System.out.println("Session connected");

                Channel channel = session.openChannel("sftp");         //открываем канал для передачи файлов
                channel.connect();                                          //соединение
                System.out.println("Channel connected");

                ChannelSftp channelSftp = (ChannelSftp) channel;
                getMaxVersionFile(channelSftp, sourceDir);                  // № max версии

                channelSftp.exit();
                session.disconnect();
            } catch (Exception cause) {
                System.out.println("Cannot make connection to SFTP server | Directory not found | Update check failed");
                return false;
            }
            return true;
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

        public static double getMaxVersionFile(ChannelSftp channelSftp, String dir) throws SftpException {
            List<ChannelSftp.LsEntry> files = channelSftp.ls(dir);
            double maxVersion = 0;
            for (ChannelSftp.LsEntry entry : files) {
                if (!entry.getAttrs().isDir()) {
                    String name = entry.getFilename().replace(".jar", "");
                    String[] result = name.split("-");
                    double version = Double.parseDouble(result[result.length - 1]);
                    if (version > maxVersion) {
                        maxVersion = version;
                    }
                }
            }
            Constants.MAX_VERSION = maxVersion;
            System.out.println("Актуальная версия: " + maxVersion);
            return maxVersion;
            }
    }
