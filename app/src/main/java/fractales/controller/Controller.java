package fractales.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import fractales.model.*;
import fractales.model.Fractal.FractalType;
import fractales.utils.*;
import java.util.*;
import javafx.scene.image.*;

public class Controller {

    // fractal selection
    @FXML private MenuButton fractalSelection;

    // displays the name of the selected fractal
    @FXML private Label fractalSelected;

    // the build button
    @FXML private Button buildButton;

    // the build button
    @FXML private Button saveButton;

    // the maximal number of iterations
    @FXML private TextField maxIterationInput;

    // the minimal value along the x-axis
    @FXML private TextField xMinInput;

    // the maximal value along the x-axis
    @FXML private TextField xMaxInput;

    // the minimal value along the y-axis
    @FXML private TextField yMinInput;

    // the maximal value along the y-axis
    @FXML private TextField yMaxInput;

    // the real part of the alpha factor in the iteration function
    @FXML private TextField alphaRealPartInput;

    // the imaginary part of the alpha factor in the iteration function
    @FXML private TextField alphaImPartInput;

    // the real part of the beta factor in the iteration function
    @FXML private TextField betaRealPartInput;

    // the imaginary part of the beta factor in the iteration function
    @FXML private TextField betaImPartInput;

    // the real part of the complex constant
    @FXML private TextField cstRealPartInput;

    // the imaginary part of the complex constant
    @FXML private TextField cstImPartInput;

    // the filename in which to store the image
    @FXML private TextField filenameInput;

    // the discrete step for the complex rectangle
    @FXML private TextField discreteStepInput;

    // width of the image
    @FXML private TextField imageWidthInput;

    // height of the image
    @FXML private TextField imageHeightInput;

    @FXML private Label stateLabel;

    // the fractal to build
    private Fractal fractalToBuild;

    // the fractal image
    private FractalImage fractalImage;

    // the image view that displays the fractal image
    @FXML private ImageView fractalDisplay;

    // the zoom button
    @FXML private Button zoomInButton;

    // the selection for the zoom zone
    @FXML private MenuButton zoomZoneSelection;

    //The last fractal generated
    private String lastFractal;

    // the name of the build file
    @FXML private TextField buildFromFileInput;

    // the button for building the fractal from a file
    @FXML private Button buildFromFileButton;

    // the alpha factor for the color function
    @FXML private TextField alphaColorInput;

    // the beta factor for the color function
    @FXML private TextField betaColorInput;

    // the gamma factor for the color function
    @FXML private TextField gammaColorInput;

    // initialize the state of the view upon launch
    @FXML private void initialize(){
	fractalSelected.setText("Select a fractal to build");
	initFractalSelection();
	initZoomZoneSelection();
	disableFields(true);
	buildButton.setDisable(true);
	buildButton.setOnAction(e -> buildFractal());
	zoomInButton.setOnAction(e -> zoomInAction());
	zoomInButton.setDisable(true);
  zoomZoneSelection.setDisable(true);
	buildFromFileButton.setOnAction(e -> buildFromFile());
    }

    // tries to read a double input
    private double readDoubleInput(TextField doubleInput)
	throws IllegalArgumentException {
	Scanner sc = new Scanner(doubleInput.getText());
	if(sc.hasNextDouble())
	    return sc.nextDouble();
	throw new IllegalArgumentException();
    }

    // tries to read an int input
    private int readIntInput(TextField intInput)
	throws IllegalArgumentException {
	Scanner sc = new Scanner(intInput.getText());
	if(sc.hasNextInt())
	    return sc.nextInt();
	throw new IllegalArgumentException();
    }

    // returns true if the text field is not empty, false otherwise
    private boolean isInputGiven(TextField input){
	return input.getLength() > 0;
    }

    // displays an error alert with the specified parameters
    private void showErrorAlert(){
	Alert alert = new Alert(Alert.AlertType.ERROR);
	alert.setContentText("You have provided invalid input.\n"
			     + "Please provide valid input");
	alert.showAndWait();
    }

