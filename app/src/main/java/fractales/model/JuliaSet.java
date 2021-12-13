package fractales.model;

import java.util.function.Function;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Color;

/**
 * This class encapsulates a Julia set
 */
public class JuliaSet implements Fractal {

    // escape radius and maximal number of iterations
    // of the function
    private static final double RADIUS = 2.0;
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
    private final String fileName = "JuliaSetTest";

    // iteration function and constant complex
    private final Function <Complex,Complex> iterationFunction; 
    private final Complex complexConstant;   
    
    // Constructor from Julia Set builder
    private JuliaSet(JuliaSetBuilder builder){
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
    }
    
    /**
     * Builder class for a JuliaSet
     */
    public static class JuliaSetBuilder {

	//required parameters
	private final Complex complexConstant;
	private final Function<Complex, Complex> iterationFunction;
	    
	// optionnal parameters for the Julia Set
	private int maxIteration = 1000;
	private double discreteStep = 0.00075;
	private double xMin = -1;
	private double xMax = 1;
	private double yMin = -1;
	private double yMax = 1;
	private int imageHeight = 0;
	private int imageWidth = 0;
	private String fileName = "JuliaSet";

	/**
	 * Instantiates a JuliaSetBuilder
	 * @param complexConstant The complex constant used with the
	 * iteration function
	 * @param iterationFunction The function used to compute the index
	 * of divergence
	 */
	public JuliaSetBuilder(Complex complexConstant,
			       Function<Complex, Complex> iterationFunction){
	    this.complexConstant = complexConstant;
	    this.iterationFunction = iterationFunction;
	}

	/**
	 * Sets the maximum iteration value for the iteration function
	 * @param maxIteration The maximum iteration value
	 * @return This JuliaSetBuilder instance
	 */
	public JuliaSetBuilder maxIteration(int maxIteration){
	    this.maxIteration = maxIteration;
	    return this;
	}

	/**
	 * Sets the value of the discrete step
	 * @param discreteStep The value of the discrete step
	 * @return This JuliaSetBuilder instance
	 */
	public JuliaSetBuilder discreteStep(double discreteStep){
	    this.discreteStep = discreteStep;
	    return this;
	}

	/**
	 * Sets the value for the minimum real value of a complex number
	 * @param xMin The value for the minimum real value of a complex number
	 * @return This JuliaSetBuilder instance
	 */
	public JuliaSetBuilder xMin(double xMin){
	    this.xMin = xMin;
	    return this;
	}

	/**
	 * Sets the value for the maximum real value of a complex number
	 * @param xMin The value for the maximum real value of a complex number
	 * @return This JuliaSetBuilder instance
	 */
	public JuliaSetBuilder xMax(double xMax){
	    this.xMax = xMax;
	    return this;
	}

	/**
	 * Sets the value for the minimum imaginary value of a complex number
	 * @param xMin The value for the minimum imaginary value of a complex number
	 * @return This JuliaSetBuilder instance
	 */
	public JuliaSetBuilder yMin(double yMin){
	    this.yMin = yMin;
	    return this;
	}

	/**
	 * Sets the value for the maximum imaginary value of a complex number
	 * @param xMin The value for the maximum imaginary value of a complex number
	 * @return This JuliaSetBuilder instance
	 */
	public JuliaSetBuilder yMax(double yMax){
	    this.yMax = yMax;
	    return this;
	}

	/**
	 * Sets the height of the image that contains the representation of
	 * the Julia Set
	 * @param imageHeight The height of the image
	 * @return This JuliaSetBuilder instance
	 */
	public JuliaSetBuilder imageHeight(int imageHeight){
	    this.imageHeight = imageHeight;
	    return this;
	}

	/**
	 * Sets the width of the image that contains the representation of the
	 * Juia Set
	 * @param imageWidth The width of the image
	 * @return This JuliaSetBuilder instance
	 */
	public JuliaSetBuilder imageWidth(int imageWidth){
	    this.imageWidth = imageWidth;
	    return this;
	}

	/**
	 * Sets the name of the file that contains the image of the Julia Set
	 * @param fileName The file name
	 * @return This JuliaSetBuilder instance
	 */
	public JuliaSetBuilder fileName(String fileName){
	    this.fileName = fileName;
	    return this;
	}

	/**
	 * Builds a Julia Set instance from this builder
	 * @return A new JuliaSet instance
	 */
	public JuliaSet build(){
	    if(imageHeight <= 0 || imageWidth <= 0){
		// assigns each point of the discrete plane to a pixel of
		// the image
		imageHeight =
		    (int) ((Math.abs(yMin)+Math.abs(yMax))/discreteStep + 1.0);
		imageWidth =
		    (int) ((Math.abs(xMin)+Math.abs(xMax))/discreteStep + 1.0);
	    }
	    return new JuliaSet(this);
	}
    }

    /**
     * Computes the index of divergence of the complex z0
     * @param z0 The initial complex of the iteration
     * @return The index of divergence of z0
     */
    @Override
    public int computeDivergence(Complex z0){
	int iteration = 0;
	Complex zn = z0;
	while(iteration < maxIteration - 1 && zn.modulus() <= RADIUS){
	    zn = iterationFunction.apply(zn);
	    iteration++;
	}
	return iteration;
    }

    /**
     * Computes the index of divergence of each complex in the 
     * rectangle delimeted by xMin xMax yMin yMax of the complex plane
     * and stores the resulting indices in a 2D array
     * @return A 2D array containing the index of divergence of the
     * corresponding complex number
     */
    @Override
    public int[][] getDivergenceIndexMatrix(){
    	// compute divergence for each complex points
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
     * @return The witdh in pixels of the image
     */
    @Override
    public int getWidth(){
	return imageWidth;
    }

    /**
     * Returns the height of the image that contains this Fractal
     * @return The height in pixels of the image
     */
    @Override
    public int getHeight(){
	return imageHeight;
    }

    /**
     * Returns the name of the file that contains the image of this Fractal
     * @return The name of the file that contains the image
     */
    @Override
    public String getFilename(){
	return fileName;
    }

    // creation de l'image a separer de cette classe
    // /**
    //  * Stores into a file of name filename a PNG image of the JuliaSet defined
    //  * by this instance
    //  */
    // public void drawImage(){
    // 	var img =
    // 	    new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
    // 	int[][] arrayDivergence = getDivergenceIndexMatrix();
    // 	for(int i = 0; i < imageWidth -1; i++){
    // 	    for(int j = 0; j < imageHeight -1; j++){
    // 		int div = arrayDivergence[i][j];
    // 		int rgb;
    // 		if(div == 999){
    // 		    rgb = 0;
    // 		} else {
    // 		    // rgb=Color
    // 		    //.HSBtoRGB((float)div/maxIter, 0.7f, (float)div/maxIter);
    // 		    rgb = Color
    // 			.HSBtoRGB((float)div*20.0f/(float)maxIteration,1.0f,1.0f);
    // 		}
    // 		img.setRGB(i,j,rgb);
    // 	    }
    // 	}
    // 	File file = new File(fileName + ".png");
    // 	try{
    // 	    ImageIO.write(img, "PNG", file);
    // 	} catch (Exception e){
    // 	    e.printStackTrace();
    // 	}
    // }
}
