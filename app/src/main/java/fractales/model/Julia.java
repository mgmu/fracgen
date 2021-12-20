package fractales.model;

import java.util.function.Function;
import java.awt.Color;
import java.util.concurrent.*;
import fractales.utils.DivergenceIndexMatrixCalculator;

/**
 * This class encapsulates a Julia set
 */
public class Julia implements Fractal {

    // maximal number of iterations of the function
    private final int maxIteration;

    // working rectangle of the Complex plane
    private final double xMin;
    private final double xMax;
    private final double yMin;
    private final double yMax;

    // discrete step
    private final double discreteStep;

    // image height, width and name
    private final int imageHeight;
    private final int imageWidth;
    private final String fileName;

    // iteration function and constant complex
    private final Function <Complex,Complex> iterationFunction;
    private final Complex complexConstant;

    // factors for the color function
    private final float alphaColor;
    private final float betaColor;
    private final float gammaColor;


    // Constructs from Julia builder
    private Julia(Builder builder){
	this.complexConstant = builder.complexConstant;
	this.maxIteration = builder.maxIteration;
	this.discreteStep = builder.discreteStep;
	this.xMin = builder.xMin;
	this.xMax = builder.xMax;
	this.yMin = builder.yMin;
	this.yMax = builder.yMax;
	this.iterationFunction = builder.iterationFunction;
	this.imageHeight = builder.imageHeight;
	this.imageWidth = builder.imageWidth;
	this.fileName = builder.fileName;
	this.alphaColor = builder.alphaColor;
	this.betaColor = builder.betaColor;
	this.gammaColor = builder.gammaColor;
    }

    /**
     * Builder for Julia class
     */
    public static class Builder {

	private Complex complexConstant = Complex.getZERO();
	private int maxIteration = 1000;
	private double discreteStep = 0.00075;
	private double xMin = -1;
	private double xMax = 1;
	private double yMin = -1;
	private double yMax = 1;
	private int imageHeight = 0;
	private int imageWidth = 0;
	private String fileName = "Julia";
	private Function<Complex, Complex> iterationFunction =
	    (z) -> {
	    return Complex.getONE().multiply(z.multiply(z))
	    .add(z.multiply(Complex.getZERO()))
	    .add(complexConstant);
	};
	private float alphaColor = 20.0f;
	private float betaColor = 1.0f;
	private float gammaColor = 1.0f;

	/**
	 * Sets the value of the complex constant
	 *
	 * @param complexConstant The complex constant
	 * @return This Builder instance
	 */
	public Builder complexConstant(Complex complexConstant){
	    this.complexConstant = complexConstant;
	    return this;
	}

	/**
	 * Sets the iteration function expression.
	 * The iteration function is of the form : 
	 * f(z) = alpha * z^2 + beta * z + complexConstant
	 * where alpha and beta are Complex numbers
	 *
	 * @param alpha The complex factor of squared z
	 * @param beta The complex factor of z
	 * @return This Builder instance
	 */
	public Builder iterationFunction(Complex alpha, Complex beta){
	    this.iterationFunction = (z) -> {
		return alpha.multiply(z.multiply(z))
		.add(beta.multiply(z))
		.add(complexConstant);
	    };
	    return this;
	}
	
	/**
	 * Sets the maximum iteration value for the iteration function
	 *
	 * @param maxIteration The maximum iteration value
	 * @return This Builder instance
	 */
	public Builder maxIteration(int maxIteration){
	    this.maxIteration = maxIteration;
	    return this;
	}

	/**
	 * Sets the value of the discrete step
	 *
	 * @param discreteStep The value of the discrete step
	 * @return This Builder instance
	 */
	public Builder discreteStep(double discreteStep){
	    this.discreteStep = discreteStep;
	    return this;
	}

	/**
	 * Sets the value for the minimum real value of a complex number
	 *
	 * @param xMin The value for the minimum real value of a complex number
	 * @return This Builder instance
	 */
	public Builder xMin(double xMin){
	    this.xMin = xMin;
	    return this;
	}

	/**
	 * Sets the value for the maximum real value of a complex number
	 *
	 * @param xMin The value for the maximum real value of a complex number
	 * @return This Builder instance
	 */
	public Builder xMax(double xMax){
	    this.xMax = xMax;
	    return this;
	}

	/**
	 * Sets the value for the minimum imaginary value of a complex number
	 *
	 * @param xMin The value for the minimum imaginary value of a complex number
	 * @return This Builder instance
	 */
	public Builder yMin(double yMin){
	    this.yMin = yMin;
	    return this;
	}

	/**
	 * Sets the value for the maximum imaginary value of a complex number
	 *
	 * @param xMin The value for the maximum imaginary value of a complex number
	 * @return This Builder instance
	 */
	public Builder yMax(double yMax){
	    this.yMax = yMax;
	    return this;
	}

