package fractales.utils;

import java.util.concurrent.RecursiveAction;
import fractales.model.*;

/**
 * This class is used to compute the divergence index matrix.
 * It uses a pool of threads in order to do so
 */
public class DivergenceIndexMatrixCalculator extends RecursiveAction {
    int from, to; // boundaries
    int[][] results; // resulting array of divergence indices
    Fractal fractal; // the fractal being built

    /**
     * Instantiates a Calculator with the specified arguments
     *
     * @param from The starting index 
     * @param to The ending index
     * @param results The array to fill with divergence indices
     * @param fractal The fractal being built
     */
    public DivergenceIndexMatrixCalculator(int from, int to, int[][] results,
					   Fractal fractal){
	this.from = from;
	this.to = to;
	this.results = results;
	this.fractal = fractal;
    }

    @Override
    protected void compute(){
	if(to - from < fractal.getWidth() / 8){
	    computeDirectly();
	    return;
	}
	int middle = (from + to) / 2;
	invokeAll(new DivergenceIndexMatrixCalculator(from, middle, results,
						      fractal),
		  new DivergenceIndexMatrixCalculator(middle, to, results,
						      fractal));
    }

    // computes divergence indices from index from to index to
    private void computeDirectly(){
	for(int i = from; i < to; i++){
	    for(int j = 0; j < fractal.getHeight() - 1; j++){
		Complex complex =
		    Complex.of(fractal.getXMin()
			       + (fractal.getDiscreteStep() * i),
			       fractal.getYMax()
			       - (fractal.getDiscreteStep() * j));
		results[i][j] = fractal.computeDivergence(complex);
	    }
	}
    }
}
