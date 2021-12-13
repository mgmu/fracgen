package fractales.model;

/**
 * This interface defines a Fractal object.
 */
public interface Fractal {

    /**
     * Computes the index of divergence of each complex in the 
     * rectangle delimeted by xMin xMax yMin yMax of the complex plane
     * and stores the resulting indices in a 2D array
     * @return A 2D array containing the index of divergence of the
     * corresponding complex number
     */
    int[][] getDivergenceIndexMatrix();
    
    /**
     * Computes the index of divergence of the complex z0
     * @param z0 The initial complex of the iteration
     * @return The index of divergence of z0
     */
    int computeDivergence(Complex z0);

    /**
     * Returns the width of the image that contains this Fractal
     * @return The witdh in pixels of the image
     */
    int getWidth();

    /**
     * Returns the height of the image that contains this Fractal
     * @return The height in pixels of the image
     */
    int getHeight();

    /**
     * Returns the name of the file that contains the image of this Fractal
     * @return The name of the file that contains the image
     */
    String getFilename();
}
