package com.example.client.wsdl;

import com.example.client.exception.DiscriminantException;

import discriminant.GetDiscriminantRequest;
import discriminant.GetDiscriminantResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.client.SoapFaultClientException;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.w3c.dom.Node;

import javax.xml.transform.dom.DOMSource;

public class DiscriminantClient extends WebServiceGatewaySupport {

    public GetDiscriminantResponse getDiscriminant(double a, double b, double c) {
        GetDiscriminantRequest request = new GetDiscriminantRequest();
        request.setA(a);
        request.setB(b);
        request.setC(c);

        try {
            GetDiscriminantResponse response = (GetDiscriminantResponse) getWebServiceTemplate()
                    .marshalSendAndReceive("http://localhost:8080/ws/discriminant.wsdl",
                            request, new SoapActionCallback("http://discriminant/getDiscriminantRequest"));
            return response;
        } catch (SoapFaultClientException e) {

            SoapFaultDetail soapFaultDetail = e.getSoapFault().getFaultDetail();
            DOMSource sourceNode = (DOMSource) soapFaultDetail.getSource();

            Node fault = sourceNode.getNode().getChildNodes().item(0);
            String formula = fault.getFirstChild().getNodeValue();
            String discriminant = fault.getNextSibling().getFirstChild().getNodeValue();

            throw new DiscriminantException("Отрицательный дискриминант", formula, discriminant);
        }
    }
}