    // builds the fractal Julia
    private Julia buildJuliaFractal(){
	Julia.Builder builder = new Julia.Builder();

	// read the complex constant if given
	if(isInputGiven(cstRealPartInput) && isInputGiven(cstImPartInput)){
	    double a = readDoubleInput(cstRealPartInput);
	    double b = readDoubleInput(cstImPartInput);
	    builder = builder.complexConstant(Complex.of(a,b));
	}

	// read the iteration function factors if given
	if(isInputGiven(alphaRealPartInput)
	   && isInputGiven(alphaImPartInput)
	   && isInputGiven(betaRealPartInput)
	   && isInputGiven(betaImPartInput)){
	    double ar = readDoubleInput(alphaRealPartInput);
	    double ai = readDoubleInput(alphaImPartInput);
	    double br = readDoubleInput(betaRealPartInput);
	    double bi = readDoubleInput(betaImPartInput);

	    builder = builder
		.iterationFunction(Complex.of(ar, ai), Complex.of(br, bi));
	}

	// read iteration if given
	if(isInputGiven(maxIterationInput)){
	    int i = readIntInput(maxIterationInput);
	    builder = builder.maxIteration(i);
	}

	// read step if given
	if(isInputGiven(discreteStepInput)){
	    double s = readDoubleInput(discreteStepInput);
	    builder = builder.discreteStep(s);
	}

	// read complex rectangle if given
	if(isInputGiven(xMinInput)
	   && isInputGiven(xMaxInput)
	   && isInputGiven(yMinInput)
	   && isInputGiven(yMaxInput)){
	    double xMin = readDoubleInput(xMinInput);
	    double xMax = readDoubleInput(xMaxInput);
	    double yMin = readDoubleInput(yMinInput);
	    double yMax = readDoubleInput(yMaxInput);
	    builder = builder.xMin(xMin).xMax(xMax).yMin(yMin).yMax(yMax);
	}

	// read image dimensions if given
	if(isInputGiven(imageWidthInput)){
	    int w = readIntInput(imageWidthInput);
	    builder = builder.imageWidth(w);
	}

	if(isInputGiven(imageHeightInput)){
	    int h = readIntInput(imageHeightInput);
	    builder = builder.imageHeight(h);
	}

	// read filename
	if(isInputGiven(filenameInput)){
	    String n = filenameInput.getText();
	    builder = builder.fileName(n);
	}

  if(isInputGiven(alphaColorInput)
     && isInputGiven(betaColorInput)
     && isInputGiven(gammaColorInput)){
       float alpha = (float)(readDoubleInput(alphaColorInput));
       float beta = (float)(readDoubleInput(betaColorInput));
       float gamma = (float)(readDoubleInput(gammaColorInput));
       builder = builder.colorFunction(alpha,beta,gamma);
     }

	return builder.build();
    }

    // builds the Mandelbrot fractal
    private Mandelbrot buildMandelbrotFractal() throws IllegalArgumentException {
	Mandelbrot.Builder builder = new Mandelbrot.Builder();

	// read iteration if given
	if(isInputGiven(maxIterationInput)){
	    int i = readIntInput(maxIterationInput);
	    builder = builder.maxIteration(i);
	}

	// read step if given
	if(isInputGiven(discreteStepInput)){
	    double s = readDoubleInput(discreteStepInput);
	    builder = builder.discreteStep(s);
	}

	// read complex rectangle if given
	if(isInputGiven(xMinInput)
	   && isInputGiven(xMaxInput)
	   && isInputGiven(yMinInput)
	   && isInputGiven(yMaxInput)){
	    double xMin = readDoubleInput(xMinInput);
	    double xMax = readDoubleInput(xMaxInput);
	    double yMin = readDoubleInput(yMinInput);
	    double yMax = readDoubleInput(yMaxInput);
	    builder = builder.xMin(xMin).xMax(xMax).yMin(yMin).yMax(yMax);
	}

	// read image dimensions if given
	if(isInputGiven(imageWidthInput)){
	    int w = readIntInput(imageWidthInput);
	    builder = builder.imageWidth(w);
	}

	if(isInputGiven(imageHeightInput)){
	    int h = readIntInput(imageHeightInput);
	    builder = builder.imageHeight(h);
	}

	// read filename
	if(isInputGiven(filenameInput)){
	    String n = filenameInput.getText();
	    builder = builder.fileName(n);
	}

  if(isInputGiven(alphaColorInput)
	   && isInputGiven(betaColorInput)
     && isInputGiven(gammaColorInput)){
       float alpha = (float)(readDoubleInput(alphaColorInput));
       float beta = (float)(readDoubleInput(betaColorInput));
       float gamma = (float)(readDoubleInput(gammaColorInput));
       builder = builder.colorFunction(alpha,beta,gamma);
     }

	return builder.build();
    }

