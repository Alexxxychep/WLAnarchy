package me.alexxxychep.wlanarchy.service;

import me.alexxxychep.wlanarchy.WLException;

public class ServiceException extends WLException {

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String message) {
        super(message);
    }
}
