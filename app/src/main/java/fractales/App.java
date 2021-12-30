package fractales;

import fractales.model.*;
import fractales.utils.FractalImage;
import fractales.utils.FractalText;
import java.util.function.Function;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.Color;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.fxml.*;
import org.apache.commons.cli.*;

public class App extends Application {

    private static final Option GUI_OPT =
	Option.builder("gui")
	.longOpt("graphics")
	.desc("Launches application in GUI mode")
	.build();

    // Julia and Mandelbrot options
    private static final Option MAX_ITER_OPT =
	Option.builder("maxIter")
	.longOpt("maxIteration")
	.hasArg()
	.valueSeparator()
	.desc("Sets the maximal value of iterations for divergence computing")
	.build();

    private static final Option STEP_OPT =
	Option.builder("step")
	.longOpt("discreteStep")
	.hasArg()
	.valueSeparator()
	.desc("Sets the discrete step value for the Complex plane")
	.build();

    private static final Option XMIN_OPT =
	Option.builder("xMin")
	.hasArg()
	.valueSeparator()
	.desc("Sets the minimal value along the x-axis")
	.build();

    private static final Option XMAX_OPT =
	Option.builder("xMax")
	.hasArg()
	.valueSeparator()
	.desc("Sets the maximal value along the x-axis")
	.build();

    private static final Option YMIN_OPT =
	Option.builder("yMin")
	.hasArg()
	.valueSeparator()
	.desc("Sets the minimal value along the y-axis")
	.build();

    private static final Option YMAX_OPT =
	Option.builder("yMax")
	.hasArg()
	.valueSeparator()
	.desc("Sets the maximal value along the y-axis")
	.build();

    private static final Option IMG_W_OPT =
	Option.builder("w")
	.longOpt("imageWidth")
	.hasArg()
	.valueSeparator()
	.desc("Sets the width of the image in which to store the fractal")
	.build();

    private static final Option IMG_H_OPT =
	Option.builder("h")
	.longOpt("imageHeight")
	.hasArg()
	.valueSeparator()
	.desc("Sets the height of the image in which to store the fractal")
	.build();

    private static final Option FILENAME_OPT =
	Option.builder("name")
	.longOpt("filename")
	.hasArg()
	.valueSeparator()
	.desc("Sets the name of the while in which to store the image")
	.build();

    private static final Option COLOR_FUN_OPT =
	Option.builder("colorFun")
	.longOpt("colorFunction")
	.hasArg()
	.numberOfArgs(3)
	.valueSeparator(';')
	.desc("The function color")
	.build();

    private static final Option JULIA_OPT =
	Option.builder("julia")
	.desc("Julia generation")
	.build();

    private static final Option MANDELBROT_OPT =
	Option.builder("mandelbrot")
	.desc("Mandelbrot generation")
	.build();

    // Julia options only
    private static final Option COMPLEX_CST_OPT =
	Option.builder("constant")
	.longOpt("complexConstant")
	.hasArg()
	.numberOfArgs(2)
	.valueSeparator(';')
	.desc("Sets the value of the Complex constant for divergence")
	.build();

    private static final Option ITER_FUN_OPT =
	Option.builder("iterFun")
	.longOpt("iterationFunction")
	.hasArg()
	.numberOfArgs(4)
	.valueSeparator(';')
	.desc("The iteration function")
	.build();

    private static final Option BUILD_FROM_FILE_OPT =
  Option.builder("buildFrom")
  .longOpt("buildFromFile")
  .hasArg()
  .desc("Builds an image from a text file.")
  .build();

    @Override
    public void start(Stage primaryStage) throws IOException {
	primaryStage.setTitle("Fractal Builder"); // gui title
	primaryStage.setResizable(false); // gui can't be resized
	// load scene from fxml file
	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/App.fxml"));
	Parent root = (Parent) loader.load();
	Scene scene = new Scene(root);
	// sets the primary stage scene to the loaded scene
	primaryStage.setScene(scene);
	primaryStage.show();
    }

