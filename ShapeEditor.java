import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.transform.*;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SnapshotParameters;
import javafx.scene.SubScene;
import javafx.stage.*;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * 
 * @author Kylan Parayao
 * 
 */

public class ShapeEditor extends Application {

	private Button addShapeButton;
	private Button submitButton;
	private BorderPane borderPane;
	private Stage window;
	private Scene sceneShapeEditor;
	private Scene firstScene;
	private Label userPrompt;
	private Label label1;
	private Label label2;
	private Label subSceneColorChangeLabel;
	private Label sphereRadiusLabel;
	private Label boxWidthLabel;
	private Label boxHeightLabel;
	private Label boxDepthLabel;
	private Label cylinderRadiusLabel;
	private Label cylinderHeightLabel;
	private TextField xField;
	private TextField yField;
	private CheckBox boxCB;
	private CheckBox sphereCB;
	private CheckBox cylinderCB;
	private Box box;
	private SubScene shapesSub;
	private Sphere sphere;
	private Cylinder cylinder;
	private Group shapesGroup;
	private PerspectiveCamera pCamera;
	private BorderPane rootNode;
	private HBox topLabelBox;
	private HBox selectShapeBox;
	private VBox midLabel1Box;
	private VBox midLabel2Box;
	private VBox editBox;
	private HBox submitBox;
	private HBox xyBox;
	private VBox shapeTransformBox;
	private VBox sphereBox;
	private VBox cylinderBox;
	private VBox boxBox;
	private VBox shapeBox;
	private int xVal;
	private int yVal;
	private double sphereRadius;
	private TextField sphereRadiusField;
	private Label rotateLabel;
	private Slider rotateXSlider;
	private Slider rotateYSlider;
	private Slider rotateZSlider;
	private Label rotateXLabel;
	private Label rotateYLabel;
	private Label rotateZLabel;
	private Rotate rotateXBox;
	private Rotate rotateYBox;
	private Rotate rotateZBox;
	private Rotate rotateXSphere;
	private Rotate rotateYSphere;
	private Rotate rotateZSphere;
	private Rotate rotateXCylinder;
	private Rotate rotateYCylinder;
	private Rotate rotateZCylinder;
	private Label translateLabel;
	private Slider translateXSlider;
	private Slider translateYSlider;
	private Slider translateZSlider;
	private Label translateXLabel;
	private Label translateYLabel;
	private Label translateZLabel;
	private Translate translateXBox;
	private Translate translateYBox;
	private Translate translateZBox;
	private Translate translateXSphere;
	private Translate translateYSphere;
	private Translate translateZSphere;
	private Translate translateXCylinder;
	private Translate translateYCylinder;
	private Translate translateZCylinder;
	private Label scaleLabel;
	private Slider scaleSlider;
	private Scale scaleBox;
	private Scale scaleSphere;
	private Scale scaleCylinder;
	private double boxWidth;
	private double boxHeight;
	private double boxDepth;
	private TextField boxWidthField;
	private TextField boxHeightField;
	private TextField boxDepthField;
	private double cylinderRadius;
	private double cylinderHeight;
	private TextField cylinderRadiusField;
	private TextField cylinderHeightField;
	private ColorPicker subSceneColorPicker;
	private Label shapeColorPickerLabel;
	private ColorPicker shapeColorPicker;
	private EventHandler<MouseEvent> eventHandlerBox;
	private EventHandler<MouseEvent> eventHandlerSphere;
	private EventHandler<MouseEvent> eventHandlerCylinder;
	private MenuBar menuBar;
	private Menu fileMenu;
	private MenuItem savePictureItem;
	private MenuItem editPictureItem;
	private FileChooser fileChooser;
	private Label selectedShapeLabel;
	private Button resetTransformationButton;
	private Button cancelButton;
	private Button clearShapeButton;
	private ArrayList shapeArray;
	private Shape3D selectedShape = null;
	private String boxString;
	private String sphereString;
	private String cylinderString;
	private boolean boxExists;
	private boolean sphereExists;
	private boolean cylinderExists;

	private String bColor = null;
	private String sColor = null;
	private String cColor = null;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		shapeArray = new ArrayList();
		window = primaryStage;
		topLabelBox = new HBox(10);
		selectShapeBox = new HBox(10);
		midLabel1Box = new VBox(10);
		midLabel2Box = new VBox(10);
		sphereBox = new VBox(10);
		cylinderBox = new VBox(10);
		boxBox = new VBox(10);
		xyBox = new HBox(10);
		editBox = new VBox(10);
		submitBox = new HBox(10);
		subSceneColorPicker = new ColorPicker();
		shapeColorPicker = new ColorPicker();

