package com.example.client.exception;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscriminantException extends RuntimeException{

    private String discriminant;
    private String formula;

    public DiscriminantException(String s, String formula, String discriminant) {
        super(s);
        this.discriminant = discriminant;
        this.formula = formula;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
