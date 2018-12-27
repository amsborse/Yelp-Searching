package application;
	


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;


public class Main extends Application {
	//contains all the main categories
	static ListView<CheckBox> Main_Category = new ListView<>();
	//list view which will contains sub-category with respect to main category
	static ListView<CheckBox> Sub_categories = new ListView<>();	
	//list view which will contain all the attributes with respect to sub-category
	static ListView<CheckBox> Attributes = new ListView<>();
	
	static DatePicker to_Date;
	static DatePicker from_Date;
	static ComboBox<String> choice_for_stars;
	static TextField value_of_stars;
	static ComboBox<String> votes_choice;
	static TextField value_of_votes;
	TextArea Query_Text;
	static ComboBox<String> and_or;
	static Map<CheckBox,List<CheckBox>> map_sub_categories = new HashMap<>();
	static Map<CheckBox,List<CheckBox>> map_attributes = new HashMap<>();
	

	@SuppressWarnings("rawtypes")
	static TableView table;
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			//Layout to contain elements for business search
			VBox Business_Box = new VBox();
			//layout to contain labels for the business search
			HBox label_box = new HBox();
			//layout to contain listview
			HBox list_box = new HBox();

			//main categories checkbox
			CheckBox Active_Life = new CheckBox("Active Life");
			CheckBox Arts_and_Entertainment = new CheckBox("Arts & Entertainment");
			CheckBox Automotive = new CheckBox("Automotive");
			CheckBox Car_Rental = new CheckBox("Car Rental");
			CheckBox Cafes = new CheckBox("Cafes");
			CheckBox Beauty_and_Spas = new CheckBox("Beauty & Spas");
			CheckBox Convenience_Stores = new CheckBox("Convenience Stores");
			CheckBox Dentists = new CheckBox("Dentists");
			CheckBox Doctors = new CheckBox("Doctors");
			CheckBox Drugstores = new CheckBox("Drugstores");
			CheckBox Department_Stores = new CheckBox("Department Stores");
			CheckBox Education = new CheckBox("Education");
			CheckBox Event_Planning_and_Services = new CheckBox("Event Planning & Services");
			CheckBox Flowers_and_Gifts = new CheckBox("Flowers & Gifts");
			CheckBox Food = new CheckBox("Food");
			CheckBox Health_and_Medical = new CheckBox("Health & Medical");
			CheckBox Home_Services = new CheckBox("Home Services");
			CheckBox Home_and_Garden = new CheckBox("Home & Garden");
			CheckBox Hospitals = new CheckBox("Hospitals");
			CheckBox Hotels_and_Travel = new CheckBox("Hotels & Travel");
			CheckBox Hardware_Stores = new CheckBox("Hardware Stores");
			CheckBox Grocery = new CheckBox("Grocery");
			CheckBox Medical_Centers = new CheckBox("Medical Centers");
			CheckBox Nurseries_and_Gardening = new CheckBox("Nurseries & Gardening");
			CheckBox Nightlife = new CheckBox("Nightlife");
			CheckBox Restaurants = new CheckBox("Restaurants");
			CheckBox Shopping = new CheckBox("Shopping");
			CheckBox Transportation = new CheckBox("Transportation");
			//Connection conn = getConnection();
			table = new TableView<>();
			//add all the catgories to the listview
			Main_Category.getItems().addAll(Active_Life,Arts_and_Entertainment,	Automotive,	Car_Rental,Cafes,Beauty_and_Spas,Convenience_Stores,Dentists,Doctors,Drugstores,Department_Stores,Education,Event_Planning_and_Services,Flowers_and_Gifts,Food,	Health_and_Medical,	Home_Services,Home_and_Garden,Hospitals,Hotels_and_Travel,Hardware_Stores,Grocery,Medical_Centers,Nurseries_and_Gardening,Nightlife,Restaurants,Shopping,Transportation);

