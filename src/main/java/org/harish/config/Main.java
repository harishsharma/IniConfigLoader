package org.harish.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author harish.sharma
 */
public class Main {
    public static void main(String[] args) throws LoadException, IOException {
        ConfigLoader loader = new DefaultConfigLoader();
        InputStream in = Main.class.getClassLoader().getResourceAsStream("configfile1");
        Reader reader = new InputStreamReader(in);
        Config cfg = loader.loadConfig(reader, "ubuntu", "production");
        System.out.println(cfg.getConfig("common.paid_users_size_limit"));
        System.out.println(cfg.getConfig("ftp.name"));
        System.out.println(cfg.getConfig("http.params"));
        System.out.println(cfg.getConfig("ftp.lastname"));
        System.out.println(cfg.getConfig("ftp.enabled"));
        System.out.println(cfg.getConfig("ftp.path"));
        System.out.println(cfg.getConfig("ftp"));
        System.out.println(cfg.getConfig("http"));
        in.close();
    }
}
