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
	
	Complex c = Complex.of(0.355534, -0.337292);
	Function<Complex, Complex> iterFun = (zn) -> (zn.multiply(zn)).add(c);
	FractalImage fImg = FractalImage
	    .of(new JuliaSet.JuliaSetBuilder(c, iterFun).build());
	fImg.saveFile();

    }
}
