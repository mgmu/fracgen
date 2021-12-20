package fractales.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import fractales.model.*;
import fractales.utils.FractalImage;
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

    // initialize the state of the view upon launch
    @FXML private void initialize(){
	fractalSelected.setText("Select a fractal to build");
	initFractalSelection();
	disableFields(true);
	buildButton.setDisable(true);
	buildButton.setOnAction(e -> buildFractal());
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

    // builds the fractal Julia
    private Julia buildJuliaFractal(){
	Julia.Builder builder = new Julia.Builder();

	try{

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
	    if(isInputGiven(imageWidthInput) && isInputGiven(imageHeightInput)){
		int w = readIntInput(imageWidthInput);
		int h = readIntInput(imageHeightInput);
		builder = builder.imageWidth(w).imageHeight(h);
	    }

	    // read filename
	    if(isInputGiven(filenameInput)){
		String n = filenameInput.getText();
		builder = builder.fileName(n);
	    }

	    return builder.build();

	} catch(IllegalArgumentException iae){
	    // show alert box
	}

	return null;
    }

    // builds the Mandelbrot fractal
    private Mandelbrot buildMandelbrotFractal() {
	Mandelbrot.Builder builder = new Mandelbrot.Builder();
	try{

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
	    if(isInputGiven(imageWidthInput) && isInputGiven(imageHeightInput)){
		int w = readIntInput(imageWidthInput);
		int h = readIntInput(imageHeightInput);
		builder = builder.imageWidth(w).imageHeight(h);
	    }

	    // read filename
	    if(isInputGiven(filenameInput)){
		String n = filenameInput.getText();
		builder = builder.fileName(n);
	    }

	    return builder.build();

	} catch(IllegalArgumentException iae){
	    // show alert box
	}

	return null;
    }

    // builds the fractal
    private void buildFractal(){
	System.out.println("PRESSED BUILD BUTTON");
	if(fractalSelected.getText().equals("Julia"))
	    fractalToBuild = buildJuliaFractal();
	if(fractalSelected.getText().equals("Mandelbrot"))
	    fractalToBuild = buildMandelbrotFractal();

	if(fractalToBuild != null){
	    stateLabel.setText("Building...");
	    fractalSelected.setText("Select a fractal to build");
	    buildButton.setDisable(true);
	    System.out.println("Building...");
	    fractalImage = FractalImage.of(fractalToBuild);
	    System.out.println("Saving image...");
	    fractalImage.saveFile(); // saves the png image
	    System.out.println("Display image");
	    displayImage(); // displays it onto the screen
	    stateLabel.setText("Built image "
			       + fractalToBuild.getFileName() + " !");
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
    }

    // displays allowed fields accoding to the selected fractal
    private void displayAllowedFields(String fractalName){
	disableFields(false);
	if(fractalName.equals("Mandelbrot")){
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
	    .add(new FractalMenuItem("Julia"));
	fractalSelection.getItems()
	    .add(new FractalMenuItem("Mandelbrot"));
    }

    // This menu item encapsulates the corresponding fractal name
    // and enables/disables text fields accordingly
    private class FractalMenuItem extends MenuItem {

	FractalMenuItem(String name){
	    super(name);
	    disableFields(true);
	    this.setOnAction(e -> {
		    displayAllowedFields(name);
		    fractalSelected.setText(name);
		    buildButton.setDisable(false);
		});
	}
    }
}
