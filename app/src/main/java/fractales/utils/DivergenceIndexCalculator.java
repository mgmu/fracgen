package fractales.utils;

import fractales.model.*;

public class DivergenceIndexCalculator implements Runnable {

    // the array to fill
    private int[][] result;

    // the complex used in the computation of the divergence index
    private Complex complex;

    // the fractal to generate
    private Fractal fractal;

    // the coordinates of the array to fill 
    private int i;
    private int j;

    // constructor
    private DivergenceIndexCalculator(int[][] result, Complex complex,
				      Fractal fractal, int i, int j){
	this.result = result;
	this.complex = complex;
	this.fractal = fractal;
	this.i = i;
	this.j = j;
    }

    /**
     * Returns a new instance of this class with the specified arguments
     *
     * @param result The array that stores the divergence indices
     * @param complex The complex to compute its divergence index
     * @param fractal The fractal to generate
     * @param i The width index of the array
     * @param j The height index of the array
     */
    public static DivergenceIndexCalculator of(int[][] result, Complex complex,
					       Fractal fractal, int i, int j){
	return new DivergenceIndexCalculator(result, complex, fractal, i, j);
    }

    @Override
    public void run(){
	result[i][j] = fractal.computeDivergence(complex);
    }
    
}
