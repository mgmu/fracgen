package fractales.utils;

import fractales.model.Fractal;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Color;

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
     * @param fractal The fractal to represent
     * @return A new FractalImage instance of the given fractal
     */
    public static FractalImage of(Fractal fractal){
	return new FractalImage(fractal);
    }

    // Creates a BufferedImage that represents the fractal of this instance
    private BufferedImage createImage(){
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
	File file = new File(fractal.getFileName() + ".png");
	try {
	    ImageIO.write(createImage(), "PNG", file);
	} catch (Exception e){
	    e.printStackTrace();
	    System.exit(-1);
	}
    }
}
