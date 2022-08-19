package com.example.ws.services;

import com.example.ws.exception.NegativeDiscriminantException;
import com.example.ws.model.Equation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class DiscriminantServiceImplTest {

    private static final DiscriminantServiceImpl service = new DiscriminantServiceImpl();

    @Test
    public void calculateEquationThenDiscriminantIsPositive() {
        Equation equation = new Equation(1.0, 4.0, 1.0);
        equation = service.calculate(equation);
        assertEquals(12.0, equation.getD());
        assertEquals(-3.732050807568877, equation.getX1());
        assertEquals(-0.2679491924311228, equation.getX2());
        assertEquals("1.0x^2+4.0x+1.0=0", equation.getFormula());
    }

    @Test
    public void calculateEquationThenDiscriminantIsZero() {
        Equation equation = new Equation(1.0, 4.0, 4.0);
        equation = service.calculate(equation);
        assertEquals(0.0, equation.getD());
        assertEquals(-2.0, equation.getX1());
        assertNull(equation.getX2());
        assertEquals("1.0x^2+4.0x+4.0=0", equation.getFormula());
    }

    @Test
    public void calculateEquationThenDiscriminantIsNegative() {
        Equation equation = new Equation(1.0, 4.0, 40.0);
        NegativeDiscriminantException exception = assertThrows(NegativeDiscriminantException.class, () -> service.calculate(equation));
        assertTrue(exception.getMessage().contains("Отрицательный дискриминант"));
        assertEquals(exception.getServiceStatus().getD(), "-144.0");
        assertEquals(exception.getServiceStatus().getFormula(), "1.0x^2+4.0x+40.0=0");
    }
}
