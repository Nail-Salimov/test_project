package com.example.ws.services;

import com.example.ws.exception.NegativeDiscriminantException;
import com.example.ws.model.Equation;
import discriminant.ServiceStatus;
import org.springframework.stereotype.Service;


@Service
public class DiscriminantServiceImpl implements DiscriminantService{

    @Override
    public Equation calculate(Equation equation) {
        double a = equation.getA();
        double b = equation.getB();
        double c = equation.getC();

        double d = b * b - 4 * a * c;
        equation.setD(d);

        if (d > 0) {
            equation.setX1((-b - Math.sqrt(d)) / (2 * a));
            equation.setX2((-b + Math.sqrt(d)) / (2 * a));
            return equation;
        } else if (d == 0) {
            equation.setX1((-b - Math.sqrt(d)) / (2 * a));
            return equation;
        } else {
            ServiceStatus status = new ServiceStatus();
            status.setFormula(equation.getFormula());
            status.setD(String.valueOf(equation.getD()));
            throw new NegativeDiscriminantException("Отрицательный дискриминант", status);
        }
    }
}
