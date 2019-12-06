import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Translate;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.stage.*;

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
	private VBox rootNode;
	private HBox topLabelBox;
	private HBox selectShapeBox;
	private VBox midLabel1Box;
	private VBox midLabel2Box;
	private VBox editBox;
	private HBox submitBox;
	private HBox xyBox;
	private VBox sphereBox;
	private VBox cylinderBox;
	private VBox boxBox;
	private int xVal;
	private int yVal;
	private double sphereRadius;
	private TextField sphereRadiusField;
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

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
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

		label2 = new Label("Determine X-Position and Y-Position");
		xField = new TextField();
		yField = new TextField();
		sphereRadiusField = new TextField();
		boxWidthField = new TextField();
		boxDepthField = new TextField();
		boxHeightField = new TextField();
		cylinderRadiusField = new TextField();
		cylinderHeightField = new TextField();
		addShapeButton = new Button("Add Shape");
		submitButton = new Button("Submit");

		rootNode = new VBox(10);
		rootNode.setAlignment(Pos.CENTER);
		shapesGroup = new Group();
		shapesSub = new SubScene(shapesGroup, 500, 500, true, SceneAntialiasing.DISABLED);

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
		label1 = new Label("Select Shapes");
		sphereRadiusLabel = new Label("Radius");
		boxWidthLabel = new Label("Width");
		boxHeightLabel = new Label("Height");
		boxDepthLabel = new Label("Depth");
		cylinderRadiusLabel = new Label("Radius");
		cylinderHeightLabel = new Label("Height");

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

		submitButton.setOnAction(event -> {
			xVal = Integer.parseInt(xField.getText());
			yVal = Integer.parseInt(yField.getText());
			if (boxCB.isSelected()) {
				boxWidth = Double.parseDouble(boxWidthField.getText());
				boxHeight = Double.parseDouble(boxHeightField.getText());
				boxDepth = Double.parseDouble(boxDepthField.getText());
				box = new Box(boxWidth, boxHeight, boxDepth);
				shapesGroup.getChildren().add(box);
				box.getTransforms().addAll(new Translate(xVal, yVal, 0));
			}
			if (sphereCB.isSelected()) {
				sphereRadius = Double.parseDouble(sphereRadiusField.getText());
				sphere = new Sphere(sphereRadius);
				shapesGroup.getChildren().add(sphere);
				sphere.getTransforms().addAll(new Translate(xVal, yVal, 0));
			}
			if (cylinderCB.isSelected()) {
				cylinderRadius = Double.parseDouble(cylinderRadiusField.getText());
				cylinderHeight = Double.parseDouble(cylinderHeightField.getText());
				cylinder = new Cylinder(cylinderRadius, cylinderHeight);
				shapesGroup.getChildren().add(cylinder);
				cylinder.getTransforms().addAll(new Translate(xVal, yVal, 0));
			}
			pCamera = new PerspectiveCamera(true);
			pCamera.getTransforms().addAll(new Translate(0, 0, -60));
			shapesSub.setCamera(pCamera);
			window.setScene(firstScene);
		});

		shapesSub.setFill(Color.AZURE);
		rootNode.getChildren().addAll(shapesSub, addShapeButton);

		submitBox.getChildren().add(submitButton);
		submitBox.setPadding(new Insets(10));
		submitBox.setAlignment(Pos.CENTER);

		borderPane.setTop(topLabelBox);
		borderPane.setCenter(editBox);
		borderPane.setBottom(submitBox);

		sceneShapeEditor = new Scene(borderPane, 600, 600);

		firstScene = new Scene(rootNode, 600, 600);
		window.setScene(firstScene);
		window.setTitle("Shape Editor");
		window.show();
	}

	private void refreshShapeEditor() {
		xField.clear();
		yField.clear();
		boxCB.setSelected(false);
		cylinderCB.setSelected(false);
		sphereCB.setSelected(false);
		boxWidthField.clear();
		boxHeightField.clear();
		boxDepthField.clear();
		sphereRadiusField.clear();
		cylinderRadiusField.clear();
		cylinderHeightField.clear();
	}
}
