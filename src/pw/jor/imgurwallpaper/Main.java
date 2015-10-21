package pw.jor.imgurwallpaper;

import pw.jor.imgurwallpaper.gui.GUI;

public class Main {

    public static GUI gui;

    public static void main(String[] args) {

        // make https work with windows
        java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        System.setProperty(
                "java.protocol.handler.pkgs",
                "com.sun.net.ssl.internal.www.protocol");

        // and aWAY we GO!!!
        gui = new GUI();
    }
}