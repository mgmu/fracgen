package fractales;

/**
 * This class encapsulates a complex number.
 * It admits algebric representation, addition, substraction and multiplication 
 * operations.
 * This class is immutable.
 */
public final class Complex{

    /**
     * The real part of the complex number.
     */
    private final double re;

    /**
     * The imaginary part of the complex number.
     */
    private final double im;

    /**
     * The complex number i.
     */
    private final static Complex I = new Complex(0.0, 1.0);

    /**
     * The complex number 1.
     */
    private final static Complex ONE = new Complex(1.0, 0.0);

    /**
     * The complex number 0.
     */
    private final static Complex ZERO = new Complex(0.0, 0.0);

    /**
     * Instantiates a new Complex number.
     * @param re The real part.
     * @param im The imaginary part.
     */
    private Complex(double realPart, double imaginaryPart){
	this.re = realPart;
	this.im = imaginaryPart;
    }

    /**
     * Returns a new complex number z such as Re(z) = realPart and
     * Im(z) = imaginaryPart.
     * @param realPart The real part of the new complex number
     * @param imaginaryPart The imaginary part of the new complex number
     * @return A new complex number z equal to realPart + i * imaginaryPart
     */
    public static Complex of(double realPart, double imaginaryPart){
	return new Complex(realPart, imaginaryPart);
    }

    @Override
    public String toString(){
	String toString = "z = " + re;
	if(im > 0)
	    return toString + " + i * (" + im + ")";
	else if(im < 0)
	    return toString + " - i * (" + (im * -1) + ")";
	else
	    return toString;
    }

    /**
     * Returns the sum of this complex number and the given complex number addend.
     * @param addend The complex number to add to this complex number.
     * @return A new complex number equal to (this + addend)
     */
    public Complex add(Complex addend){
	return new Complex(re + addend.getRe(), im + addend.getIm());
    }
    
    /**
     * Returns the substraction of this complex number and the given
     * complex number subtrahend.
     * @param subtrahend The second complex number.
     * @return A new complex number equal to (this - subtrahend)
     */
    public Complex subtract(Complex subtrahend){
	return new Complex(re - subtrahend.getRe(), im - subtrahend.getIm());
    }

    /**
     * Returns the multiplication of this complex number and the given
     * complex number factor.
     * @param factor The second complex number.
     * @return A new complex number equal to (this * factor)
     */
    public Complex multiply(Complex factor){
	return new Complex(re * factor.getRe() - im * factor.getIm(),
			   re * factor.getIm() + im * factor.getRe());
    }
    
    /**
     * Gets the real part of this complex number.
     * @return the real part of this complex number.
     */
    public double getRe(){
	return re;
    }

    /**
     * Gets the imaginary part of this complex number.
     * @return the imaginary part of this complex number.
     */
    public double getIm(){
	return im;
    }

    /**
     * Returns true if the argument z equals this complex number
     * @param z The complex number to compare with this 
     * complex number.
     * @return true if (this.realPart = w.realPart) and
     * (this.imaginaryPart = z.imaginaryPart), false otherwise.
     */
    public boolean equals(Complex z){
	return (re == z.getRe() && im == z.getIm());
    }

    /**
     * Returns the conjugate of this complex number.
     * @return The conjugate of this complex number.
     */
    public Complex conjugate(){
	return new Complex(re, im * -1.0);
    }

    /**
     * Returns the modulus of this complex number.
     * @return The modulus of this complex number.
     */
    public double modulus(){
	return Math.sqrt(Math.pow(re, 2.0) + Math.pow(im, 2.0));
    }

    /**
     * Returns the argument of this complex number.
     * @return The argument of this complex number.
     */
    public double argument(){
	return Math.atan2(im, re);
    }

    /**
     * Gets the constant 0.
     * @return The complex number z = 0.
     */
    public static Complex getZERO(){
	return ZERO;
    }

    /**
     * Gets the constant 1.
     * @return The complex number z = 1.
     */
    public static Complex getONE(){
	return ONE;
    }

    /**
     * Gets the constant i.
     * @return The complex number z = i.
     */
    public static Complex getI(){
	return I;
    }
}
