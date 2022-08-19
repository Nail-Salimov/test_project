package com.example.ws.exception;

import discriminant.ServiceStatus;

public class NegativeDiscriminantException extends RuntimeException{

    private ServiceStatus serviceStatus;

    public NegativeDiscriminantException(String message, ServiceStatus serviceStatus) {
        super(message);
        this.serviceStatus = serviceStatus;
    }

    public NegativeDiscriminantException(String message, Throwable e, ServiceStatus serviceStatus) {
        super(message, e);
        this.serviceStatus = serviceStatus;
    }

    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(ServiceStatus serviceStatus) {
        this.serviceStatus = serviceStatus;
    }
}