    // builds the fractal
    private void buildFractal(){
	try {
	    if(fractalSelected.getText() == FractalType.JULIA.name()){
		fractalToBuild = buildJuliaFractal();
		lastFractal = "Julia";
	    }
	    if(fractalSelected.getText() == FractalType.MANDELBROT.name()){
		fractalToBuild = buildMandelbrotFractal();
		lastFractal = "Mandelbrot";
	    }
	} catch(Exception e){
	    showErrorAlert();
	    fractalToBuild = null;
	}
	if(fractalToBuild != null){
	    fractalSelected.setText("Select a fractal to build");
	    buildButton.setDisable(true);
	    buildFromFileButton.setDisable(true);
	    fractalImage = FractalImage.of(fractalToBuild);
	    fractalImage.saveFile(); // saves the png image
	    displayImage(); // displays it onto the screen
	    zoomInButton.setDisable(false);
      zoomZoneSelection.setDisable(false);
      zoomZoneSelection.setText("Zoom zone");
	    buildFromFileButton.setDisable(false);
	}
    }

    // displays the generated fractal onto the screen
    private void displayImage(){
	// the image is saved in /tmp
	String path = "file://" + fractalImage.getPath();
	Image image = new Image(path);
	fractalDisplay.setImage(image);
	fractalDisplay.setPreserveRatio(true);
	fractalDisplay.setFitWidth(1360);
	fractalDisplay.setFitHeight(1000);
    }

    // if boolean disable is true, disables all text fields, enables otherwise
    private void disableFields(boolean disable){
	filenameInput.setDisable(disable);
	cstImPartInput.setDisable(disable);
	cstRealPartInput.setDisable(disable);
	betaImPartInput.setDisable(disable);
	betaRealPartInput.setDisable(disable);
	alphaImPartInput.setDisable(disable);
	alphaRealPartInput.setDisable(disable);
	yMaxInput.setDisable(disable);
	yMinInput.setDisable(disable);
	xMaxInput.setDisable(disable);
	xMinInput.setDisable(disable);
	maxIterationInput.setDisable(disable);
	imageWidthInput.setDisable(disable);
	imageHeightInput.setDisable(disable);
	discreteStepInput.setDisable(disable);
  alphaColorInput.setDisable(disable);
  betaColorInput.setDisable(disable);
  gammaColorInput.setDisable(disable);
    }

    // displays allowed fields accoding to the selected fractal
    private void displayAllowedFields(FractalType fractalType){
	disableFields(false);
	if(fractalType == FractalType.MANDELBROT){
	    alphaRealPartInput.setDisable(true);
	    alphaImPartInput.setDisable(true);
	    betaRealPartInput.setDisable(true);
	    betaImPartInput.setDisable(true);
	    cstRealPartInput.setDisable(true);
	    cstImPartInput.setDisable(true);
	}
    }

    // initializes the fractal selection in the gui
    private void initFractalSelection(){
	fractalSelection.getItems()
	    .add(new FractalMenuItem(FractalType.JULIA));
	fractalSelection.getItems()
	    .add(new FractalMenuItem(FractalType.MANDELBROT));
    }

    private void buildFromFile(){
	buildFromFileButton.setDisable(true);
	if(isInputGiven(buildFromFileInput)){
	    String path = "/tmp/" + buildFromFileInput.getText() + ".txt";
	    fractalToBuild = FractalText.textToImage(path);
	    if(fractalToBuild != null){
		fractalImage = FractalImage.of(fractalToBuild);
		fractalImage.saveFile();
		displayImage();
		buildFromFileButton.setDisable(false);
    zoomInButton.setDisable(false);
    zoomZoneSelection.setDisable(false);
	    } else {
		showErrorAlert();
		buildFromFileButton.setDisable(false);
	    }
	} else {
	    showErrorAlert();
	    buildFromFileButton.setDisable(false);
	}
    }

    // This menu item encapsulates the corresponding fractal name
    // and enables/disables text fields accordingly
    private class FractalMenuItem extends MenuItem {

	FractalType fractalType;

	FractalMenuItem(FractalType fractalType){
	    super(fractalType.name());
	    this.fractalType = fractalType;
	    disableFields(true);
	    this.setOnAction(e -> {
		    displayAllowedFields(fractalType);
		    fractalSelected.setText(fractalType.name());
		    buildButton.setDisable(false);
		});
	}
    }

    // Initializes the zoom zone selection in the gui
    private void initZoomZoneSelection(){
      zoomZoneSelection.getItems().add(new ZoomMenuItem("TOP LEFT"));
      zoomZoneSelection.getItems().add(new ZoomMenuItem("TOP RIGHT"));
      zoomZoneSelection.getItems().add(new ZoomMenuItem("BOTTOM LEFT"));
      zoomZoneSelection.getItems().add(new ZoomMenuItem("BOTTOM RIGHT"));
    }

    // This menu item gives the choice to the user for the zoom zone.
    private class ZoomMenuItem extends MenuItem {
      ZoomMenuItem(String name){
        super(name);
        this.setOnAction(e -> {
          zoomZoneSelection.setText(name);
          zoomInButton.setDisable(false);
        });
      }
    }

