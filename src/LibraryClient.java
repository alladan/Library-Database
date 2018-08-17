import javafx.application.Application;
import javafx.stage.Stage;

/*
 * Allan Yu CMSC214 4/26/17 Project 13
 * Eclipse SE1.8
 */

//The actual running main set-up
public class LibraryClient extends Application {

	public void start(Stage primaryStage) {
		
		//Create both panes
		ConnectDBPane c = new ConnectDBPane();
		LibraryControlPane lib=new LibraryControlPane();
		
		//First show the connection pane
		primaryStage = c.getStage(lib);
		primaryStage.show();
	}

	/**
	 * The main method is only needed for the IDE with limited JavaFX support.
	 * Not needed for running from the command line.
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
}//end class