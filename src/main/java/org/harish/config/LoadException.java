package org.harish.config;

/**
 * {@link LoadException} represents the Error condition while loading the configuration.
 * 
 * @author harish.sharma
 *
 */
public class LoadException extends Exception {

    private static final long serialVersionUID = 1L;

    public LoadException(String message) {
        super(message);
    }

    public LoadException(Throwable cause) {
        super(cause);
    }
}
