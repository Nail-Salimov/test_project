package com.example.ws.endpoints;

import com.example.ws.model.Equation;
import com.example.ws.services.DiscriminantService;
import discriminant.GetDiscriminantRequest;
import discriminant.GetDiscriminantResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


@Endpoint
public class DiscriminantEndPoint {


    private final DiscriminantService discriminantService;

    private static final String NAMESPACE_URI = "http://discriminant";

    @Autowired
    public DiscriminantEndPoint(DiscriminantService discriminantService) {
        this.discriminantService = discriminantService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDiscriminantRequest")
    @ResponsePayload
    public GetDiscriminantResponse getDiscriminant(@RequestPayload GetDiscriminantRequest request) {
        GetDiscriminantResponse response = new GetDiscriminantResponse();
        Equation equation = Equation.builder()
                .a(request.getA())
                .b(request.getB())
                .c(request.getC()).build();
        equation = discriminantService.calculate(equation);

        response.setD(doubleToString(equation.getD()));
        response.setX1(doubleToString(equation.getX1()));
        response.setX2(doubleToString(equation.getX2()));
        response.setFormula(equation.getFormula());
        return response;
    }

    private String doubleToString(Double value){
        return value == null? null : String.valueOf(value);
    }
}