	/**
	 * Sets the height of the image that contains the representation of
	 * the Julia Set
	 *
	 * @param imageHeight The height of the image
	 * @return This Builder instance
	 */
	public Builder imageHeight(int imageHeight){
	    this.imageHeight = imageHeight;
	    return this;
	}

	/**
	 * Sets the width of the image that contains the representation of
	 * the Julia Set
	 *
	 * @param imageWidth The width of the image
	 * @return This Builder instance
	 */
	public Builder imageWidth(int imageWidth){
	    this.imageWidth = imageWidth;
	    return this;
	}

	/**
	 * Sets the name of the file that contains the image of the Julia set
	 *
	 * @param fileName The file name
	 * @return This Builder instance
	 */
	public Builder fileName(String fileName){
	    this.fileName = fileName;
	    return this;
	}

	/**
	 * Sets the factors for the color function.
	 * @param alpha First factor
	 * @param beta Second factor
	 * @param gamma Third factor
	 * @return This Builder instance
	 */
	public Builder colorFunction(float alpha, float beta, float gamma){
	    this.alphaColor = alpha;
	    this.betaColor = beta;
	    this.gammaColor = gamma;
	    return this;
	}

	/**
	 * Builds a Julia instance from this builder
	 *
	 * @return A new Julia instance
	 */
	public Julia build(){
	    if(imageHeight <= 0 || imageWidth <= 0){
		// assigns each point of the discrete plane to a pixel of
		// the image
		imageHeight =
		    (int) ((Math.abs(yMin)+Math.abs(yMax))/discreteStep + 1.0);
		imageWidth =
		    (int) ((Math.abs(xMin)+Math.abs(xMax))/discreteStep + 1.0);
	    }
	    return new Julia(this);
	}
    }

    /**
     * Computes the divergence index of Complex z
     *
     * @param z A Complex number
     * @return The divergence index of z
     */
    @Override
    public int computeDivergence(Complex z){
	int iteration = 0;
	Complex zn = z;
	while(iteration < maxIteration - 1 && zn.modulus() <= Fractal.RADIUS){
	    zn = iterationFunction.apply(zn);
	    iteration++;
	}
	return iteration;
    }

    /**
     * Computes the divergence index of each complex in the
     * rectangle delimeted by xMin xMax yMin yMax of the complex plane
     * and stores the resulting indices in a 2D array
     *
     * @return A 2D array containing the divergence index of the
     * corresponding complex number
     */
    @Override
    public int[][] getDivergenceIndexMatrix(){
    	int[][] arrayDivergence = new int[imageWidth][imageHeight];
	DivergenceIndexMatrixCalculator work =
	    new DivergenceIndexMatrixCalculator(0, this.getWidth() - 1,
						arrayDivergence, this);
	ForkJoinPool pool = new ForkJoinPool();
	pool.invoke(work);
	return arrayDivergence;
    }

    /**
     * Returns the width of the image that contains this Fractal
     *
     * @return The width in pixels of the image
     */
    @Override
    public int getWidth(){
	return imageWidth;
    }

    /**
     * Returns the height of the image that contains this Fractal
     *
     * @return The height in pixels of the image
     */
    @Override
    public int getHeight(){
	return imageHeight;
    }

   /**
     * Returns the name of the file that contains the image of this Fractal
     *
     * @return The name of the file that contains the image
     */
    @Override
    public String getFileName(){
	return fileName;
    }

    /**
     * Returns an int in RGB format that represents the color associated
     * to the specified int divergenceIndex
     *
     * @param divergenceIndex A divergence index
     * @return The color associated to the given int divergence index
     */
    @Override
    public int getColorFromDivergenceIndex(int divergenceIndex){
	// rgb=Color.HSBtoRGB((float)div/maxIter, 0.7f, (float)div/maxIter);
	if(divergenceIndex == maxIteration - 1)
	    return 0;
	return Color
	    .HSBtoRGB((float)divergenceIndex * alphaColor / (float)maxIteration,
		      betaColor,
		      gammaColor);
	// return Color
	//     .HSBtoRGB((float)divergenceIndex * alphaColor / (float)maxIteration,
	// 	      (float)divergenceIndex * betaColor / (float)maxIteration,
	// 	      (float)divergenceIndex * gammaColor / (float)maxIteration);
    }

    @Override
    public double getDiscreteStep(){
	return this.discreteStep;
    }

    @Override
    public double getXMin(){
	return this.xMin;
    }

    @Override
    public double getXMax(){
	return this.xMax;
    }

    @Override
    public double getYMin(){
	return this.yMin;
    }

    @Override
    public double getYMax(){
	return this.yMax;
    }
}
