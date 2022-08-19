package com.example.ws.exception;

import discriminant.ServiceStatus;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;

import javax.xml.namespace.QName;

public class DetailSoapFaultDefinitionExceptionResolver extends SoapFaultMappingExceptionResolver {

    private static final QName FORMULA = new QName("formula");
    private static final QName D = new QName("D");

    @Override
    protected void customizeFault(Object endpoint, Exception ex, SoapFault fault) {
        if (ex instanceof NegativeDiscriminantException) {
            ServiceStatus status = ((NegativeDiscriminantException) ex).getServiceStatus();
            SoapFaultDetail detail = fault.addFaultDetail();
            detail.addFaultDetailElement(FORMULA).addText(status.getFormula());
            detail.addFaultDetailElement(D).addText(status.getD());
        }
    }
}
