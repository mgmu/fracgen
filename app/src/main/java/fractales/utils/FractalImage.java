package fractales.utils;

import fractales.model.Fractal;

/**
 * This class represents the inage of a Fractal object
 */
public class FractalImage {

    // The fractal in the image
    private Fractal fractal;

    // instantiates a FractalImage from a Fractal object
    private FractalImage(Fractal fractal){
	this.fractal = fractal;
    }

    /**
     * Returns a new FractalImage instance that contains the image
     * of the specified Fractal fractal
     * @param fractal The fractal ot represent
     * @return A new FractalImage instance of the given fractal
     */
    public static FractalImage of(Fractal fractal){
	return new FractalImage(fractal);
    }
}
