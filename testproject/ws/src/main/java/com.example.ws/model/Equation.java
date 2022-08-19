package com.example.ws.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Equation {

    private Double a;
    private Double b;
    private Double c;
    private Double D;
    private Double x1;
    private Double x2;

    public Equation(Double a, Double b, Double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public String getFormula(){
        return  a + "x^2+"+ b +"x+" + c + "=0";
    }
}
