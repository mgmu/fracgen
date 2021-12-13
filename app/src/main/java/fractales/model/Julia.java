package fractales.model;

import java.util.function.Function;
import java.awt.Color;

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
    }
    
    /**
     * Builder for Julia class
     */
    public static class Builder {

	// required parameters
	private final Complex complexConstant;
	private final Function<Complex, Complex> iterationFunction;
	    
	// optionnal parameters for Julia
	private int maxIteration = 1000;
	private double discreteStep = 0.00075;
	private double xMin = -1;
	private double xMax = 1;
	private double yMin = -1;
	private double yMax = 1;
	private int imageHeight = 0;
	private int imageWidth = 0;
	private String fileName = "Julia";

	/**
	 * Instantiates a Builder
	 *
	 * @param complexConstant The complex constant used with the
	 * iteration function
	 * @param iterationFunction The function used to compute the index
	 * of divergence
	 */
	public Builder(Complex complexConstant,
			       Function<Complex, Complex> iterationFunction){
	    this.complexConstant = complexConstant;
	    this.iterationFunction = iterationFunction;
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
    	for(int i = 0; i < imageWidth -1; i++){
    	    for(int j = 0; j < imageHeight -1; j++){
		Complex complex =
		    Complex.of(xMin + (discreteStep * i),
			       yMax - (discreteStep * j));
		arrayDivergence[i][j] = computeDivergence(complex);
	    }
    	}
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
    public int getColorFromDivergenceIndex(int divergenceIndex){
	// rgb=Color.HSBtoRGB((float)div/maxIter, 0.7f, (float)div/maxIter);
	if(divergenceIndex == maxIteration - 1)
	    return 0;
	return Color
	    .HSBtoRGB((float)divergenceIndex * 20.0f / (float)maxIteration,
		      1.0f,
		      1.0f);
    }
}