			for(CheckBox c:Main_Category.getItems()) {
				c.selectedProperty().addListener(new ChangeListener<Boolean>() {
					public void changed(ObservableValue<? extends Boolean> ov,
				            Boolean old_val, Boolean new_val) {
			                if(new_val) {
			                	addSubCategories(c);
			                	addAttributes(c);
			                }
			                else {
			                	removeSubCategories(c);
			                	removeAttributes(c);
			                }
				        }
				});
			}
			table.setStyle("-fx-cursor: hand;");
			table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			    if (newSelection != null) {

			    	Show_Details.display(table.getSelectionModel().selectedItemProperty().getValue());
			    }
			});
			
			Label Main_Category_Label = new Label("Main Category");
			
			Label Sub_Category_Label = new Label("Sub Category");
			
			Label Attribute_Label = new Label("Attribute");
			
			
			

			
			
			HBox.setMargin(Main_Category_Label, new Insets(10,10,10,50));
			HBox.setMargin(Sub_Category_Label, new Insets(10,10,10,120));
			HBox.setMargin(Attribute_Label, new Insets(10,10,10,120));
			
			label_box.getChildren().addAll(Main_Category_Label,Sub_Category_Label,Attribute_Label);
			
			list_box.getChildren().addAll(Main_Category,Sub_categories,Attributes);
			list_box.setStyle("-fx-pref-width:600px");
			
			and_or = new ComboBox<>();
			and_or.getItems().addAll("AND","OR");
			VBox.setMargin(and_or, new Insets(10,10,10,0));
			and_or.setStyle("-fx-pref-width:200px");
			and_or.setPromptText(" AND / OR ");
			//root.setTop(Sub_categories);


			Business_Box.getChildren().addAll(label_box,list_box,and_or);
			
			//Empty Table
			final Label label = new Label("Results");
	        label.setFont(new Font("Arial", 20));
			

	 

	        
	        VBox review_box = new VBox();
			
			Label from_Label = new Label("From");
			from_Date = new DatePicker();
			from_Date.setPromptText("From");


			
			
			HBox from_Box = new HBox();
			from_Box.getChildren().addAll(from_Label,from_Date);
			HBox.setMargin(from_Date,new Insets(10,10,10,10));
			HBox.setMargin(from_Label,new Insets(10,10,10,10));
			
			Label to_Label = new Label("To    ");
			to_Date = new DatePicker();
			to_Date.setPromptText("To");

			
			HBox to_Box = new HBox();
			to_Box.getChildren().addAll(to_Label,to_Date);
			HBox.setMargin(to_Date,new Insets(10,10,10,10));
			HBox.setMargin(to_Label,new Insets(10,10,10,10));

			
			Label stars_label = new Label("Stars: ");
			choice_for_stars = new ComboBox<>();
			choice_for_stars.getItems().addAll("<",">","<=",">=","=");
			choice_for_stars.setPromptText("Relational Operator");
			choice_for_stars.setStyle("-fx-pref-width:150px;");
			

			
			
			
			
			
			
	        

			HBox stars_box = new HBox();
			stars_box.getChildren().addAll(stars_label,choice_for_stars);
			HBox.setMargin(stars_label, new Insets(10,10,10,10));
			HBox.setMargin(choice_for_stars, new Insets(10,10,10,10));
			
			
			value_of_stars = new TextField();
			Label value_of_stars_label = new Label("Value:");
			
			HBox stars_Box = new HBox();
			stars_Box.getChildren().addAll(value_of_stars_label,value_of_stars);
			HBox.setMargin(value_of_stars_label, new Insets(10,10,10,10));
			HBox.setMargin(value_of_stars, new Insets(10,10,10,10));
			
			votes_choice = new ComboBox<>();
			votes_choice.getItems().addAll("<",">","<=",">=","=");
			votes_choice.setPromptText("Relational Operator");
			votes_choice.setStyle("-fx-pref-width:150px;");
			
			Label votes_label = new Label("Votes: ");
			
			HBox votes_box = new HBox();
			votes_box.getChildren().addAll(votes_label,votes_choice);
			HBox.setMargin(votes_choice, new Insets(10,10,10,10));
			HBox.setMargin(votes_label, new Insets(10,10,10,10));
		
			
			value_of_votes = new TextField();
			Label value_of_votes_label = new Label("Value:");
			
			HBox votes_Box = new HBox();
			votes_Box.getChildren().addAll(value_of_votes_label,value_of_votes);
			HBox.setMargin(value_of_votes_label, new Insets(10,10,10,10));
			HBox.setMargin(value_of_votes, new Insets(10,10,10,10));
			//textArea.setStyle("-fx-pref-height:550px;");
			
			Query_Text = new TextArea();
			Query_Text.setPromptText("Query");
			
			review_box.getChildren().addAll(from_Box,to_Box,stars_box,stars_Box,votes_box,votes_Box);
			
	        final VBox result_box = new VBox();
	        result_box.setSpacing(5);
	        result_box.setPadding(new Insets(10, 0, 0, 10));
	        result_box.getChildren().addAll(label, table,Query_Text);
			
			StackPane pane2 = new StackPane();
			TextArea textArea2 = new TextArea();
			pane2.getChildren().add(textArea2);
			
			VBox User_box = new VBox();
			
			HBox Member_since_box = new HBox(); 
			Label Member_since_label = new Label("Memeber Since       ");
			DatePicker Member_since_date = new DatePicker();
			Label Member_since_value_label = new Label("Value : ");
			TextField Member_since_value = new TextField();
			HBox.setMargin(Member_since_label, new Insets(10,10,10,10));
			HBox.setMargin(Member_since_date, new Insets(10,10,10,10));
			HBox.setMargin(Member_since_value_label, new Insets(10,10,10,10));
			HBox.setMargin(Member_since_value, new Insets(10,10,10,10));
			Member_since_box.getChildren().addAll(Member_since_label,Member_since_date,Member_since_value_label,Member_since_value);
			
			HBox Review_count_box = new HBox();
			Label Review_count_label = new Label("Review count          ");
			ComboBox<String> Review_count_choice = new ComboBox<>();
			Review_count_choice.getItems().addAll("<",">","<=",">=","=");
			Review_count_choice.setPromptText("Relational Operator");
			Review_count_choice.setStyle("-fx-pref-width:150px;");
			Label Review_count_value_label = new Label("        Value : ");
			TextField Review_count_value = new TextField();
			HBox.setMargin(Review_count_label, new Insets(10,10,10,10));
			HBox.setMargin(Review_count_choice, new Insets(10,10,10,10));
			HBox.setMargin(Review_count_value_label, new Insets(10,10,10,10));
			HBox.setMargin(Review_count_value, new Insets(10,10,10,10));
			Review_count_box.getChildren().addAll(Review_count_label,Review_count_choice,Review_count_value_label,Review_count_value);

			HBox Number_of_friends_box = new HBox();
			Label Number_of_friends_label = new Label("Number of Friends ");
			ComboBox<String> Number_of_friends_choice = new ComboBox<>();
			Number_of_friends_choice.getItems().addAll("<",">","<=",">=","=");
			Number_of_friends_choice.setPromptText("Relational Operator");
			Number_of_friends_choice.setStyle("-fx-pref-width:150px;");
			Label Number_of_friends_value_label = new Label("        Value : ");
			TextField Number_of_friends_value = new TextField();
			HBox.setMargin(Number_of_friends_label, new Insets(10,10,10,10));
			HBox.setMargin(Number_of_friends_choice, new Insets(10,10,10,10));
			HBox.setMargin(Number_of_friends_value_label, new Insets(10,10,10,10));
			HBox.setMargin(Number_of_friends_value, new Insets(10,10,10,10));
			Number_of_friends_box.getChildren().addAll(Number_of_friends_label,Number_of_friends_choice,Number_of_friends_value_label,Number_of_friends_value);

			HBox Average_stars_box = new HBox();
			Button Execute_Query = new Button("Execute Query");
			Label Average_stars_label = new Label("Average Stars         ");
			ComboBox<String> Average_stars_choice = new ComboBox<>();
			Average_stars_choice.getItems().addAll("<",">","<=",">=","=");
			Average_stars_choice.setPromptText("Relational Operator");
			Average_stars_choice.setStyle("-fx-pref-width:150px;");
			Label Average_stars_value_label = new Label("        Value : ");
			TextField Average_stars_value = new TextField();
			HBox.setMargin(Average_stars_label, new Insets(10,10,10,10));
			HBox.setMargin(Average_stars_choice, new Insets(10,10,10,10));
			HBox.setMargin(Average_stars_value_label, new Insets(10,10,10,10));
			HBox.setMargin(Average_stars_value, new Insets(10,10,10,10));
			HBox.setMargin(Execute_Query, new Insets(10,10,10,350));
			Average_stars_box.getChildren().addAll(Average_stars_label,Average_stars_choice,Average_stars_value_label,Average_stars_value,Execute_Query);

			HBox Number_of_votes_box = new HBox();
			Label Number_of_votes_label = new Label("Number of Votes   ");
			ComboBox<String> Number_of_votes_choice = new ComboBox<>();
			Number_of_votes_choice.getItems().addAll("<",">","<=",">=","=");
			Number_of_votes_choice.setPromptText("Relational Operator");
			Number_of_votes_choice.setStyle("-fx-pref-width:150px;");
			Label Number_of_votes_value_label = new Label("        Value : ");
			TextField Number_of_votes_value = new TextField();
			HBox.setMargin(Number_of_votes_label, new Insets(10,10,10,10));
			HBox.setMargin(Number_of_votes_choice, new Insets(10,10,10,10));
			HBox.setMargin(Number_of_votes_value_label, new Insets(10,10,10,10));
			HBox.setMargin(Number_of_votes_value, new Insets(10,10,10,10));
			Number_of_votes_box.getChildren().addAll(Number_of_votes_label,Number_of_votes_choice,Number_of_votes_value_label,Number_of_votes_value);

			
			User_box.getChildren().addAll(Member_since_box,Review_count_box,Number_of_friends_box,Average_stars_box,Number_of_votes_box);
			
			Execute_Query.setOnAction(e -> Execute_Query());
			
			//User_box.setStyle("-fx-pref-height:500px");
			root.setBottom(User_box);
			root.setRight(result_box);
			root.setCenter(review_box);
			root.setLeft(Business_Box);
			root.setPadding(new Insets(10,10,10,10));
			Scene scene = new Scene(root,1360,700);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			//scene.setCursor(Cursor.HAND);
			primaryStage.setScene(scene);
			primaryStage.setTitle("YELP DATASET SEARCH");
			
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("d.png")));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	



	@SuppressWarnings("unchecked")
	private Object Execute_Query() {
		//from_Date.getUserData();
		String str = "";
		String andorchoice = " AND ";
		boolean flag = false;
		if(and_or.getValue()!=null) {
			andorchoice = " "+and_or.getValue()+" ";
		}
		if(value_of_stars.getText().length()!=0 && choice_for_stars.getValue()!=null) {
			str=str+" "+"business.stars "+ choice_for_stars.getValue()+" "+value_of_stars.getText()+"\n";
			flag = true;
		}
		if(value_of_votes.getText().length()!=0 && votes_choice.getValue()!=null ) {
			if(flag) {
				str = str + andorchoice + "(funny_vote+useful_vote+cool_vote) " + votes_choice.getValue()+" "+value_of_votes.getText()+"\n";
			}
			else {
				str = str + " " + "(funny_vote+useful_vote+cool_vote) " + votes_choice.getValue()+" "+value_of_votes.getText()+"\n";
			}
			flag = true;
		}
		for(CheckBox c:Main_Category.getItems()) {
			if(c.isSelected()) {
				if(flag) {
					str = str + andorchoice + "b_main_category.c_name = '"+c.getText()+"'"+"\n";
				}
				else {
					str = str + " " + "b_main_category.c_name = '"+c.getText()+"'"+"\n";
				}
				flag = true;
			}
		}
		for(CheckBox c:Sub_categories.getItems()) {
			if(c.isSelected()) {
				if(flag) {
					str = str + andorchoice + "b_sub_category.c_name = '"+c.getText()+"'"+"\n";
				}
				else {
					str = str + " " + "b_sub_category.c_name = '"+c.getText()+"'"+"\n";
				}
				flag = true;
			}
		}
		for(CheckBox c:Attributes.getItems()) {
			if(c.isSelected()) {
				if(flag) {
					str = str + andorchoice + "b_attributes.a_name = '"+c.getText()+"'"+"\n";
				}
				else {
					str = str + " " + "b_attributes.a_name = '"+c.getText()+"'"+"\n";
				}
				flag = true;
			}
		}
		str = "select distinct business.b_name,business.city,business.state,business.stars from business \n" + 
				"inner join b_main_category on b_main_category.bid=business.bid\n" + 
				"inner join b_attributes on b_attributes.bid=business.bid\r\n" + 
				"inner join b_sub_category on b_sub_category.bid=business.bid\r\n" + 
				"inner join reviews on reviews.bid=business.bid \n" +
				"where " + str;
	

		TableColumn<Business, String> b_nameColumn = new TableColumn<>("Name");
		b_nameColumn.setCellValueFactory(new PropertyValueFactory<>("b_name"));

		TableColumn<Business, String> cityColumn = new TableColumn<>("City");
		cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));

		TableColumn<Business, String> stateColumn = new TableColumn<>("State");
		stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
		
		TableColumn<Business, Float> starsColumn = new TableColumn<>("Stars");
		starsColumn.setCellValueFactory(new PropertyValueFactory<>("stars"));
		

		table.getColumns().clear();
		table.getColumns().addAll(b_nameColumn,cityColumn,stateColumn,starsColumn);

		List<Business> testlist = new ArrayList<>();
		Connection conn = getConnection();
		Statement st;
		ResultSet rs;
		try {
			
			st = conn.createStatement();
			rs = st.executeQuery(str);
            while(rs.next()){
            	testlist.add(new Business(rs.getString(1),rs.getString(2),rs.getString(3),rs.getFloat(4)));
            }

            table.getItems().clear();
            table.getItems().addAll(testlist);
            

			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		//table.setItems(data);
		//table.getItems().addAll(data);
		//table.getColumns().addAll(idColumn,addressColumn,openColumn,cityColumn,reviewCountColumn,b_nameColumn,neighborhoodsColumn,longitudeColumn,stateColumn,starsColumn,latitudeColumn,b_typeColumn);
		
		Query_Text.setText(str);

		return null;
	}


	public static void main(String[] args) {
		launch(args);
	}
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","akshay","akshay");
			//Statement st = conn.createStatement();
			///st.executeQuery("set define off");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public static List<CheckBox> getSubcategories(CheckBox c) {
		List<CheckBox> newlist = new ArrayList<>();
		Connection conn = getConnection();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select distinct b_sub_category.c_name,b_main_category.c_name from b_sub_category join b_main_category on b_main_category.bid = b_sub_category.bid where b_main_category.c_name = '"+ c.getText() +"'");
			while(rs.next()) {
        		//System.out.println(rs.getString(1));
        		CheckBox test = new CheckBox(rs.getString(1));			        
        		newlist.add(test);
        	}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newlist;
		
	}
	
	public static List<CheckBox> getAttributes(CheckBox c) {
		List<CheckBox> newlist = new ArrayList<>();
		Connection conn = getConnection();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select distinct b_attributes.a_name,b_main_category.c_name from b_attributes join b_main_category on b_main_category.bid = b_attributes.bid where b_main_category.c_name  = '"+ c.getText() +"'");
			while(rs.next()) {
        		//System.out.println(rs.getString(1));
        		CheckBox test = new CheckBox(rs.getString(1));			        
        		newlist.add(test);
        	}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newlist;
		
	}
	public static void addSubCategories(CheckBox c) {
    	map_sub_categories.put(c, getSubcategories(c));
    	for(CheckBox i : map_sub_categories.get(c)) {
    		Sub_categories.getItems().add(i);			                		
    	}
	}	
	public static void removeSubCategories(CheckBox c) {
    	for(CheckBox i : map_sub_categories.get(c)) {
    		Sub_categories.getItems().remove(i);
    	}
	}
	
	public static void addAttributes(CheckBox c) {
		map_attributes.put(c, getAttributes(c));
    	for(CheckBox i : map_attributes.get(c)) {
    		Attributes.getItems().add(i);
    	}
	}
	public static void removeAttributes(CheckBox c) {
		for(CheckBox i : map_attributes.get(c)) {
    		Attributes.getItems().remove(i);	
    	}
	}
}