		label2 = new Label("Determine X-coordinate and Y-coordinate");
		xField = new TextField("0");
		yField = new TextField("0");

		sphereRadiusField = new TextField("2");
		boxWidthField = new TextField("2");
		boxDepthField = new TextField("2");
		boxHeightField = new TextField("2");
		cylinderRadiusField = new TextField("2");
		cylinderHeightField = new TextField("2");
		addShapeButton = new Button("Add Shape");
		submitButton = new Button("Submit");
		cancelButton = new Button("Cancel");

		rootNode = new BorderPane();
		shapesGroup = new Group();
		shapesSub = new SubScene(shapesGroup, 800, 700, true, SceneAntialiasing.DISABLED);

		pCamera = new PerspectiveCamera(true);
		pCamera.getTransforms().addAll(new Translate(0, 0, -60));
		shapesSub.setCamera(pCamera);

		borderPane = new BorderPane();
		userPrompt = new Label("Shape Parameters");
		topLabelBox.getChildren().add(userPrompt);
		topLabelBox.setAlignment(Pos.CENTER);
		topLabelBox.setPadding(new Insets(15));

		boxCB = new CheckBox("Box");
		sphereCB = new CheckBox("Sphere");
		cylinderCB = new CheckBox("Cylinder");
		label1 = new Label("Select a Shape");
		sphereRadiusLabel = new Label("Radius");
		boxWidthLabel = new Label("Width");
		boxHeightLabel = new Label("Height");
		boxDepthLabel = new Label("Depth");
		cylinderRadiusLabel = new Label("Radius");
		cylinderHeightLabel = new Label("Height");

		boxWidthField.setDisable(true);
		boxDepthField.setDisable(true);
		boxHeightField.setDisable(true);
		sphereRadiusField.setDisable(true);
		cylinderRadiusField.setDisable(true);
		cylinderHeightField.setDisable(true);
		submitButton.setDisable(true);

