package fractales;

import java.util.function.Function;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class App {

    static final int MAX_ITER = 1000;
    static final double RADIUS = 1.0;

    // computes the divergence of a complex point
    public static int computeDivergence(Complex z0, Function<Complex, Complex> f){
	int iteration = 0;
	Complex zn = z0;
	while(iteration < MAX_ITER - 1 && zn.modulus() <= RADIUS){
	    zn = f.apply(zn);
	    iteration++;
	}
	return iteration;
    }
    
    public static void main(String[] args) {

	Complex c = Complex.of(-0.7269, +0.1889);
	Function<Complex, Complex> f = (z) -> (z.multiply(z)).add(c);
	// [-1, 1] x [-1, 1] is the chosen complex plane
	double x1, x2, y1, y2;
	x1 = -1;
	x2 = 1;
	y1 = -1;
	y2 = 1;

	// 2d array of complex points
	double dFactor = 0.01;
	int width = (int) ((Math.abs(x1) + Math.abs(x2)) / dFactor + 1.0);
	int height = (int)((Math.abs(y1) + Math.abs(y2)) / dFactor + 1.0);

	Complex[][] plane = new Complex[height][width];
	for(int i = 0; i < height; i++){
	    for(int j = 0; j < width; j++){
		plane[i][j] =
		    Complex.of(x1 + (dFactor * j), y2 - (dFactor * i));
	    }
	}

	// compute divergence for each complex points
	int[][] arrayDivergence = new int[height][width];
	for(int i = 0; i < height; i++){
	    for(int j = 0; j < width; j++){
		arrayDivergence[i][j] = computeDivergence(plane[i][j], f);
	    }
	}

	var img = new BufferedImage(width, height, BufferedImage. TYPE_INT_RGB);
	for(int i = 0; i < height; i++){
	    for(int j = 0; j < width; j++){
		int div = arrayDivergence[i][j];
		int r = div * 255 / MAX_ITER;
		int g = div * 255 / MAX_ITER;
		int b = 0;
		img.setRGB(i,j,(r << 16) | (g << 8) | b);
	    }
	}

	File file = new File("JuliaTest.png");
	
	try{
	    ImageIO.write(img, "PNG", file);
	} catch (Exception e){
	    e.printStackTrace();
	}
    }
}
