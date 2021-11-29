package fractales;

import java.util.function.Function;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Color;

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

    //examples of complexConstant for nice JuliaSet images
    // Complex c = Complex.of(-0.7269, +0.1889);
    // Complex c = Complex.of(0, -0.8);
    Complex c = Complex.of(0.355534, -0.337292);
    // Complex c = Complex.of(-0.4, -0.59);
    // Complex c = Complex.of(-0.54, +0.54);
    // Complex c = Complex.of(0.34, +0.05);
    // Complex c = Complex.of(0.355, +0.355);
    // Complex c = Complex.of(0.37, +0.1);
    // Complex c = Complex.of(-1.34882125854492, -0.454237874348958);
    // Complex c = Complex.of(-0.202420806884766, -0.39527333577474);
    Function<Complex, Complex> f = (z) -> (z.multiply(z)).add(c);
    JuliaSet js = new JuliaSet.JuliaSetBuilder(c,f).build();
    js.drawImage();
    }
}
