package fractales;

import fractales.model.*;
import fractales.utils.FractalImage;

import java.util.function.Function;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Color;

public class App {

    public static void main(String[] args) {

	// Julia image
	
	// Complex c = Complex.of(0.355534, -0.337292);
	// Function<Complex, Complex> iterFun = (zn) -> (zn.multiply(zn)).add(c);
	// FractalImage julia = FractalImage
	//     .of(new Julia.Builder(c, iterFun).build());
	// julia.saveFile();

	// Mandelbrot image
	FractalImage mandelbrot = FractalImage
	    .of(new Mandelbrot.Builder().build());
	mandelbrot.saveFile();
    }
}
