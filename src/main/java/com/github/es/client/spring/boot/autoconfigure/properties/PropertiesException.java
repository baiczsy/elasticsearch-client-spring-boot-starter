package com.github.es.client.spring.boot.autoconfigure.properties;

/**
 * @author wangl
 * @date 2019-05-13
 */
public class PropertiesException extends RuntimeException {

    public PropertiesException(String message) {
        super(message);
    }

    public PropertiesException(String message, Throwable cause) {
        super(message, cause);
    }

    public PropertiesException(Throwable cause) {
        super(cause);
    }
}
