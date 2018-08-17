import java.io.File;
import java.sql.*;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/*
 * Allan Yu CMSC214 4/26/17 Project 13
 * Eclipse SE1.8
 */

//The Connection to Database screen GUI
public class ConnectDBPane extends Pane {

	// If connection is made
	private boolean connected = false;

	private boolean tableSet = false;

	private boolean once = false;// add server lists only once

	// Connection to the database
	private Connection connection;

	// DBC info for a database connection
	private TextField tfUsername = new TextField();
	private PasswordField pfPassword = new PasswordField();
	private ComboBox<String> cboURL = new ComboBox<>();
	private ComboBox<String> cboDriver = new ComboBox<>();

	// Buttons and Labels...
	private Button btConnectDB = new Button("Connect to Database");
	private Button btCreateTables = new Button("Create Tables");
	private Button btClose = new Button("Close Setup");
	private Label lblConnectionStatus = new Label("No connection now");

	// Uses a SQL script to setup database.
	private void importSQL() throws Exception {

		Scanner s = new Scanner(new File("bookslibrary.sql"));

		s.useDelimiter("(;(\r)?\n)|(--\n)");
		Statement st = null;
		try {
			st = connection.createStatement();
			while (s.hasNext()) {
				String line = s.next();
				if (line.startsWith("/*!") && line.endsWith("*/")) {
					int i = line.indexOf(' ');
					line = line.substring(i + 1, line.length() - " */".length());
				}

				if (line.trim().length() > 0) {
					st.execute(line);
				}
			}
		} finally {
			if (st != null)
				st.close();
		}

		s.close();

	}// end method

	// Returns if table is set
	public boolean tableSet() {

		return tableSet;
	}// end method

	// Check for connection
	public boolean connectionEstablished() {

		return connected;
	}// end method

	// set the Stage
	public Stage getStage(LibraryControlPane lib) {

		// Combobox setup

		if (once == false) {
			cboURL.getItems()
					.addAll(FXCollections.observableArrayList("jdbc:mysql://localhost/bookslibrary",
							"jdbc:mysql://liang.armstrong.edu/javabook", "jdbc:odbc:exampleMDBDataSource",
							"jdbc:oracle:thin:@liang.armstrong.edu:1521:orcl"));
			cboURL.setPrefWidth(250);
			cboURL.getSelectionModel().selectFirst();

			cboDriver.getItems().addAll(FXCollections.observableArrayList("com.mysql.jdbc.Driver",
					"sun.jdbc.odbc.dbcOdbcDriver", "oracle.jdbc.driver.OracleDriver"));
			cboDriver.getSelectionModel().selectFirst();
			once = true;
		}

		// Create UI for connecting to the database
		GridPane gridPane = new GridPane();
		gridPane.add(cboURL, 1, 0);
		gridPane.add(cboDriver, 1, 1);
		gridPane.add(tfUsername, 1, 2);
		gridPane.add(pfPassword, 1, 3);
		gridPane.add(new Label("*JDBC Driver"), 0, 0);
		gridPane.add(new Label("Database URL"), 0, 1);
		gridPane.add(new Label("Username"), 0, 2);
		gridPane.add(new Label("Password"), 0, 3);
		gridPane.setAlignment(Pos.CENTER);

		HBox hBoxConnection = new HBox();
		hBoxConnection.getChildren().addAll(btConnectDB, btCreateTables, btClose);
		hBoxConnection.setAlignment(Pos.CENTER);

		VBox vBoxConnection = new VBox();
		vBoxConnection.getChildren().addAll(lblConnectionStatus, gridPane, hBoxConnection);
		vBoxConnection.setAlignment(Pos.CENTER);
		gridPane.setStyle("-fx-border-color: black;");

		BorderPane borderPane = new BorderPane();
		borderPane.setTop(vBoxConnection);
		borderPane.setCenter(hBoxConnection);

		Stage primaryStage = new Stage();
		// Create a scene and place it in the stage
		Scene scene = new Scene(borderPane, 320, 150);
		primaryStage.setTitle("Database Setup"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage

		// Only let tables be created if connection made
		if (!connected)
			btCreateTables.setDisable(true);

		// Create tables button
		btCreateTables.setOnAction(e -> {

			try {
				importSQL();
				tableSet = true;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});// end lambda

		// Create a connection button
		btConnectDB.setOnAction(e -> {
			connectToDB();

			if (connected == true)
				btCreateTables.setDisable(false);

		});// end lambda

		btClose.setOnAction(e -> {

			primaryStage.close();
			lib.getStage(this).show();
		});

		primaryStage.setResizable(false);
		return primaryStage;
	}// end method

	/** Connect to DB */
	private void connectToDB() {
		// Get database information from the user input
		String driver = cboDriver.getSelectionModel().getSelectedItem();
		String url = cboURL.getSelectionModel().getSelectedItem();
		String username = tfUsername.getText().trim();
		String password = pfPassword.getText().trim();

		// Connection to the database
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, password);
			lblConnectionStatus.setText("Connected to " + url);
			connected = true;
		} catch (java.lang.Exception ex) {
			ex.printStackTrace();
		}
	}

	// Returns connection
	public Connection getConnection() {

		return connection;
	}// end method

}// end class