    public static void main(String[] args) {

	// add all options to implemented options list
	Options options = new Options();
	options.addOption(GUI_OPT);
	options.addOption(MAX_ITER_OPT);
	options.addOption(STEP_OPT);
	options.addOption(XMIN_OPT);
	options.addOption(XMAX_OPT);
	options.addOption(YMIN_OPT);
	options.addOption(YMAX_OPT);
	options.addOption(IMG_W_OPT);
	options.addOption(IMG_H_OPT);
	options.addOption(FILENAME_OPT);
	options.addOption(COLOR_FUN_OPT);
	options.addOption(JULIA_OPT);
	options.addOption(MANDELBROT_OPT);
	options.addOption(COMPLEX_CST_OPT);
	options.addOption(ITER_FUN_OPT);
	options.addOption(BUILD_FROM_FILE_OPT);

	String set = "";

	Julia.Builder juliaBuilder = new Julia.Builder();
	Mandelbrot.Builder mandelbrotBuilder = new Mandelbrot.Builder();

	CommandLine commandLine;
	HelpFormatter helper = new HelpFormatter();
	CommandLineParser parser = new DefaultParser();

	try{
	    //parsing the commandline
	    commandLine = parser.parse(options, args);

	    // if -gui or --graphics is present, launch gui version
	    if(commandLine.hasOption("gui")){
		launch(args);
	    }
      else if(commandLine.hasOption("buildFrom")) {
        // builds an image from a text file
        String path = "/tmp/" + commandLine.getOptionValue("buildFrom")+ ".txt";
  	    Fractal fractal = FractalText.textToImage(path);
  	    if(fractal != null){
      		FractalImage fractalImage = FractalImage.of(fractal);
      		fractalImage.saveFile();
          System.exit(0);
        } else {
          System.out.println("Invalid file");
        }
      }
      else { // launches console version

    // must take exactly one of the two args
		// can only generate one fractal
		if(commandLine.hasOption("julia")
		   == commandLine.hasOption("mandelbrot")){
		    helper.printHelp(" ", options);
		    System.exit(0);
		}

		// user wants Julia fractal
		if(commandLine.hasOption("julia")){
		    set = "julia";

		    if(commandLine.hasOption("constant")){

			//parsing constant
			double constantReal =
			    Double.parseDouble(commandLine
					       .getOptionValues("constant")[0]);
			double constantIm =
			    Double.parseDouble(commandLine
					       .getOptionValues("constant")[1]);

			Complex constant = Complex.of(constantReal,constantIm);
			juliaBuilder.complexConstant(constant);
		    }

		    if(commandLine.hasOption("iterFun")){

			//parsing iterFun
			//parsing alpha factor
			double alphaReal =
			    Double.parseDouble(commandLine
					       .getOptionValues("iterFun")[0]);
			double alphaIm =
			    Double.parseDouble(commandLine
					       .getOptionValues("iterFun")[1]);

			Complex alpha = Complex.of(alphaReal,alphaIm);

			//parsing beta factor
			double betaReal =
			    Double.parseDouble(commandLine
					       .getOptionValues("iterFun")[2]);
			double betaIm =
			    Double.parseDouble(commandLine
					       .getOptionValues("iterFun")[3]);

			Complex beta = Complex.of(betaReal,betaIm);

			juliaBuilder.iterationFunction(alpha,beta);
		    }
		} else if (commandLine.hasOption("mandelbrot")){
		    set = "mandelbrot";
		}

		// parse iteration input
		if(commandLine.hasOption("maxIter")){
		    int maxIter =
			Integer.parseInt(commandLine.getOptionValue("maxIter"));
		    if(set.equals("julia")){
			juliaBuilder.maxIteration(maxIter);
		    } else if(set.equals("mandelbrot")){
			mandelbrotBuilder.maxIteration(maxIter);
		    }
		}

		// parse step input
		if(commandLine.hasOption("step")){
		    double step =
			Double.parseDouble(commandLine.getOptionValue("step"));
		    if(set.equals("julia")){
			juliaBuilder.discreteStep(step);
		    } else if(set.equals("mandelbrot")){
			mandelbrotBuilder.discreteStep(step);
		    }
		}

		// parse xMin input
		if(commandLine.hasOption("xMin")){
		    int xMin =
			Integer.parseInt(commandLine.getOptionValue("xMin"));
		    if(set.equals("julia")){
			juliaBuilder.xMin(xMin);
		    } else if(set.equals("mandelbrot")){
			mandelbrotBuilder.xMin(xMin);
		    }
		}

		// parse xMax input
		if(commandLine.hasOption("xMax")){
		    int xMax =
			Integer.parseInt(commandLine.getOptionValue("xMax"));
		    if(set.equals("julia")){
			juliaBuilder.xMax(xMax);
		    } else if(set.equals("mandelbrot")){
			mandelbrotBuilder.xMax(xMax);
		    }
		}

		// parse yMin input
		if(commandLine.hasOption("yMin")){
		    int yMin =
			Integer.parseInt(commandLine.getOptionValue("yMin"));
		    if(set.equals("julia")){
			juliaBuilder.yMin(yMin);
		    } else if(set.equals("mandelbrot")){
			mandelbrotBuilder.yMin(yMin);
		    }
		}

		// parse yMax input
		if(commandLine.hasOption("yMax")){
		    int yMax =
			Integer.parseInt(commandLine.getOptionValue("yMax"));
		    if(set.equals("julia")){
			juliaBuilder.yMax(yMax);
		    } else if(set.equals("mandelbrot")){
			mandelbrotBuilder.yMax(yMax);
		    }
		}

		// parse height input
		if(commandLine.hasOption("h")){
		    int imageHeight =
			Integer.parseInt(commandLine.getOptionValue("h"));
		    if(set.equals("julia")){
			juliaBuilder.imageHeight(imageHeight);
		    } else if(set.equals("mandelbrot")){
			mandelbrotBuilder.imageHeight(imageHeight);
		    }
		}

		// parse width input
		if(commandLine.hasOption("w")){
		    int imageWidth =
			Integer.parseInt(commandLine.getOptionValue("w"));
		    if(set.equals("julia")){
			juliaBuilder.imageWidth(imageWidth);
		    } else if(set.equals("mandelbrot")){
			mandelbrotBuilder.imageWidth(imageWidth);
		    }
		}

		// parse filename input
		if(commandLine.hasOption("name")){
		    if(set.equals("julia")){
			juliaBuilder.fileName(commandLine
					      .getOptionValue("name"));
		    } else if(set.equals("mandelbrot")){
			mandelbrotBuilder.fileName(commandLine
						   .getOptionValue("name"));
		    }
		}

		// parse color function input
		if(commandLine.hasOption("colorFun")){
		    double alpha =
			Double.parseDouble(commandLine
					   .getOptionValues("colorFun")[0]);
		    double beta =
			Double.parseDouble(commandLine
					   .getOptionValues("colorFun")[1]);
		    double gamma =
			Double.parseDouble(commandLine
					   .getOptionValues("colorFun")[2]);
		    //initialize color function
		    if(set.equals("julia")){
			juliaBuilder.colorFunction((float)alpha, (float)beta,
						   (float)gamma);
		    } else if(set.equals("mandelbrot")){
			mandelbrotBuilder
			    .colorFunction((float)alpha, (float)beta,
					   (float)gamma);
		    }
		}

		Fractal fractal = null;
		if(set.equals("julia")){
		    fractal = juliaBuilder.build();
		} else if(set.equals("mandelbrot")){
		    fractal = mandelbrotBuilder.build();
		}

		FractalImage fi = FractalImage.of(fractal);
		System.out.println("Saving...");
		fi.saveFile();
		System.out.println("Saved at " + fi.getPath());
		System.out.println("Exiting");
		System.exit(0);
	    }
	} catch(Exception e){
	    System.out.println(e.getMessage());
	    helper.printHelp(" ", options);
	    System.exit(-1);
	}
    }
}
