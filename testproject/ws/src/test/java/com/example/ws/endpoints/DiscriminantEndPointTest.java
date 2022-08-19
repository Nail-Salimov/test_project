package com.example.ws.endpoints;

import com.example.ws.configs.WebServiceConfig;
import com.example.ws.exception.NegativeDiscriminantException;
import com.example.ws.model.Equation;
import com.example.ws.services.DiscriminantService;
import discriminant.ServiceStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.webservices.server.WebServiceServerTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;


import java.io.IOException;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.*;


@WebServiceServerTest
@ContextConfiguration(classes = WebServiceConfig.class)
public class DiscriminantEndPointTest {

    @Autowired
    private MockWebServiceClient client;

    @MockBean
    private DiscriminantService discriminantService;

    @Test
    public void getXmlRequestWhenDiscriminantIsPositive() throws IOException {
        Equation calculatedEquation = new Equation(1.0, 4.0, 1.0);
        calculatedEquation.setD(12.0);
        calculatedEquation.setX1(-3.732050807568877);
        calculatedEquation.setX2(-0.2679491924311228);

        when(discriminantService.calculate(any())).thenReturn(calculatedEquation);

        StringSource request = new StringSource(
                "<dis:getDiscriminantRequest xmlns:dis=\"http://discriminant\">\n" +
                        "         <dis:a>1</dis:a>\n" +
                        "         <dis:b>4</dis:b>\n" +
                        "         <dis:c>1</dis:c>\n" +
                        "</dis:getDiscriminantRequest>"
        );

        StringSource expectedResponse = new StringSource(
                "<ns2:getDiscriminantResponse xmlns:ns2=\"http://discriminant\">" +
                        "<ns2:formula>1.0x^2+4.0x+1.0=0</ns2:formula>" +
                        "<ns2:D>12.0</ns2:D>" +
                        "<ns2:x1>-3.732050807568877</ns2:x1>" +
                        "<ns2:x2>-0.2679491924311228</ns2:x2>" +
                        "</ns2:getDiscriminantResponse>"
        );

        client.sendRequest(withPayload(request))
                .andExpect(noFault())
                .andExpect(validPayload(new ClassPathResource("discriminant.xsd")))
                .andExpect(payload(expectedResponse));
    }

    @Test
    public void getXmlRequestWhenDiscriminantIsZero() throws IOException {
        Equation calculatedEquation = new Equation(1.0, 4.0, 4.0);
        calculatedEquation.setD(0.0);
        calculatedEquation.setX1(-2.0);

        when(discriminantService.calculate(any())).thenReturn(calculatedEquation);

        StringSource request = new StringSource(
                "<dis:getDiscriminantRequest xmlns:dis=\"http://discriminant\">\n" +
                        "         <dis:a>1</dis:a>\n" +
                        "         <dis:b>4</dis:b>\n" +
                        "         <dis:c>4</dis:c>\n" +
                        "</dis:getDiscriminantRequest>"
        );

        StringSource expectedResponse = new StringSource(
                "<ns2:getDiscriminantResponse xmlns:ns2=\"http://discriminant\">" +
                        "<ns2:formula>1.0x^2+4.0x+4.0=0</ns2:formula>" +
                        "<ns2:D>0.0</ns2:D>" +
                        "<ns2:x1>-2.0</ns2:x1>" +
                        "</ns2:getDiscriminantResponse>"
        );

        client.sendRequest(withPayload(request))
                .andExpect(noFault())
                .andExpect(payload(expectedResponse));
    }

    @Test
    public void getXmlRequestWhenDiscriminantIsNegative() throws IOException {
        ServiceStatus status = new ServiceStatus();
        status.setFormula("1.0x^2+4.0x+40.0=0");
        status.setD("-144.0");

        when(discriminantService.calculate(any())).thenThrow(new NegativeDiscriminantException("Отрицательный дискриминант", status));

        StringSource request = new StringSource(
                "<dis:getDiscriminantRequest xmlns:dis=\"http://discriminant\">\n" +
                        "         <dis:a>1</dis:a>\n" +
                        "         <dis:b>4</dis:b>\n" +
                        "         <dis:c>40</dis:c>\n" +
                        "</dis:getDiscriminantRequest>"
        );

        client.sendRequest(withPayload(request))
                .andExpect(serverOrReceiverFault("Отрицательный дискриминант"));
    }
}