		EventHandler<MouseEvent> boxCBEventHandler = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				boxWidthField.setDisable(false);
				boxDepthField.setDisable(false);
				boxHeightField.setDisable(false);
				submitButton.setDisable(false);

			}

		};

		EventHandler<MouseEvent> sphereCBEventHandler = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				sphereRadiusField.setDisable(false);
				submitButton.setDisable(false);
			}

		};

		EventHandler<MouseEvent> cylinderCBEventHandler = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				cylinderRadiusField.setDisable(false);
				cylinderHeightField.setDisable(false);
				submitButton.setDisable(false);

			}

		};

		boxCB.addEventHandler(MouseEvent.MOUSE_CLICKED, boxCBEventHandler);
		cylinderCB.addEventHandler(MouseEvent.MOUSE_CLICKED, cylinderCBEventHandler);
		sphereCB.addEventHandler(MouseEvent.MOUSE_CLICKED, sphereCBEventHandler);

		boxBox.getChildren().addAll(boxCB, boxWidthLabel, boxWidthField, boxHeightLabel, boxHeightField, boxDepthLabel,
				boxDepthField);
		sphereBox.getChildren().addAll(sphereCB, sphereRadiusLabel, sphereRadiusField);
		cylinderBox.getChildren().addAll(cylinderCB, cylinderRadiusLabel, cylinderRadiusField, cylinderHeightLabel,
				cylinderHeightField);
		selectShapeBox.getChildren().addAll(boxBox, sphereBox, cylinderBox);
		selectShapeBox.setAlignment(Pos.CENTER);
		selectShapeBox.setPadding(new Insets(15));
		midLabel1Box.getChildren().addAll(label1, selectShapeBox);
		midLabel1Box.setAlignment(Pos.CENTER);
		midLabel1Box.setPadding(new Insets(15));

		xyBox.getChildren().addAll(xField, yField);
		xyBox.setAlignment(Pos.CENTER);
		xyBox.setPadding(new Insets(15));

		midLabel2Box.getChildren().addAll(label2, xyBox);
		midLabel2Box.setAlignment(Pos.CENTER);
		midLabel2Box.setPadding(new Insets(15));

		editBox.getChildren().addAll(midLabel1Box, midLabel2Box);

		addShapeButton.setOnAction(event -> {
			window.setScene(sceneShapeEditor);
			refreshShapeEditor();
		});

		rotateLabel = new Label("Rotate Shape Parameters");
		rotateXLabel = new Label("Rotate Across X-Axis");
		rotateYLabel = new Label("Rotate Across Y-Axis");
		rotateZLabel = new Label("Rotate Across Z-Axis");
		translateLabel = new Label("Translate Shape Parameters");
		translateXLabel = new Label("Translate Across X-Axis");
		translateYLabel = new Label("Translate Across Y-Axis");
		translateZLabel = new Label("Translate Across Z-Axis");
		scaleLabel = new Label("Scale Shape");

		rotateXSlider = new Slider(-90, 90, 0);
		rotateXSlider.setShowTickLabels(true);
		rotateXSlider.setShowTickMarks(true);

		rotateYSlider = new Slider(-90, 90, 0);
		rotateYSlider.setShowTickLabels(true);
		rotateYSlider.setShowTickMarks(true);

		rotateZSlider = new Slider(-90, 90, 0);
		rotateZSlider.setShowTickLabels(true);
		rotateZSlider.setShowTickMarks(true);

		translateXSlider = new Slider(-1, 1, 0);
		translateXSlider.setShowTickLabels(true);
		translateXSlider.setShowTickMarks(true);

		translateYSlider = new Slider(-1, 1, 0);
		translateYSlider.setShowTickLabels(true);
		translateYSlider.setShowTickMarks(true);

		translateZSlider = new Slider(-1, 1, 0);
		translateZSlider.setShowTickLabels(true);
		translateZSlider.setShowTickMarks(true);

		scaleSlider = new Slider(.25, 2, 1);
		scaleSlider.setShowTickLabels(true);
		scaleSlider.setShowTickMarks(true);

		shapeColorPickerLabel = new Label("Change Shape Color");

		selectedShapeLabel = new Label("Selected Shape:\t\t");

		eventHandlerBox = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				rotateXSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
					if (selectedShape == box) {
						double boxRotateXAxis = rotateXSlider.getValue();
						rotateXBox = new Rotate(boxRotateXAxis, Rotate.X_AXIS);
						box.getTransforms().add(rotateXBox);
					}
				});
				rotateYSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
					if (selectedShape == box) {
						double boxRotateYAxis = rotateYSlider.getValue();
						rotateYBox = new Rotate(boxRotateYAxis, Rotate.Y_AXIS);
						box.getTransforms().add(rotateYBox);
					}
				});
				rotateZSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
					if (selectedShape == box) {
						double boxRotateZAxis = rotateZSlider.getValue();
						rotateZBox = new Rotate(boxRotateZAxis, Rotate.Z_AXIS);
						box.getTransforms().add(rotateZBox);
					}
				});
				translateXSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
					if (selectedShape == box) {
						double boxTranslateXAxis = translateXSlider.getValue();
						translateXBox = new Translate(boxTranslateXAxis, 0, 0);
						box.getTransforms().add(translateXBox);
					}
				});
				translateYSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
					if (selectedShape == box) {
						double boxTranslateYAxis = translateYSlider.getValue();
						translateYBox = new Translate(0, boxTranslateYAxis, 0);
						box.getTransforms().add(translateYBox);
					}
				});
				translateZSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
					if (selectedShape == box) {
						double boxTranslateZAxis = translateZSlider.getValue();
						translateZBox = new Translate(0, 0, boxTranslateZAxis);
						box.getTransforms().add(translateZBox);
					}
				});

				scaleSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
					if (selectedShape == box) {
						double scaleValue = scaleSlider.getValue();
						scaleBox = new Scale(scaleValue, scaleValue, scaleValue);
						box.getTransforms().add(scaleBox);
					}
				});

				shapeColorPicker.setOnAction(new EventHandler() {
					public void handle(Event e) {
						box.setMaterial(new PhongMaterial(shapeColorPicker.getValue()));
						bColor = shapeColorPicker.getValue().toString();
						box.setUserData(bColor);

					}
				});

				selectedShapeLabel.setText("Selected Shape: Box is selected");
				selectedShape = box;

				boxExists = true;
			}

		};

		eventHandlerSphere = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				rotateXSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
					if (selectedShape == sphere) {
						double sphereRotateXAxis = rotateXSlider.getValue();
						rotateXSphere = new Rotate(sphereRotateXAxis, Rotate.X_AXIS);
						sphere.getTransforms().add(rotateXSphere);
					}
				});
				rotateYSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
					if (selectedShape == sphere) {
						double sphereRotateYAxis = rotateYSlider.getValue();
						rotateYSphere = new Rotate(sphereRotateYAxis, Rotate.Y_AXIS);
						sphere.getTransforms().add(rotateYSphere);
					}
				});
				rotateZSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
					if (selectedShape == sphere) {
						double sphereRotateZAxis = rotateZSlider.getValue();
						rotateZSphere = new Rotate(sphereRotateZAxis, Rotate.Z_AXIS);
						sphere.getTransforms().add(rotateZSphere);
					}
				});
				translateXSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
					if (selectedShape == sphere) {
						double sphereTranslateXAxis = translateXSlider.getValue();
						translateXSphere = new Translate(sphereTranslateXAxis, 0, 0);
						sphere.getTransforms().add(translateXSphere);
					}
				});
				translateYSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
					if (selectedShape == sphere) {
						double sphereTranslateYAxis = translateYSlider.getValue();
						translateYSphere = new Translate(0, sphereTranslateYAxis, 0);
						sphere.getTransforms().add(translateYSphere);
					}
				});
				translateZSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
					if (selectedShape == sphere) {
						double sphereTranslateZAxis = translateZSlider.getValue();
						translateZSphere = new Translate(0, 0, sphereTranslateZAxis);
						sphere.getTransforms().add(translateZSphere);
					}
				});
				scaleSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
					if (selectedShape == sphere) {
						double scaleValue = scaleSlider.getValue();
						scaleSphere = new Scale(scaleValue, scaleValue, scaleValue);
						sphere.getTransforms().add(scaleSphere);
					}
				});

				shapeColorPicker.setOnAction(new EventHandler() {
					public void handle(Event e) {
						sphere.setMaterial(new PhongMaterial(shapeColorPicker.getValue()));
						sColor = shapeColorPicker.getValue().toString();
						sphere.setUserData(sColor);
					}
				});

				selectedShapeLabel.setText("Selected Shape: Sphere is selected");
				selectedShape = sphere;
				sphereExists = true;

			}

		};

		eventHandlerCylinder = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				rotateXSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
					if (selectedShape == cylinder) {
						double cylinderRotateXAxis = rotateXSlider.getValue();
						rotateXCylinder = new Rotate(cylinderRotateXAxis, Rotate.X_AXIS);
						cylinder.getTransforms().add(rotateXCylinder);
					}
				});
				rotateYSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
					if (selectedShape == cylinder) {
						double cylinderRotateYAxis = rotateYSlider.getValue();
						rotateYCylinder = new Rotate(cylinderRotateYAxis, Rotate.Y_AXIS);
						cylinder.getTransforms().add(rotateYCylinder);
					}
				});
				rotateZSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
					if (selectedShape == cylinder) {
						double cylinderRotateZAxis = rotateZSlider.getValue();
						rotateZCylinder = new Rotate(cylinderRotateZAxis, Rotate.Z_AXIS);
						cylinder.getTransforms().add(rotateZCylinder);
					}
				});
				translateXSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
					if (selectedShape == cylinder) {
						double cylinderTranslateXAxis = translateXSlider.getValue();
						translateXCylinder = new Translate(cylinderTranslateXAxis, 0, 0);
						cylinder.getTransforms().add(translateXCylinder);
					}
				});
				translateYSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
					if (selectedShape == cylinder) {
						double cylinderTranslateYAxis = translateYSlider.getValue();
						translateYCylinder = new Translate(0, cylinderTranslateYAxis, 0);
						cylinder.getTransforms().add(translateYCylinder);
					}
				});
				translateZSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
					if (selectedShape == cylinder) {
						double cylinderTranslateZAxis = translateZSlider.getValue();
						translateZCylinder = new Translate(0, 0, cylinderTranslateZAxis);
						cylinder.getTransforms().add(translateZCylinder);
					}
				});
				scaleSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
					if (selectedShape == cylinder) {
						double scaleValue = scaleSlider.getValue();
						scaleCylinder = new Scale(scaleValue, scaleValue, scaleValue);
						cylinder.getTransforms().add(scaleCylinder);
					}
				});

				shapeColorPicker.setOnAction(new EventHandler() {
					public void handle(Event e) {
						cylinder.setMaterial(new PhongMaterial(shapeColorPicker.getValue()));
						cColor = shapeColorPicker.getValue().toString();
						cylinder.setUserData(cColor);
					}
				});

				selectedShapeLabel.setText("Selected Shape: Cylinder is selected");
				selectedShape = cylinder;
				cylinderExists = true;

			}

		};

		submitButton.setOnAction(event -> {
			xVal = Integer.parseInt(xField.getText());
			yVal = Integer.parseInt(yField.getText());
			if (xVal > 20 || yVal > 20 || xVal < -20 || yVal < -20) {
				Alert errorAlert = new Alert(AlertType.ERROR,
						"The parameters entered for X and Y are not valid. Default to origin.");
				errorAlert.showAndWait();
				xVal = 0;
				yVal = 0;
			}
			if (boxCB.isSelected()) {
				boxWidth = Double.parseDouble(boxWidthField.getText());
				boxHeight = Double.parseDouble(boxHeightField.getText());
				boxDepth = Double.parseDouble(boxDepthField.getText());
				if (boxWidth < 0 || boxHeight < 0 || boxDepth < 0) {
					Alert errorAlert = new Alert(AlertType.ERROR,
							"The parameters entered for box radius is not valid. Default to 2.");
					errorAlert.showAndWait();
					boxWidth = 2;
					boxHeight = 2;
					boxDepth = 2;
				}
				box = new Box(boxWidth, boxHeight, boxDepth);
				shapesGroup.getChildren().add(box);
				box.getTransforms().addAll(new Translate(xVal, yVal, 0));
				boxString = new String(
						"Box" + "," + boxWidth + "," + boxHeight + "," + boxDepth + "," + xVal + "," + yVal);
				shapeArray.add(boxString);
				box.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandlerBox);
			}
			if (sphereCB.isSelected()) {
				sphereRadius = Double.parseDouble(sphereRadiusField.getText());
				if (sphereRadius < 0) {
					Alert errorAlert = new Alert(AlertType.ERROR,
							"The parameter entered for sphere is not valid. Default to 2.");
					errorAlert.showAndWait();
					sphereRadius = 2;
				}
				sphere = new Sphere(sphereRadius);
				shapesGroup.getChildren().add(sphere);
				shapesGroup.getChildren().get(0).getLocalToSceneTransform();
				shapesGroup.getChildren().get(0).getLocalToParentTransform();
				shapesGroup.getChildren().get(0).getRotationAxis();
				sphere.getTransforms().addAll(new Translate(xVal, yVal, 0));
				sphereString = new String("Sphere" + "," + sphereRadius + "," + xVal + "," + yVal);
				shapeArray.add(sphereString);
				sphere.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandlerSphere);
			}
			if (cylinderCB.isSelected()) {
				cylinderRadius = Double.parseDouble(cylinderRadiusField.getText());
				cylinderHeight = Double.parseDouble(cylinderHeightField.getText());
				if (cylinderRadius < 0 || cylinderHeight < 0) {
					Alert errorAlert = new Alert(AlertType.ERROR,
							"The parameters entered for box cylinder is not valid. Default to 2.");
					errorAlert.showAndWait();
					cylinderRadius = 2;
					cylinderHeight = 2;
				}
				cylinder = new Cylinder(cylinderRadius, cylinderHeight);
				shapesGroup.getChildren().add(cylinder);
				cylinder.getTransforms().addAll(new Translate(xVal, yVal, 0));
				cylinderString = new String(
						"Cylinder" + "," + cylinderRadius + "," + cylinderHeight + "," + xVal + "," + yVal);
				shapeArray.add(cylinderString);
				cylinder.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandlerCylinder);
			}
			pCamera = new PerspectiveCamera(true);
			pCamera.getTransforms().addAll(new Translate(0, 0, -60));
			shapesSub.setCamera(pCamera);
			window.setScene(firstScene);
			refreshFirstScene();
		});

		cancelButton.setOnAction(event -> {
			window.setScene(firstScene);
			refreshFirstScene();
		});

		resetTransformationButton = new Button("Reset Transformations");

		resetTransformationButton.setOnAction(event -> {
			rotateXSlider.setValue(0);
			rotateYSlider.setValue(0);
			rotateZSlider.setValue(0);
			translateXSlider.setValue(0);
			translateYSlider.setValue(0);
			translateZSlider.setValue(0);
			scaleSlider.setValue(1);
		});

		clearShapeButton = new Button("Clear Shapes");

		clearShapeButton.setOnAction(event -> {
			shapesGroup.getChildren().removeAll(box, sphere, cylinder);
		});

		subSceneColorPicker.setValue(Color.AZURE);

		subSceneColorPicker.setOnAction(new EventHandler() {
			public void handle(Event e) {
				shapesSub.setFill(subSceneColorPicker.getValue());
			}
		});

		subSceneColorChangeLabel = new Label("Change SubScene Color: ");
		shapesSub.setFill(Color.AZURE);

		shapeBox = new VBox(10);
		HBox subSceneColorBox = new HBox(10);
		subSceneColorBox.getChildren().addAll(addShapeButton, clearShapeButton, subSceneColorChangeLabel,
				subSceneColorPicker, selectedShapeLabel);
		subSceneColorBox.setAlignment(Pos.CENTER);

		shapeBox.getChildren().addAll(shapesSub, subSceneColorBox);
		shapeBox.setAlignment(Pos.CENTER);
		shapeBox.setPadding(new Insets(15));

		shapeTransformBox = new VBox(10);

		shapeTransformBox.getChildren().addAll(resetTransformationButton, rotateLabel, rotateXLabel, rotateXSlider,
				rotateYLabel, rotateYSlider, rotateZLabel, rotateZSlider, translateLabel, translateXLabel,
				translateXSlider, translateYLabel, translateYSlider, translateZLabel, translateZSlider, scaleLabel,
				scaleSlider, shapeColorPickerLabel, shapeColorPicker);
		shapeTransformBox.setPadding(new Insets(10));
		shapeTransformBox.setAlignment(Pos.CENTER);

		menuBar = new MenuBar();
		fileMenu = new Menu("File");
		MenuItem saveSceneItem = new MenuItem("Save Scene");
		savePictureItem = new MenuItem("Save As Image");
		editPictureItem = new MenuItem("Load & Edit Scene");
		fileMenu.getItems().addAll(savePictureItem, saveSceneItem, editPictureItem);
		menuBar.getMenus().add(fileMenu);

		rootNode.setTop(menuBar);
		rootNode.setCenter(shapeBox);
		rootNode.setRight(shapeTransformBox);

		submitBox.getChildren().addAll(submitButton, cancelButton);
		submitBox.setPadding(new Insets(10));
		submitBox.setAlignment(Pos.CENTER);

		borderPane.setTop(topLabelBox);
		borderPane.setCenter(editBox);
		borderPane.setBottom(submitBox);

		sceneShapeEditor = new Scene(borderPane, 600, 600);

		firstScene = new Scene(rootNode, 1000, 1000);

		ImageView imageView = new ImageView();

		savePictureItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				fileChooser = new FileChooser();

				WritableImage image = shapesSub.snapshot(new SnapshotParameters(), null);
				imageView.setImage(image);

				File file = fileChooser.showSaveDialog(window);
				if (file != null) {
					try {
						ImageIO.write(SwingFXUtils.fromFXImage(imageView.getImage(), null), "png", file);
					} catch (IOException ex) {
						Logger.getLogger(ShapeEditor.class.getName()).log(Level.SEVERE, null, ex);
					}
				}

			}

		});

		saveSceneItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				fileChooser = new FileChooser();

				WritableImage image = shapesSub.snapshot(new SnapshotParameters(), null);
				imageView.setImage(image);
