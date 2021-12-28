package fractales.utils;

import java.io.File;
import java.io.PrintWriter;
import fractales.model.Fractal;
import fractales.model.Fractal.FractalType;
import fractales.model.Julia;
import fractales.model.Complex;
import fractales.model.Mandelbrot;
import java.util.Scanner;

/**
 * This class creates a descriptive text file of a fractal
 */
public class FractalText {

    // the generated fractal
    private Fractal fractal;

    // instantiates this class
    private FractalText(Fractal fractal){
	this.fractal = fractal;
    }

    /**
     * Creates a new instance of this class with the specified Fractal fractal
     *
     * @param fractal The fractal to save
     * @return A new instance of this class
     */
    public static FractalText of(Fractal fractal){
	return new FractalText(fractal);
    }

    /**
     * Saves at the same location of the fractal image a descriptive text file
     * that allows the user to load the generated fractal
     */
    public void saveFile(){
	try{
	    String path = "/tmp/" + fractal.getFileName() + ".txt";
	    File file = new File(path);

	    PrintWriter pw = new PrintWriter(file);
	    pw.println(fractal.getFractalType());
	    if(fractal.getFractalType() == FractalType.JULIA){
		pw.println(((Julia)fractal).getComplexConstant());
		pw.println(((Julia)fractal).getAlphaFactor());
		pw.println(((Julia)fractal).getBetaFactor());
	    }
	    pw.println(fractal.getMaxIteration());
	    pw.println(fractal.getDiscreteStep());
	    pw.println(fractal.getXMin());
	    pw.println(fractal.getXMax());
	    pw.println(fractal.getYMin());
	    pw.println(fractal.getYMax());
	    pw.println(fractal.getWidth());
	    pw.println(fractal.getHeight());
	    pw.println(fractal.getFileName());
	    pw.println(fractal.getAlphaColor());
	    pw.println(fractal.getBetaColor());
	    pw.println(fractal.getGammaColor());
	    pw.close();
	} catch(Exception e){
	    e.printStackTrace();
	    System.out.println("ERROR SAVING TXT");
	    System.exit(-1);
	}
    }

    /**
     * Saves a fractal image from the file path
     */
    public static Fractal textToImage(String path){
	try {
	    Fractal fractal = null;
	    File file = new File(path);
	    Scanner sc = new Scanner(file);

	    // fetching data
	    Complex cc = null;
	    Complex af = null;
	    Complex bf = null;
	    FractalType ft = FractalType.valueOf(sc.nextLine());
	    if(ft == FractalType.JULIA){
		cc = Complex.fromString(sc.nextLine());
		af = Complex.fromString(sc.nextLine());
		bf = Complex.fromString(sc.nextLine());
	    }
	    int mi = sc.nextInt();
	    double ds = sc.nextDouble();
	    double xmin = sc.nextDouble();
	    double xmax = sc.nextDouble();
	    double ymin = sc.nextDouble();
	    double ymax = sc.nextDouble();
	    int w = sc.nextInt();
	    int h = sc.nextInt();
	    String fn = sc.nextLine();
	    sc.nextLine();
	    float ac = sc.nextFloat();
	    float bc = sc.nextFloat();
	    float gc = sc.nextFloat();
	    sc.close();

	    // creating fractal
	    if(ft == FractalType.JULIA){
		fractal =
		    new Julia.Builder()
		    .complexConstant(cc)
		    .iterationFunction(af, bf)
		    .maxIteration(mi)
		    .discreteStep(ds)
		    .xMin(xmin)
		    .xMax(xmax)
		    .yMin(ymin)
		    .yMax(ymax)
		    .imageWidth(w)
		    .imageHeight(h)
		    .fileName(fn)
		    .colorFunction(ac, bc, gc)
		    .build();
	    }
	    
	    if(ft == FractalType.MANDELBROT) {
		fractal =
		    new Mandelbrot.Builder()
		    .maxIteration(mi)
		    .discreteStep(ds)
		    .xMin(xmin)
		    .xMax(xmax)
		    .yMin(ymin)
		    .yMax(ymax)
		    .imageWidth(w)
		    .imageHeight(h)
		    .fileName(fn)
		    .colorFunction(ac, bc, gc)
		    .build();
	    }
	    return fractal;
	} catch(Exception e){
	    e.printStackTrace();
	    System.out.println("Error loading file...");
	    return null;
	}
    }
}