    // zooms into the displayed image
    private void zoomInAction(){

      if(lastFractal.equals("Julia")){
        Julia.Builder builder = new Julia.Builder();
        builder.imageHeight((int)(fractalToBuild.getHeight()/1.25));
        builder.imageWidth((int)(fractalToBuild.getWidth()/1.25));
        builder.fileName(fractalToBuild.getFileName());
        builder.discreteStep(fractalToBuild.getDiscreteStep());
        builder.iterationFunction(((Julia)fractalToBuild).getAlphaFactor(),
				  ((Julia)fractalToBuild).getBetaFactor());
        builder.complexConstant(((Julia)fractalToBuild).getComplexConstant());
        builder.colorFunction(fractalToBuild.getAlphaColor(),
          fractalToBuild.getBetaColor(), fractalToBuild.getGammaColor());
          
        if(zoomZoneSelection.getText().equals("TOP LEFT")){
          builder.xMin(fractalToBuild.getXMin());
          builder.xMax(fractalToBuild.getXMax()/2);
          builder.yMin(fractalToBuild.getYMin());
          builder.yMax(fractalToBuild.getYMax());
        }
        else if(zoomZoneSelection.getText().equals("TOP RIGHT")){
          builder.xMin(fractalToBuild.getXMin()/2);
          builder.xMax(fractalToBuild.getXMax());
          builder.yMin(fractalToBuild.getYMin());
          builder.yMax(fractalToBuild.getYMax());
        }
        else if(zoomZoneSelection.getText().equals("BOTTOM LEFT")){
          builder.xMin(fractalToBuild.getXMin());
          builder.xMax(fractalToBuild.getXMax());
          builder.yMin(fractalToBuild.getYMin());
          builder.yMax(fractalToBuild.getYMax()/2);
        }
        else if(zoomZoneSelection.getText().equals("BOTTOM RIGHT")){
          builder.xMin(fractalToBuild.getXMin()/2);
          builder.xMax(fractalToBuild.getXMax());
          builder.yMin(fractalToBuild.getYMin());
          builder.yMax(fractalToBuild.getYMax()/2);
        }
        else
        {
          stateLabel.setText("Choose a zone for the zoom");
          return;
        }
        fractalToBuild = builder.build();
      }
      else if (lastFractal.equals("Mandelbrot")){

        Mandelbrot.Builder builder = new Mandelbrot.Builder();
        builder.imageHeight((int)(fractalToBuild.getHeight()/1.25));
        builder.imageWidth((int)(fractalToBuild.getWidth()/1.25));
        builder.fileName(fractalToBuild.getFileName());
        builder.discreteStep(fractalToBuild.getDiscreteStep());
        builder.colorFunction(fractalToBuild.getAlphaColor(),
          fractalToBuild.getBetaColor(), fractalToBuild.getGammaColor());

        if(zoomZoneSelection.getText().equals("TOP LEFT")){
          builder.xMin(fractalToBuild.getXMin());
          builder.xMax(fractalToBuild.getXMax()/2);
          builder.yMin(fractalToBuild.getYMin());
          builder.yMax(fractalToBuild.getYMax());
        }
        else if(zoomZoneSelection.getText().equals("TOP RIGHT")){
          builder.xMin(fractalToBuild.getXMin()/2);
          builder.xMax(fractalToBuild.getXMax());
          builder.yMin(fractalToBuild.getYMin());
          builder.yMax(fractalToBuild.getYMax());
        }
        else if(zoomZoneSelection.getText().equals("BOTTOM LEFT")){
          builder.xMin(fractalToBuild.getXMin());
          builder.xMax(fractalToBuild.getXMax());
          builder.yMin(fractalToBuild.getYMin());
          builder.yMax(fractalToBuild.getYMax()/2);
        }
        else if(zoomZoneSelection.getText().equals("BOTTOM RIGHT")){
          builder.xMin(fractalToBuild.getXMin()/2);
          builder.xMax(fractalToBuild.getXMax());
          builder.yMin(fractalToBuild.getYMin());
          builder.yMax(fractalToBuild.getYMax()/2);
        }
        else
        {
          stateLabel.setText("Choose a zone for the zoom");
          return;
        }
        fractalToBuild = builder.build();
      }
	    fractalSelected.setText("Select a fractal to build");
	    buildButton.setDisable(true);
	    fractalImage = FractalImage.of(fractalToBuild);
	    fractalImage.saveFile(); // saves the png image
	    displayImage(); // displays it onto the screen
	    stateLabel.setText("Image "
			       + fractalToBuild.getFileName() + " zoomed !");
    }
}
