package com.example.calculatrice;

import java.math.BigDecimal;
import java.math.MathContext;

/** The four operations supported by the calculator. */
enum Operation {
    ADD("add", "+", "Addition"),
    SUB("sub", "−", "Soustraction"),
    MUL("mul", "×", "Multiplication"),
    DIV("div", "÷", "Division");

    final String code;
    final String symbol;
    final String label;

    Operation(String code, String symbol, String label) {
        this.code = code;
        this.symbol = symbol;
        this.label = label;
    }

    static Operation fromCode(String code) {
        for (Operation o : values()) {
            if (o.code.equals(code)) {
                return o;
            }
        }
        throw new IllegalArgumentException("Opération inconnue : " + code);
    }

    BigDecimal apply(BigDecimal a, BigDecimal b) {
        return switch (this) {
            case ADD -> a.add(b);
            case SUB -> a.subtract(b);
            case MUL -> a.multiply(b);
            case DIV -> {
                if (b.signum() == 0) {
                    throw new ArithmeticException("Division par zéro impossible");
                }
                yield a.divide(b, MathContext.DECIMAL64);
            }
        };
    }
}
