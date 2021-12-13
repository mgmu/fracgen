package fractales;

import fractales.model.*;
import fractales.utils.FractalImage;

import java.util.function.Function;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Color;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.BasicParser;

public class App {

    public static void main(String[] args) throws ParseException {

    if(args[0].equals("s")) {
      //launches shell version

      //parsing the commandline
      CommandLine commandLine;
      HelpFormatter helper = new HelpFormatter();
      CommandLineParser parser = new DefaultParser();

      // Option help = new Option("help", false, "help");
      // Option help = Option.builder("help")
      // .hasArg()
      // .desc("help")
      // .build();

      Option complex = Option.builder("c")
      .argName("complex")
      .hasArgs()
      .required(true)
      .valueSeparator(';')
      .desc("Complex number format <real;imaginary> like <5;6> for 5 + 6i")
      .build();

      Option rectangle = Option.builder("r")
      .argName("int;int;int;int")
      .hasArgs()
      .longOpt("rec")
      .valueSeparator(';')
      .desc("rectangle")
      .build();

      Option stepOption = Option.builder("step")
      .argName("double")
      .hasArg()
      .desc("step of discretisation")
      .build();

      Option height_width = Option.builder("hw")
      .argName("int;int")
      .hasArgs()
      .longOpt("heightwidth")
      .valueSeparator(';')
      .desc("height and width of the matrix")
      .build();

      Option maxOption = Option.builder("max")
      .argName("int")
      .hasArg()
      .desc("maximum iterations")
      .build();

      Options options = new Options();
      options.addOption(complex);
      options.addOption(rectangle);
      options.addOption(stepOption);
      options.addOption(height_width);
      options.addOption(maxOption);
      // options.addOption(help);

      try {
        commandLine = parser.parse(options, args);

        // if(commandLine.hasOption("help")) {
        //   System.out.println("s : shell");
        //   System.out.println("g : gui");
        //   helper.printHelp("Usage:", options);
          // System.exit(0);
        // }

        if(commandLine.hasOption("c")){

          Double r = Double.parseDouble(commandLine.getOptionValues("c")[0]);
          Double im = Double.parseDouble(commandLine.getOptionValues("c")[1]);
          final Complex c = Complex.of(r,im);
          Function<Complex, Complex> iterFun = (zn) -> (zn.multiply(zn)).add(c);

          JuliaSet.JuliaSetBuilder jsb = new JuliaSet.JuliaSetBuilder (c,iterFun);

          if(commandLine.hasOption("r")){
            int x1 = Integer.parseInt(commandLine.getOptionValues("r")[0]);
            int x2 = Integer.parseInt(commandLine.getOptionValues("r")[1]);
            int y1 = Integer.parseInt(commandLine.getOptionValues("r")[2]);
            int y2 = Integer.parseInt(commandLine.getOptionValues("r")[3]);
            jsb.x1(x1).x2(x2).y1(y1).y2(y2);
          }

          if(commandLine.hasOption("step")){
            double tmp = Double.parseDouble(commandLine.getOptionValue("step"));
            jsb.step(tmp);
          }


            if(commandLine.hasOption("hw")) {
              int height = Integer.parseInt(commandLine.getOptionValues("hw")[0]);
              int width = Integer.parseInt(commandLine.getOptionValues("hw")[1]);
              jsb.height(height).width(width);
            }

            if(commandLine.hasOption("max")) {
              int maxIter = Integer.parseInt(commandLine.getOptionValue("max"));
              jsb.maxIter(maxIter);
            }

            JuliaSet js = jsb.build();
            js.drawImage();
        }

      }
      catch(Exception e) {
        System.out.println(e.getMessage());
        helper.printHelp("Usage:", options);
        System.exit(0);
      }
    }
    else if (args[0] == "g") {
      //launches gui version
      System.out.println("gui");
    }
    else
    {
      //error
      System.out.println(args[0]);
>>>>>>> parsing some options from commandline
    }
  }
}
