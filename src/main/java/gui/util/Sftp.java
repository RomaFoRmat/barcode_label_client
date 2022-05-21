package gui.util;

import com.jcraft.jsch.*;
import gui.model.Constants;
import org.apache.commons.collections4.SplitMapUtils;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import java.util.List;

public class Sftp {
    public static class Connection {

        public static void check(String host, Integer port, String user, String password, String sourceDir) {
            try {
                JSch jsch = new JSch();

                Session session = jsch.getSession(user, host, port);
                session.setUserInfo(new MyUserInfo(password));
                session.connect();

                System.out.println("session connected");

                Channel channel = session.openChannel("sftp");
                channel.connect();

                System.out.println("channel connected");

                ChannelSftp channelSftp = (ChannelSftp) channel;
                getMaxVersionFile(channelSftp, sourceDir);

                channelSftp.exit();
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

//        public static double getMaxVersion(File dir) {
//            File[] files = dir.listFiles();
//            double maxVersion = 0;
//            int maxVersionIndex = 0;
//            if (files != null) {
//                for (File file : files) {
//                    if (file.isFile()) {
//                        String name = file.getName().replace(".jar", "");
//                        String[] result = name.split("-");
//                        double version = Double.parseDouble(result[result.length - 1]);
//                        if (version > maxVersion) {
//                            maxVersion = version;
//                        }
//                    }
//                }
////            System.out.println(maxVersion);
//            } else {
//                System.out.println("Данной директории не существует");
//            }
//            return maxVersion;
//        }


        public static double getMaxVersionFile(ChannelSftp channelSftp, String dir) throws SftpException {
            List<ChannelSftp.LsEntry> files = channelSftp.ls(dir);
            double maxVersion = 0;
            for (ChannelSftp.LsEntry entry : files) {
                if (!entry.getAttrs().isDir()) {
                    String path = entry.getFilename();
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
    }
