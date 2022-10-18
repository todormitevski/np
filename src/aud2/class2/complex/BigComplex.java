package aud2.class2.complex;

import java.math.BigDecimal;

public class BigComplex {
    private BigDecimal realPart;
    private BigDecimal imaginaryPart;

    public BigComplex() {
    }

    public BigComplex(BigDecimal realPart, BigDecimal imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    public BigComplex add(BigComplex complexNum){
        return new BigComplex(this.realPart.add(complexNum.realPart), this.imaginaryPart.add(complexNum.imaginaryPart));
    }

    @Override
    public String toString() {
        return "BigComplex{" +
                "realPart=" + realPart +
                ", imaginaryPart=" + imaginaryPart +
                '}';
    }
}
