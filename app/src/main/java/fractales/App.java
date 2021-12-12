package fractales;

import java.util.function.Function;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Color;

public class App {

    public static void main(String[] args) {
	
	Complex c = Complex.of(0.355534, -0.337292);
	Function<Complex, Complex> iterFun = (zn) -> (zn.multiply(zn)).add(c);
	JuliaSet js = new JuliaSet.JuliaSetBuilder(c, iterFun).build();
	js.drawImage();
    }
}
