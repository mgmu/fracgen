package fractales.model;

/**
 * This interface defines a Fractal object.
 */
public interface Fractal {

    public enum FractalType {
	JULIA, MANDELBROT;
    }

    /**
     * Radius value that bounds the iteration function
     */
    double RADIUS = 2.0;

    /**
     * Computes the divergence index of each complex in the
     * rectangle delimeted by xMin xMax yMin yMax of the complex plane
     * and stores the resulting indices in a 2D array
     *
     * @return A 2D array containing the divergence index of the
     * corresponding complex number
     */
    int[][] getDivergenceIndexMatrix();

    /**
     * Computes the divergence index of Complex z
     *
     * @param z A Complex number
     * @return The divergence index of z
     */
    int computeDivergence(Complex z);

    /**
     * Returns the width of the image that contains this Fractal
     *
     * @return The witdh in pixels of the image
     */
    int getWidth();

    /**
     * Returns the height of the image that contains this Fractal
     *
     * @return The height in pixels of the image
     */
    int getHeight();

    /**
     * Returns the name of the file that contains the image of this Fractal
     *
     * @return The name of the file that contains the image
     */
    String getFileName();

    /**
     * Returns the value of the discrete step used for the generation
     * of the complex plane
     *
     * @return The discrete step value
     */
    double getDiscreteStep();

    /**
     * Returns the minimal value along the x-axis
     *
     * @return The minimal value along the x-axis
     */
    double getXMin();

    /**
     * Returns the maximal value along the x-axis
     *
     * @return The maximal value along the x-axis
     */
    double getXMax();

    /**
     * Returns the minimal value along the y-axis
     *
     * @return The minimal value along the y-axis
     */
    double getYMin();

    /**
     * Returns the maximal value along the y-axis
     *
     * @return The maximal value along the y-axis
     */
    double getYMax();

    /**
     * Returns the type of the fractal
     *
     * @return The type of the fractal
     */
    FractalType getFractalType();

    /**
     * Returns the maximum number of iteration for the iteration function
     *
     * @return The maximum number of iteration for the iteration function
     */
    int getMaxIteration();

    /**
     * Returns the alpha color factor
     *
     * @return The alpha color factor
     */
    float getAlphaColor();

    /**
     * Returns the beta color factor
     *
     * @return The beta color factor
     */
    float getBetaColor();

    /**
     * Returns the gamma color factor
     *
     * @return The gamma color factor
     */
    float getGammaColor();

    /**
     * Returns an int of RGB format that represents the color associated
     * to the specified int divergenceIndex
     *
     * @param divergenceIndex A divergence index
     * @return The color associated to the given int divergence index
     */
    int getColorFromDivergenceIndex(int divergenceIndex);
}
