package fractales.utils;

import fractales.model.Fractal;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * This class represents the image of a Fractal object
 * It creates a PNG image that contains the representation
 * of a Fractal object
 */
public class FractalImage {

    // The fractal to represent
    private Fractal fractal;

    // path to the file
    private String path;

    // instantiates a FractalImage from a Fractal object
    private FractalImage(Fractal fractal){
	this.fractal = fractal;
    }

    /**
     * Returns a new FractalImage instance that contains the image
     * of the specified Fractal fractal
     * @param fractal The fractal to represent
     * @return A new FractalImage instance of the given fractal
     */
    public static FractalImage of(Fractal fractal){
	return new FractalImage(fractal);
    }

    /**
     * Returns a BufferedImage containing the representation of the fractal
     *
     * @return A BufferedImage containing the representation of the fractal
     */
    public BufferedImage createImage(){
	int w = fractal.getWidth();
	int h = fractal.getHeight();
	var img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

	int[][] divMatrix = fractal.getDivergenceIndexMatrix();
	int rgb = 0;

	for(int i = 0; i < w - 1; i++){
	    for(int j = 0; j < h - 1; j++){
		rgb = fractal.getColorFromDivergenceIndex(divMatrix[i][j]);
		img.setRGB(i, j, rgb);
	    }
	}
	return img;
    }

    /**
     * Creates an image of the fractal and saves it in a .png file
     * which name is the return value of the function getFileName() on the
     * fractal, with .png concatenated
     */

    public void saveFile(){
	FractalText ft = FractalText.of(fractal);
	ft.saveFile();
	File file =
	    new File("/tmp/" + fractal.getFileName() + ".png");
	path = file.getAbsolutePath();
	try {
	    ImageIO.write(createImage(), "PNG", file);
	} catch (Exception e){
	    e.printStackTrace();
	    System.out.println("ERROR SAVING PNG");
	    System.exit(-1);
	}
    }

    /**
     * Returns the path to the image
     *
     * @return The path to the image
     */
    public String getPath(){
	return path;
    }
}
