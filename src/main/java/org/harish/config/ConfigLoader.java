package org.harish.config;

import java.io.File;
import java.io.Reader;

/**
 * {@link ConfigLoader} contains the methods for loading configuration from different sources.
 * 
 * @author harish.sharma
 *
 */
public interface ConfigLoader {

    /**
     * Load configuration from given file present at fileLocation.
     * 
     * @param fileName
     * @return
     * @throws LoadException if unable to find the file or parsing is failed.
     */
    public Config loadConfig(final String fileLocation) throws LoadException;

    public Config loadConfig(final String fileLocation, final String... overrides) throws LoadException;

    /**
     * Load configuration from given file.
     * 
     * @param file
     * @return
     * @throws LoadException
     */
    public Config loadConfig(final File file) throws LoadException;

    public Config loadConfig(final File file, final String... overrides) throws LoadException;

    /**
     * Load configuration from given {@link Reader}.
     * 
     * @param reader
     * @return
     * @throws LoadException
     */
    public Config loadConfig(final Reader reader) throws LoadException;

    public Config loadConfig(final Reader reader, final String... overrides) throws LoadException;
}
