package org.harish.config;

/**
 * @author harish.sharma
 */
public class Main {
    public static void main(String[] args) throws LoadException {
        ConfigLoader loader = new DefaultConfigLoader();
        Config cfg = loader.loadConfig("src/main/resources/configfile1", "ubuntu", "production");
        System.out.println(cfg.getConfig("common.paid_users_size_limit"));
        System.out.println(cfg.getConfig("ftp.name"));
        System.out.println(cfg.getConfig("http.params"));
        System.out.println(cfg.getConfig("ftp.lastname"));
        System.out.println(cfg.getConfig("ftp.enabled"));
        System.out.println(cfg.getConfig("ftp.path"));
        System.out.println(cfg.getConfig("ftp"));
        System.out.println(cfg.getConfig("http"));
    }
}