//				ObservableList<Transform> trx = shapesGroup.getChildren().get(0).getTransforms();

				File file = fileChooser.showSaveDialog(window);

				if (file != null) {
					try {
						FileWriter fileWriter = new FileWriter(file.getPath(), true);
						PrintWriter outputFile = new PrintWriter(fileWriter);

						for (int i = 0; i < shapeArray.size(); i++) {
							String data = (String) shapeArray.get(i);
							// outputFile.println(data);
							String userData = (String) shapesGroup.getChildren().get(i).getUserData();
							data += "," + userData;
							outputFile.println(data);
							String type[] = data.split(",");
							ObservableList transform = shapesGroup.getChildren().get(i).getTransforms();
							transform.forEach((tr) -> {
								outputFile.println(type[0] + tr);
							});

						}
						outputFile.close();
					} catch (IOException ex) {
						Logger.getLogger(ShapeEditor.class.getName()).log(Level.SEVERE, null, ex);
					}
				}

			}

		});

		editPictureItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				File selectedFile = fileChooser.showOpenDialog(primaryStage);
				if (selectedFile != null) {

					BufferedReader br = null;
					try {
						br = new BufferedReader(new FileReader(selectedFile));
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String st;
					try {
						while ((st = br.readLine()) != null) {
							String data = st;
							String dataObject[] = data.split(",");

							if (dataObject[0].equals("Box")) {
								double w = Double.parseDouble(dataObject[1]);
								double h = Double.parseDouble(dataObject[2]);
								double d = Double.parseDouble(dataObject[3]);
								String color = dataObject[6];
								box = new Box(w, h, d);
								shapesGroup.getChildren().add(box);
								if (!color.equals("null")) {
									box.setMaterial(new PhongMaterial(Color.web(color)));
								}

								box.getTransforms().addAll(new Translate(xVal, yVal, 0));
								box.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandlerBox);
							} else if (dataObject[0].equals("Sphere")) {
								double r = Double.parseDouble(dataObject[1]);
								String color = dataObject[4];

								sphere = new Sphere(r);
								shapesGroup.getChildren().add(sphere);
								if (!color.equals("null")) {
									sphere.setMaterial(new PhongMaterial(Color.web(color)));
								}
								// sphere.setMaterial(new PhongMaterial(Color.web(color)));

								sphere.getTransforms().addAll(new Translate(xVal, yVal, 0));
								sphere.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandlerSphere);
							} else if (dataObject[0].equals("Cylinder")) {
								double r = Double.parseDouble(dataObject[1]);
								double h = Double.parseDouble(dataObject[2]);
								String color = dataObject[5];

								cylinder = new Cylinder(r, h);
								shapesGroup.getChildren().add(cylinder);
								if (!color.equals("null")) {
									cylinder.setMaterial(new PhongMaterial(Color.web(color)));
								}

								cylinder.getTransforms().addAll(new Translate(xVal, yVal, 0));
								cylinder.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandlerCylinder);
							}

							// SphereTranslate [x=0.025316455696202445, y=0.0, z=0.0]
							// KYLAN FOR FOLLOW TRANSLATE
							if (data.startsWith("SphereTranslate")) {
								String result = data.substring(data.indexOf("[") + 1, data.indexOf("]"));
								String location[] = result.split(",");
								String x[] = location[0].split("=");
								String y[] = location[1].split("=");
								String z[] = location[2].split("=");
								sphere.getTransforms().addAll(new Translate(Double.parseDouble(x[1]),
										Double.parseDouble(y[1]), Double.parseDouble(z[1])));

							}
							// KYLAN FOR FOLLOW

							if (data.startsWith("BoxTranslate")) {
								String result = data.substring(data.indexOf("[") + 1, data.indexOf("]"));
								String location[] = result.split(",");
								String x[] = location[0].split("=");
								String y[] = location[1].split("=");
								String z[] = location[2].split("=");
								box.getTransforms().addAll(new Translate(Double.parseDouble(x[1]),
										Double.parseDouble(y[1]), Double.parseDouble(z[1])));
							}

							if (data.startsWith("CylinderTranslate")) {
								String result = data.substring(data.indexOf("[") + 1, data.indexOf("]"));
								String location[] = result.split(",");
								String x[] = location[0].split("=");
								String y[] = location[1].split("=");
								String z[] = location[2].split("=");
								cylinder.getTransforms().addAll(new Translate(Double.parseDouble(x[1]),
										Double.parseDouble(y[1]), Double.parseDouble(z[1])));
							}

							// SphereRotate [angle=46.70886075949369, pivotX=0.0, pivotY=0.0, pivotZ=0.0,
							// axis=Point3D [x = 0.0, y = 0.0, z = 1.0]]
							if (data.startsWith("SphereRotate")) {
								String result = data.substring(data.indexOf("[") + 1, data.indexOf("]"));
								String location[] = result.split(",");
								String angle[] = location[0].split("=");
								String pivotX[] = location[1].split("=");
								String pivotY[] = location[2].split("=");
								String pivotZ[] = location[3].split("=");
								String x[] = location[4].split("=");
								String y[] = location[5].split("=");
								String z[] = location[6].split("=");
								if (x[2].contains("1.")) {
									sphere.getTransforms()
											.addAll(new Rotate(Double.parseDouble(angle[1]), Rotate.X_AXIS));
								} else if (y[1].contains("1.")) {
									sphere.getTransforms()
											.addAll(new Rotate(Double.parseDouble(angle[1]), Rotate.Y_AXIS));

								} else if (z[1].contains("1.")) {
									sphere.getTransforms()
											.addAll(new Rotate(Double.parseDouble(angle[1]), Rotate.Z_AXIS));
								}
								// sphere.getTransforms().addAll(new Rotate(Double.parseDouble(angle[1]),
								// Double.parseDouble(pivotX[1]), Double.parseDouble(pivotY[1]),
								// Double.parseDouble(pivotZ[1])));
							}

							if (data.startsWith("CylinderRotate")) {
								String result = data.substring(data.indexOf("[") + 1, data.indexOf("]"));
								String location[] = result.split(",");
								String angle[] = location[0].split("=");
								String pivotX[] = location[1].split("=");
								String pivotY[] = location[2].split("=");
								String pivotZ[] = location[3].split("=");
								String x[] = location[4].split("=");
								String y[] = location[5].split("=");
								String z[] = location[6].split("=");
								if (x[2].contains("1.")) {
									cylinder.getTransforms()
											.addAll(new Rotate(Double.parseDouble(angle[1]), Rotate.X_AXIS));
								} else if (y[1].contains("1.")) {
									cylinder.getTransforms()
											.addAll(new Rotate(Double.parseDouble(angle[1]), Rotate.Y_AXIS));

								} else if (z[1].contains("1.")) {
									cylinder.getTransforms()
											.addAll(new Rotate(Double.parseDouble(angle[1]), Rotate.Z_AXIS));
								}
							}

							if (data.startsWith("BoxRotate")) {
								String result = data.substring(data.indexOf("[") + 1, data.indexOf("]"));
								String location[] = result.split(",");
								String angle[] = location[0].split("=");
								String pivotX[] = location[1].split("=");
								String pivotY[] = location[2].split("=");
								String pivotZ[] = location[3].split("=");
								String x[] = location[4].split("=");
								String y[] = location[5].split("=");
								String z[] = location[6].split("=");
								if (x[2].contains("1.")) {
									box.getTransforms().addAll(new Rotate(Double.parseDouble(angle[1]), Rotate.X_AXIS));
								} else if (y[1].contains("1.")) {
									box.getTransforms().addAll(new Rotate(Double.parseDouble(angle[1]), Rotate.Y_AXIS));

								} else if (z[1].contains("1.")) {
									box.getTransforms().addAll(new Rotate(Double.parseDouble(angle[1]), Rotate.Z_AXIS));
								}
								// box.getTransforms().addAll(new Rotate(Double.parseDouble(angle[1]),
								// Double.parseDouble(pivotX[1]), Double.parseDouble(pivotY[1]),
								// Double.parseDouble(pivotZ[1])));
							}

							// SphereScale [x=1.1882911392405062, y=1.1882911392405062,
							// z=1.1882911392405062, pivotX=0.0, pivotY=0.0, pivotZ=0.0]

							if (data.startsWith("SphereScale")) {
								String result = data.substring(data.indexOf("[") + 1, data.indexOf("]"));
								String location[] = result.split(",");
								String x[] = location[0].split("=");
								String y[] = location[1].split("=");
								String z[] = location[2].split("=");
								sphere.getTransforms().addAll(new Scale(Double.parseDouble(x[1]),
										Double.parseDouble(y[1]), Double.parseDouble(z[1])));
							}

							if (data.startsWith("BoxScale")) {
								String result = data.substring(data.indexOf("[") + 1, data.indexOf("]"));
								String location[] = result.split(",");
								String x[] = location[0].split("=");
								String y[] = location[1].split("=");
								String z[] = location[2].split("=");
								box.getTransforms().addAll(new Scale(Double.parseDouble(x[1]), Double.parseDouble(y[1]),
										Double.parseDouble(z[1])));
							}

							if (data.startsWith("CylinderScale")) {
								String result = data.substring(data.indexOf("[") + 1, data.indexOf("]"));
								String location[] = result.split(",");
								String x[] = location[0].split("=");
								String y[] = location[1].split("=");
								String z[] = location[2].split("=");
								cylinder.getTransforms().addAll(new Scale(Double.parseDouble(x[1]),
										Double.parseDouble(y[1]), Double.parseDouble(z[1])));
							}

						}
					} catch (NumberFormatException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					pCamera = new PerspectiveCamera(true);
					pCamera.getTransforms().addAll(new Translate(0, 0, -60));
					shapesSub.setCamera(pCamera);
					window.setScene(firstScene);
					refreshFirstScene();

				}

			}

		});

		window.setScene(firstScene);
		window.setTitle("Shape Editor");
		window.show();

	}

	private void refreshShapeEditor() {
		boxCB.setSelected(false);
		cylinderCB.setSelected(false);
		sphereCB.setSelected(false);
		boxWidthField.setDisable(true);
		boxDepthField.setDisable(true);
		boxHeightField.setDisable(true);
		sphereRadiusField.setDisable(true);
		cylinderRadiusField.setDisable(true);
		cylinderHeightField.setDisable(true);
		submitButton.setDisable(true);
	}

	private void refreshFirstScene() {
		rotateXSlider.setValue(0);
		rotateYSlider.setValue(0);
		rotateZSlider.setValue(0);
		translateXSlider.setValue(0);
		translateYSlider.setValue(0);
		translateZSlider.setValue(0);
		scaleSlider.setValue(1);
	}

	public static void save(Serializable data, String fileName) throws Exception {
		try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
			oos.writeObject(data);
		}
	}

	public static Object load(String fileName) throws Exception {
		try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
			return ois.readObject();
		}

	}
}
