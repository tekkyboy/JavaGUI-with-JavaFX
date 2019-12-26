import java.io.File;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.Scene;
import javafx.stage.*;
import javafx.stage.FileChooser.ExtensionFilter;

public class FilePlayer extends Application {
	private Button oButton;
	private Button playButton;
	private Button pauseButton;
	private Button stopButton;
	private FileChooser fileChooser;
	private File selectedFile;
	private File soundFile;
	private Stage window;
	private Scene sceneMusicEditor;
	private Media media;
	private MediaPlayer player;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		window = primaryStage;
		oButton = new Button("O");
		playButton = new Button("Play");
		pauseButton = new Button("Pause");
		stopButton = new Button("Stop");
		VBox vbox = new VBox(10);
		HBox musicBox = new HBox(10);

		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(25));
		vbox.getChildren().add(oButton);
		oButton.setOnAction(event -> {
			fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().add( new ExtensionFilter( "Audio Files", "*.mp3"));
			selectedFile = fileChooser.showOpenDialog(primaryStage);
			soundFile = new File(selectedFile.getPath());
			media = new Media(soundFile.toURI().toString());
			player = new MediaPlayer(media);
			window.setScene(sceneMusicEditor);
		});

		playButton.setOnAction(event -> {
			player.play();
		});

		pauseButton.setOnAction(event -> {
			player.pause();
		});

		stopButton.setOnAction(event -> {
			player.stop();
		});

		musicBox.getChildren().addAll(playButton, pauseButton, stopButton);
		musicBox.setAlignment(Pos.CENTER);
		musicBox.setPadding(new Insets(15));

		sceneMusicEditor = new Scene(musicBox, 300, 150);

		window.setScene(new Scene(vbox, 200, 100));
		window.show();
	}

}
