package fractales;

import fractales.model.*;

import java.util.function.Function;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Color;
import org.apache.commons.cli.*;

public class App {

    // console and gui options
    private static final Option CONSOLE_OPT =
	Option.builder("csl")
	.longOpt("console")
	.desc("Launches application in console mode")
	.build();

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

    // private static final Option COLOR_FUN_OPT;

    // Julia options only
    private static final Option COMPLEX_CST_OPT =
	Option.builder("constant")
	.longOpt("complexConstant")
	.hasArg()
	.valueSeparator()
	.desc("Sets the value of the Complex constant for divergence")
	.build();

    // private static final Option ITER_FUN_OPT;
    
    public static void main(String[] args) {

    // 	if(args[0].equals("s")){
    // 	    //launches shell version

    // 	    //parsing the commandline
    // 	    CommandLine commandLine;
    // 	    HelpFormatter helper = new HelpFormatter();
    // 	    CommandLineParser parser = new DefaultParser();

    // 	    // Option help = new Option("help", false, "help");
    // 	    // Option help = Option.builder("help")
    // 	    // .hasArg()
    // 	    // .desc("help")
    // 	    // .build();

    // 	    Option complex = Option.builder("c")
    // 		.argName("complex")
    // 		.hasArgs()
    // 		.required(true)
    // 		.valueSeparator(';')
    // 		.desc("Complex number format <real;imaginary> like <5;6> for 5 + 6i")
    // 		.build();

    // 	    Option rectangle = Option.builder("r")
    // 		.argName("int;int;int;int")
    // 		.hasArgs()
    // 		.longOpt("rec")
    // 		.valueSeparator(';')
    // 		.desc("rectangle")
    // 		.build();

    // 	    Option stepOption = Option.builder("step")
    // 		.argName("double")
    // 		.hasArg()
    // 		.desc("step of discretisation")
    // 		.build();

    // 	    Option height_width = Option.builder("hw")
    // 		.argName("int;int")
    // 		.hasArgs()
    // 		.longOpt("heightwidth")
    // 		.valueSeparator(';')
    // 		.desc("height and width of the matrix")
    // 		.build();

    // 	    Option maxOption = Option.builder("max")
    // 		.argName("int")
    // 		.hasArg()
    // 		.desc("maximum iterations")
    // 		.build();

    // 	    Options options = new Options();
    // 	    options.addOption(complex);
    // 	    options.addOption(rectangle);
    // 	    options.addOption(stepOption);
    // 	    options.addOption(height_width);
    // 	    options.addOption(maxOption);
    // 	    // options.addOption(help);

    // 	    try {
    // 		commandLine = parser.parse(options, args);

    // 		// if(commandLine.hasOption("help")) {
    // 		//   System.out.println("s : shell");
    // 		//   System.out.println("g : gui");
    // 		//   helper.printHelp("Usage:", options);
    // 		// System.exit(0);
    // 		// }

    // 		if(commandLine.hasOption("c")){

    // 		    Double r = Double.parseDouble(commandLine.getOptionValues("c")[0]);
    // 		    Double im = Double.parseDouble(commandLine.getOptionValues("c")[1]);
    // 		    final Complex c = Complex.of(r,im);
    // 		    Julia.Builder jsb = new Julia.Builder (c,Complex.ONE, Complex.ZERO);

    // 		    if(commandLine.hasOption("r")){
    // 			int x1 = Integer.parseInt(commandLine.getOptionValues("r")[0]);
    // 			int x2 = Integer.parseInt(commandLine.getOptionValues("r")[1]);
    // 			int y1 = Integer.parseInt(commandLine.getOptionValues("r")[2]);
    // 			int y2 = Integer.parseInt(commandLine.getOptionValues("r")[3]);
    // 			jsb.x1(x1).x2(x2).y1(y1).y2(y2);
    // 		    }

    // 		    if(commandLine.hasOption("step")){
    // 			double tmp = Double.parseDouble(commandLine.getOptionValue("step"));
    // 			jsb.step(tmp);
    // 		    }


    // 		    if(commandLine.hasOption("hw")){
    // 			int height = Integer.parseInt(commandLine.getOptionValues("hw")[0]);
    // 			int width = Integer.parseInt(commandLine.getOptionValues("hw")[1]);
    // 			jsb.height(height).width(width);
    // 		    }

    // 		    if(commandLine.hasOption("max")){
    // 			int maxIter = Integer.parseInt(commandLine.getOptionValue("max"));
    // 			jsb.maxIter(maxIter);
    // 		    }

    // 		    Julia js = jsb.build();
    // 		    js.drawImage();
    // 		}

    // 	    } catch(Exception e){
    // 		System.out.println(e.getMessage());
    // 		helper.printHelp("Usage:", options);
    // 		System.exit(0);
    // 	    }
    // 	} else if (args[0] == "g"){
    // 	    //launches gui version
    // 	    System.out.println("gui");
    // 	} else {
    // 	    //error
    // 	    System.out.println(args[0]);
    // 	}
    }
}
