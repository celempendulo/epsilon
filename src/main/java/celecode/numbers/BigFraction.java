package celecode.numbers;


import java.math.BigDecimal;
import java.math.BigInteger;

public class BigFraction implements Comparable<BigFraction>
{
    private BigInteger numerator;
    private BigInteger denominator;


    public BigFraction(BigInteger numerator)
    {
        this(numerator, BigInteger.ONE);
    }

    public BigFraction(BigInteger numerator, BigInteger denominator)
    {
        if(denominator == BigInteger.ZERO)
        {
            throw new IllegalArgumentException("Zero as denominator is not allowed");
        }
        this.numerator = numerator;
        this.denominator = denominator;
        reduce();
    }

    private void reduce()
    {
        if(numerator.signum() == denominator.signum() && numerator.signum()==-1)
        {
            numerator = numerator.negate();
            denominator = denominator.negate();
        }
        BigInteger gcd = numerator.gcd(denominator);
        numerator = numerator.divide(gcd);
        denominator = denominator.divide(gcd);
    }

    public BigDecimal getDecimal()
    {
        return new BigDecimal(numerator).divide(new BigDecimal(denominator));
    }

    public BigInteger getNumerator()
    {
        return numerator;
    }

    public BigInteger getDenominator()
    {
        return denominator;
    }

    public BigFraction add(BigFraction number)
    {
        BigInteger num = numerator.multiply(number.getDenominator()).add(denominator.multiply(number.getNumerator()));
        BigInteger den = denominator.multiply(number.getDenominator());
        return new BigFraction(num, denominator);
    }

    public BigFraction subtract(BigFraction number)
    {
        return add(number.negate());
    }

    public BigFraction divide(BigFraction number)
    {
        if(number.getNumerator() == BigInteger.ZERO)
        {
            throw new IllegalArgumentException("Division by zero is not allowed");
        }

        return multiply(new BigFraction(denominator, numerator));
    }

    public BigFraction multiply(BigFraction number)
    {
        return new BigFraction(numerator.multiply(number.getNumerator()),
                denominator.multiply(number.getDenominator()));
    }


    public BigFraction abs()
    {
        return new BigFraction(numerator.abs(), denominator.abs());
    }

    public BigFraction negate()
    {
        return new BigFraction(numerator.negate(), denominator);
    }


    @Override
    public int compareTo(BigFraction o)
    {
        BigInteger diff = numerator.multiply(o.denominator).subtract(o.getNumerator().multiply(denominator));
        return diff.signum();
    }

    @Override
    public String toString()
    {
        int sign = numerator.signum()*denominator.signum();
        return sign<0 ?"-1": "" + numerator + "/" + denominator;
    }
}
