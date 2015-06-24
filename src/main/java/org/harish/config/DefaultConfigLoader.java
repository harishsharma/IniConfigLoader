package org.harish.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;

/**
 * {@link DefaultConfigLoader} loads the configuration and returns the {@link Config} containing the loaded
 * configurations.
 * 
 * @author harish.sharma
 *
 */
public class DefaultConfigLoader implements ConfigLoader {

    /**
     * @see org.harish.config.ConfigLoader#loadConfig(java.lang.String)
     */
    @Override
    public Config loadConfig(String fileLocation) throws LoadException {
        String[] overrides = new String[0];
        return loadConfig(fileLocation, overrides);
    }


    /**
     * @see org.harish.config.ConfigLoader#loadConfig(java.io.File)
     */
    @Override
    public Config loadConfig(File file) throws LoadException {
        String[] overrides = new String[0];
        return loadConfig(file, overrides);
    }


    /**
     * @see org.harish.config.ConfigLoader#loadConfig(java.io.Reader)
     */
    @Override
    public Config loadConfig(Reader reader) throws LoadException {
        String[] overrides = new String[0];
        return loadConfig(reader, overrides);
    }


    @Override
    public Config loadConfig(String fileLocation, String... overrides) throws LoadException {
        if (overrides == null) throw new LoadException("Overrides cannot be null");
        File file;
        try {
            file = new File(fileLocation);
        } catch (Exception e) {
            throw new LoadException(e);
        }
        return loadConfig(file, overrides);
    }


    @Override
    public Config loadConfig(File file, String... overrides) throws LoadException {
        if (overrides == null) throw new LoadException("Overrides cannot be null");
        if (!file.exists() || !file.isFile())
            throw new LoadException("Error occured while loading file " + file.getName());

        BufferedReader br;
        try {
            br = Files.newBufferedReader(file.toPath(), Charset.defaultCharset());
        } catch (Exception e) {
            throw new LoadException(e);
        }
        return loadConfig(br, overrides);
    }


    @Override
    public Config loadConfig(Reader reader, String... overrides) throws LoadException {
        if (overrides == null) throw new LoadException("Overrides cannot be null");
        BufferedReader br = new BufferedReader(reader);
        Config result;
        try {
            result = Parser.createConfig(br, overrides);
            br.close();
        } catch (IOException e) {
            throw new LoadException(e);
        }
        return result;
    }
}
