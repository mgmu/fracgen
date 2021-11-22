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

	// Complex c = Complex.of( -0.7269, +0.1889);
	// // Complex c = Complex.of( 0 ,-0.8);
  // //  + 0.1889i
	// Function<Complex, Complex> f = (z) -> (z.multiply(z)).add(c);
	// // [-1, 1] x [-1, 1] is the chosen complex plane
	// double x1, x2, y1, y2;
	// x1 = -1;
	// x2 = 1;
	// y1 = -1;
	// y2 = 1;
  //
	// // 2d array of complex points
	// double dFactor = 0.00075;
	// int width = (int) ((Math.abs(x1) + Math.abs(x2)) / dFactor + 1.0);
	// int height = (int)((Math.abs(y1) + Math.abs(y2)) / dFactor + 1.0);
	// // int width = 3500;
	// // int height = 3500;
  //
	// Complex[][] plane = new Complex[width][height];
	// for(int i = 0; i < width-1; i++){
	//     for(int j = 0; j < height - 1; j++){
	// 	plane[i][j] =
	// 	    Complex.of(x1 + (dFactor * i), y2 - (dFactor * j));
	//     }
	// }
  //
	// // compute divergence for each complex points
	// int[][] arrayDivergence = new int[width][height];
	// for(int i = 0; i < width -1; i++){
	//     for(int j = 0; j < height -1; j++)
  //     {
	// 	      arrayDivergence[i][j] = computeDivergence(plane[i][j], f);
	//     }
	// }
  //
	// var img = new BufferedImage(width, height, BufferedImage. TYPE_INT_RGB);
  // for(int i = 0; i < width -1; i++){
  //   for(int j = 0; j < height -1; j++){
  //     int div = arrayDivergence[i][j];
  //     int r = div * 255 / MAX_ITER;
  //     int g = div * 255 / MAX_ITER;
  //     int b = 0;
  //     // img.setRGB(i,j,(r << 16) | (g << 8) | b);
  //     int rgb;
  //     if(div == 999)
  //     {
  //       rgb = 0;
  //     }
  //     else
  //     {
  //       // rgb=Color.HSBtoRGB((float)div/MAX_ITER, 0.7f, (float)div/MAX_ITER);
  //       rgb = Color.HSBtoRGB((float)div*20.0f/(float)MAX_ITER,1.0f,1.0f);
  //     }
  //     img.setRGB(i,j,rgb);
  //   }
  // }
  //
	// File file = new File("JuliaTest.png");
  //
	// try{
	//     ImageIO.write(img, "PNG", file);
	// } catch (Exception e){
	//     e.printStackTrace();
	// }

    Complex c = Complex.of(-0.7269, +0.1889);
    // Complex c = Complex.of(0, -0.8);
    Function<Complex, Complex> f = (z) -> (z.multiply(z)).add(c);
    JuliaSet js = new JuliaSet.JuliaSetBuilder(c,f).build();
    js.drawImage();
    }
}
