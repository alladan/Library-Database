import java.sql.*;
import java.util.ArrayList;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/*
 * Allan Yu
 * Eclipse SE1.8
 */

//The Library Controls GUI
public class LibraryControlPane extends Pane {

	private Connection connection;

	// All required buttons, text fields, text areas, labels.
	private TextField isbn = new TextField();
	private TextField title = new TextField();
	private TextField author = new TextField();
	private TextArea bookIn = new TextArea();
	private TextArea bookOut = new TextArea();
	private TextArea console = new TextArea();
	private TextField userID = new TextField();
	private TextField name = new TextField();
	private TextArea address = new TextArea();
	private TextField uID = new TextField();
	private TextField bID = new TextField();
	private Button connectDB = new Button("Database Setup");
	private Button checkOut = new Button("Check Out");
	private Button returnBook = new Button("Return Book");
	private Button checkUID = new Button("Check All Rentals by UID");
	private Button checkBID = new Button("Check Availability by BID");
	private Button viewDB = new Button("View Database");
	private Button searchUserID = new Button("Search by UserID");
	private Button searchISBN = new Button("Search by ISBN");

	public Stage getStage(ConnectDBPane c) {

		connection = c.getConnection();

		console.appendText("Library Database accessed.\n");
		viewDB.setDisable(true);
		if (c.connectionEstablished()) {

			console.appendText("\nConnection established.");
			viewDB.setDisable(false);
		}
		
		if (c.tableSet()){
			
			console.appendText("\nTables set.\n");
		}
		
		/*
		 * These things here gray out buttons before you should be using them
		 */

		BooleanBinding isbnValid = Bindings.createBooleanBinding(() -> {
			// check textField1.getText() and return true/false as appropriate
			if (isbn.getText().length() > 0)
				return true;
			else
				return false;
		}, isbn.textProperty());

		searchISBN.disableProperty().bind(isbnValid.not());

		BooleanBinding searchUIDValid = Bindings.createBooleanBinding(() -> {
			// check textField2.getText() and return true/false as appropriate
			if (userID.getText().length() > 0)
				return true;
			else
				return false;
		}, userID.textProperty());

		searchUserID.disableProperty().bind(searchUIDValid.not());

		BooleanBinding checkValid = Bindings.createBooleanBinding(() -> {
			// check textField2.getText() and return true/false as appropriate
			if (uID.getText().length() > 0 && bID.getText().length() > 0)
				return true;
			else
				return false;
		}, uID.textProperty(), bID.textProperty());

		checkOut.disableProperty().bind(checkValid.not());
		returnBook.disableProperty().bind(checkValid.not());

		BooleanBinding uIDRental = Bindings.createBooleanBinding(() -> {
			// check textField2.getText() and return true/false as appropriate
			if (uID.getText().length() > 0)
				return true;
			else
				return false;
		}, uID.textProperty());

		checkUID.disableProperty().bind(uIDRental.not());

		BooleanBinding bIDRental = Bindings.createBooleanBinding(() -> {
			// check textField2.getText() and return true/false as appropriate
			if (bID.getText().length() > 0)
				return true;
			else
				return false;
		}, uID.textProperty(), bID.textProperty());

		checkBID.disableProperty().bind(bIDRental.not());

		/*
		 * End Button Grays
		 */

		// Make the stuff you don't type in uneditable
		title.setEditable(false);
		author.setEditable(false);
		bookIn.setEditable(false);
		bookOut.setEditable(false);
		name.setEditable(false);
		address.setEditable(false);

		// The CheckOut pane setup.
		GridPane checkPane = new GridPane();

		checkPane.add(new Label("User ID"), 0, 0);
		checkPane.add(new Label("Book ID"), 0, 1);
		checkPane.add(uID, 1, 0);
		checkPane.add(bID, 1, 1);
		checkPane.add(new Label("Check Out Book"), 0, 2);
		checkPane.add(checkOut, 1, 2);
		checkPane.add(new Label("Return Book"), 0, 3);
		checkPane.add(returnBook, 1, 3);
		checkPane.add(new Label("Check Rentals by User ID"), 0, 4);
		checkPane.add(checkUID, 1, 5);
		checkPane.add(new Label("Check Rental by Book ID"), 0, 6);
		checkPane.add(checkBID, 1, 7);

		GridPane userPane = new GridPane();
		userPane.add(userID, 1, 0);
		userPane.add(name, 1, 1);
		userPane.add(address, 1, 2);
		userPane.add(new Label("User ID"), 0, 0);
		userPane.add(new Label("Name"), 0, 1);
		userPane.add(new Label("Address "), 0, 2);

		HBox hBoxUser = new HBox();
		hBoxUser.getChildren().addAll(viewDB, searchUserID);
		hBoxUser.setAlignment(Pos.CENTER_RIGHT);

		if (c.tableSet())
			viewDB.setDisable(false);

		else
			viewDB.setDisable(true);
		
		viewDB.setOnAction(e -> {

			try {
				// Get a new statement for the current connection
				Statement statement = c.getConnection().createStatement();

				// Execute a SELECT SQL command
				ResultSet resultSet = statement.executeQuery("select*from books");

				// Find the number of columns in the result set
				int columnCount = resultSet.getMetaData().getColumnCount();
				String row = "\n\n";

				// Display column names
				for (int i = 1; i <= columnCount; i++) {
					row += resultSet.getMetaData().getColumnName(i) + "\t";
				}

				console.appendText(row + '\n');

				while (resultSet.next()) {
					// Reset row to empty
					row = "";

					for (int i = 1; i <= columnCount; i++) {
						// A non-String column is converted to a string
						row += resultSet.getString(i) + "\t";
					}

					console.appendText(row + '\n');
				}

				console.appendText("\n");

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});

		VBox vBoxUser = new VBox(5);
		vBoxUser.getChildren().addAll(userPane, hBoxUser);

		address.setPrefRowCount(3);
		address.setPrefColumnCount(15);

		// Create UI for connecting to the database
		GridPane bookPane = new GridPane();
		bookPane.add(isbn, 1, 0);
		bookPane.add(title, 1, 1);
		bookPane.add(author, 1, 2);
		bookPane.add(bookIn, 1, 3);
		bookPane.add(bookOut, 1, 4);
		bookPane.add(new Label("ISBN"), 0, 0);
		bookPane.add(new Label("Title"), 0, 1);
		bookPane.add(new Label("Author"), 0, 2);
		bookPane.add(new Label("BIDs Avail "), 0, 3);
		bookPane.add(new Label("BIDs Out"), 0, 4);

		bookIn.setPrefRowCount(3);
		bookIn.setPrefColumnCount(15);
		bookOut.setPrefRowCount(3);
		bookOut.setPrefColumnCount(15);

		HBox hBoxConnection = new HBox();
		hBoxConnection.getChildren().addAll(searchISBN);
		hBoxConnection.setAlignment(Pos.CENTER_RIGHT);

		VBox vBoxBook = new VBox(5);
		vBoxBook.getChildren().addAll(bookPane, hBoxConnection);

		HBox top = new HBox(575);
		Text t = new Text("Library Control");
		t.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		top.getChildren().addAll(t, connectDB);
		top.setAlignment(Pos.CENTER);

		HBox hBoxConnectionCommand = new HBox(10);
		hBoxConnectionCommand.getChildren().addAll(vBoxBook, vBoxUser, checkPane);
		hBoxConnectionCommand.setAlignment(Pos.CENTER);
		hBoxConnectionCommand.setStyle("-fx-border-color: black;");
		BorderPane borderPaneExecutionResult = new BorderPane();
		borderPaneExecutionResult.setCenter(console);

		BorderPane borderPane = new BorderPane();
		borderPane.setTop(top);
		borderPane.setCenter(hBoxConnectionCommand);
		borderPane.setBottom(borderPaneExecutionResult);

		Stage primaryStage = new Stage();
		// Create a scene and place it in the stage
		Scene scene = new Scene(borderPane, 800, 410);
		primaryStage.setTitle("Library Software"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage

		// Allows seamless movement to the database setup screen
		connectDB.setOnAction(e -> {

			primaryStage.close();
			c.getStage(this).show();
		});// end lambda

		// Return a book button
		returnBook.setOnAction(e -> {

			int uID = Integer.parseInt(this.uID.getText().trim());
			int bID = Integer.parseInt(this.bID.getText().trim());

			ResultSet rs = executeSQL("select*from books");

			try {
				// Lists of bookID, and its checkout status
				ArrayList<Integer> bookID = new ArrayList<>();
				ArrayList<Integer> checked = new ArrayList<>();

				while (rs.next()) {

					// Populate bookID list
					bookID.add(rs.getInt(1));
					// Populate checked out list
					checked.add(rs.getInt(5));

				} // end while

				// If the book's ID matches the checked out user ID
				if (checked.get(bID - 1) == (uID)) {

					try {

						console.appendText("\nUID " + uID + " has returned BID " + bID + ".");
						executeSQL("update books\n" + "set checkout=null\n" + "where bookID=" + bID + ";");

					} catch (Exception ex) {
						ex.printStackTrace();
					} // end try-catch
				} // end if

				else {

					console.appendText("\nBID " + bID + " can't be returned by UID " + uID + ".");
				}

			} catch (SQLException ex) {
				ex.printStackTrace();
			} // end try-catch

		});// end lambda

		// Check out book button, by user ID
		checkOut.setOnAction(e -> {

			int uID = Integer.parseInt(this.uID.getText().trim());
			int bID = Integer.parseInt(this.bID.getText().trim());

			ResultSet rs = executeSQL("select*from books");

			try {
				// BOOKID list, Checked out list
				ArrayList<Integer> bookID = new ArrayList<>();
				ArrayList<Integer> checked = new ArrayList<>();

				// populate the bookIDs and checkedIDs lists
				while (rs.next()) {

					bookID.add(rs.getInt(1));
					checked.add(rs.getInt(5));

				} // end while

				// If the book matches an unchecked out ID
				if (checked.get(bID - 1) == 0) {

					console.appendText("\nUID " + uID + " has checked out BID " + bID + ".");

					try {

						executeSQL("update books\n" + "set checkout=" + uID + "\n" + "where bookID=" + bID + ";");

					} catch (Exception ex) {
						ex.printStackTrace();
					} // end try-catch
				} // end if

				else
					console.appendText(
							"\nBID " + bID + " has already been checked out by UID " + checked.get(bID - 1) + ".");

			} catch (SQLException ex) {
				ex.printStackTrace();
			} // end try-catch

		});// end lambda

		// Search info by User ID (2nd panel)
		searchUserID.setOnAction(e -> {

			ResultSet rs = executeSQL("select*from users");

			try {

				while (rs.next()) {

					// Match the user ID to the table's user ID
					if (rs.getInt(1) == Integer.parseInt(userID.getText().trim())) {

						// Populate the name/address
						this.name.setText(rs.getString(2));
						this.address.setText(rs.getString(3));

						break;
					} // end if

				} // end while

			} catch (SQLException ex) {
				ex.printStackTrace();
			} // end try-catch
		});// end lambda

		// Check what UID has taken out BID button
		checkBID.setOnAction(e -> {

			ResultSet rs = executeSQL("select*from books");
			int no = Integer.parseInt(bID.getText().trim());

			console.appendText("\n" + "BID " + no + " has been checked out by ");

			try {

				while (rs.next()) {

					// Check to see that the book matches
					if (rs.getInt(1) == no) {

						//
						if (rs.getInt(5) > 0)
							console.appendText("UID " + rs.getInt(5) + ".");
						else
							console.appendText("none.");

						break;
					}
				}

			} catch (SQLException ex) {
				ex.printStackTrace();
			}

		});

		// Check User ID checked out which books button
		checkUID.setOnAction(e -> {

			ResultSet rs = executeSQL("select*from books");
			int no = Integer.parseInt(uID.getText().trim());

			console.appendText("\n" + "UID " + no + " has checked out:");

			try {

				// Lists representing table data
				ArrayList<Integer> bookID = new ArrayList<>();
				ArrayList<String> isbn = new ArrayList<>();
				ArrayList<String> book = new ArrayList<>();
				ArrayList<String> author = new ArrayList<>();
				ArrayList<Integer> checked = new ArrayList<>();

				// Find the number of columns in the result set
				int columnCount = rs.getMetaData().getColumnCount();

				while (rs.next()) {

					// populate lists
					for (int i = 1; i <= columnCount; i++) {

						switch (i) {

						case 1:
							bookID.add(rs.getInt(i));
							break;
						case 2:
							isbn.add(rs.getString(i));
							break;
						case 3:
							book.add(rs.getString(i));
							break;
						case 4:
							author.add(rs.getString(i));
							break;
						case 5:
							checked.add(rs.getInt(i));
						}// end switch
					} // end for
				} // end while

				// Check to see what books have been checked out by user ID
				for (int i = 0; i < bookID.size(); i++) {

					if (checked.get(i).equals(no)) {

						console.appendText("\nBID " + bookID.get(i) + "\t" + isbn.get(i) + "\t" + book.get(i) + "\t"
								+ author.get(i));
					} // end if
				} // end for

			} catch (SQLException ex) {
				ex.printStackTrace();
			} // end try-catch

		});// end lambda

		// Search by ISBN Button
		searchISBN.setOnAction(e -> {

			// select the books table
			ResultSet rs = executeSQL("select*from books");
			String no = isbn.getText().trim();// the ISBN #

			bookIn.setText("");// reset textfields
			bookOut.setText("");

			try {
				// Lists to represent the table
				ArrayList<Integer> bookID = new ArrayList<>();
				ArrayList<String> isbn = new ArrayList<>();
				ArrayList<String> book = new ArrayList<>();
				ArrayList<String> author = new ArrayList<>();
				ArrayList<Integer> checked = new ArrayList<>();

				// Find the number of columns in the result set
				int columnCount = rs.getMetaData().getColumnCount();

				while (rs.next()) {

					// Iterate through, populate the lists
					for (int i = 1; i <= columnCount; i++) {

						switch (i) {// switch 1-5 for tables

						case 1:
							bookID.add(rs.getInt(i));
							break;
						case 2:
							isbn.add(rs.getString(i));
							break;
						case 3:
							book.add(rs.getString(i));
							break;
						case 4:
							author.add(rs.getString(i));
							break;
						case 5:
							checked.add(rs.getInt(i));

						}// end switch
					} // end for
				} // end while

				// Loop through the arrays again
				for (int i = 0; i < bookID.size(); i++) {

					// Check to see that the ISBN is equal to the one in the
					// text field
					if (isbn.get(i).equals(no)) {

						// Set the title, author, and books in
						title.setText(book.get(i));
						this.author.setText(author.get(i));
						bookIn.appendText(bookID.get(i).toString() + ", ");

						// If null, don't set the books out
						if (checked.get(i) != 0)
							bookOut.appendText(checked.get(i).toString() + ", ");
					} // end if
				} // end for

			} catch (SQLException ex) {
				ex.printStackTrace();
			} // end try-catch

		});// end lambda

		primaryStage.setResizable(false);

		return primaryStage;
	}

	/** Execute SQL commands */
	public ResultSet executeSQL(String sql) {

		String sqlCommands = sql.trim();
		String[] commands = sqlCommands.replace('\n', ' ').split(";");

		for (String aCommand : commands) {
			if (aCommand.trim().toUpperCase().startsWith("SELECT")) {
				return processSQLSelect(aCommand);
			} else {
				processSQLNonSelect(aCommand);
			}
		}

		return null;
	}

	/** Execute SQL SELECT commands */
	private ResultSet processSQLSelect(String sqlCommand) {

		ResultSet resultSet = null;

		try {
			// Get a new statement for the current connection
			Statement statement = connection.createStatement();

			// Execute a SELECT SQL command
			resultSet = statement.executeQuery(sqlCommand);

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return resultSet;
	}

	/** Execute SQL DDL, and modification commands */
	private void processSQLNonSelect(String sqlCommand) {
		try {
			// Get a new statement for the current connection
			Statement statement = connection.createStatement();

			// Execute a non-SELECT SQL command
			statement.executeUpdate(sqlCommand);

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

}// end class