package fractales;

import java.util.function.Function;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Color;

/**
 * This class encapsulates a Julia set
 */
public class JuliaSet {


  private final double RADIUS = 2.0;

  /**
   * The constant complex from the julia set formula z²+c
   */
  private final Complex complexConstant;

  /**
   * Maximum iterations
   */
  private int maxIter = 1000;

  /**
   * The rectangle of the complex plane
   */
  private final double x1;
  private final double x2;
  private final double y1;
  private final double y2;

  /**
  * The discretisation step of the rectange
  */
  private final double step;

  private final int height;

  private final int width; //largeur

  private final Function <Complex,Complex> function;

  /**
   * The name of the image of the julia set
   */
  private String fileName = "JuliaSetTest";


  private JuliaSet(JuliaSetBuilder builder)
  {
    this.complexConstant = builder.complexConstant;
    this.step = builder.step;
    this.x1 = builder.x1;
    this.x2 = builder.x2;
    this.y1 = builder.y1;
    this.y2 = builder.y2;
    this.function = builder.function;
    if(builder.height == 0 || builder.width == 0)
    {
      this.height = (int)((Math.abs(y1) + Math.abs(y2)) / step + 1.0);
      this.width = (int) ((Math.abs(x1) + Math.abs(x2)) / step + 1.0);
    }
    else
    {
      this.height = builder.height;
      this.width = builder.width;
    }
  }

  /**
   * Builder class for a JuliaSet
   */
  public static class JuliaSetBuilder {

    private final Complex complexConstant;

    private double step = 0.00075;

    private double x1 = -1;
    private double x2 = 1;
    private double y1 = -1;
    private double y2 = 1;

    private final Function <Complex,Complex> function;

    private int height = 0;
    private int width = 0;


    public JuliaSetBuilder(Complex complex, Function function)
    {
      this.complexConstant = complex;
      this.function = function;
    }

    public JuliaSetBuilder step(double step)
    {
      this.step = step;
      return this;
    }

    public JuliaSetBuilder x1(int x1)
    {
        this.x1 = x1;
        return this;
    }

    public JuliaSetBuilder x2(int x2)
    {
        this.x2 = x2;
        return this;
    }

    public JuliaSetBuilder y1(int y1)
    {
        this.y1 = y1;
        return this;
    }

    public JuliaSetBuilder y2(int y2)
    {
        this.y2 = y2;
        return this;
    }

    public JuliaSetBuilder height(int height)
    {
      this.height = height;
      return this;
    }

    public JuliaSetBuilder width(int width)
    {
      this.width = width;
      return this;
    }

    /**
     * build function that builds a JuliaSet object
     */
    public JuliaSet build()
    {
      return new JuliaSet(this);
    }
  }


  /**
   * Compute divergence on a complex point
   */
  private int computeDivergence(Complex z0)
  {
    int iteration = 0;
    Complex zn = z0;
    while(iteration < maxIter - 1 && zn.modulus() <= RADIUS)
    {
      zn = function.apply(zn);
      iteration++;
    }
    return iteration;
  }

  /**
   * Computes computeDivergence function on each complex point
   */
  private int[][] arrayDivergence()
  {
    	// compute divergence for each complex points
    	int[][] arrayDivergence = new int[width][height];
    	for(int i = 0; i < width -1; i++){
    	    for(int j = 0; j < height -1; j++)
          {
              Complex complex = Complex.of(x1 + (step * i), y2 - (step * j));
    		      arrayDivergence[i][j] = computeDivergence(complex);
    	    }
    	}
      return arrayDivergence;
  }

  /**
   * Draws the image of the JuliaSet
   */
  public void drawImage()
  {
    var img = new BufferedImage(width, height, BufferedImage. TYPE_INT_RGB);
    int[][] arrayDivergence = arrayDivergence();
    for(int i = 0; i < width -1; i++)
    {
      for(int j = 0; j < height -1; j++)
      {
        int div = arrayDivergence[i][j];
        int rgb;
        if(div == 999)
        {
          rgb = 0;
        }
        else
        {
          // rgb=Color.HSBtoRGB((float)div/maxIter, 0.7f, (float)div/maxIter);
          rgb = Color.HSBtoRGB((float)div*20.0f/(float)maxIter,1.0f,1.0f);

        }
        img.setRGB(i,j,rgb);
      }
    }

  	File file = new File(fileName + ".png");

  	try
    {
  	    ImageIO.write(img, "PNG", file);
  	} catch (Exception e)
    {
  	    e.printStackTrace();
  	}
  }

}
