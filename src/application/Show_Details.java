package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Show_Details {
	@SuppressWarnings("unchecked")
	public static void display(Object o) {
		Stage window =  new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		
		TableView<Reviews_Details> table = new TableView<Reviews_Details>();
		if(o.getClass()==Business.class) {
			Business b = (Business) o;
			window.setTitle("Details of "+b.getB_name());
			

			TableColumn<Reviews_Details, String> user_name = new TableColumn<>("User Name");
			user_name.setCellValueFactory(new PropertyValueFactory<>("user_name"));
			
			TableColumn<Reviews_Details, Integer> funny_votes = new TableColumn<>("Funny Votes");
			funny_votes.setCellValueFactory(new PropertyValueFactory<>("funny_votes"));

			TableColumn<Reviews_Details, Integer> useful_votes = new TableColumn<>("Useful Votes");
			useful_votes.setCellValueFactory(new PropertyValueFactory<>("useful_votes"));
			
			TableColumn<Reviews_Details, Integer> cool_votes = new TableColumn<>("Cool Votes");
			cool_votes.setCellValueFactory(new PropertyValueFactory<>("cool_votes"));
			
			TableColumn<Reviews_Details, Integer> stars = new TableColumn<>("Stars");
			stars.setCellValueFactory(new PropertyValueFactory<>("stars"));
			
			table.getColumns().clear();
			table.getColumns().addAll(user_name,funny_votes,useful_votes,cool_votes,stars);
			
			List<Reviews_Details> list = new ArrayList<>();
			
			
			Connection conn = Main.getConnection();
			
			String str = "select distinct "
					+ "business.b_name, yelp_user.user_name, reviews.funny_vote,reviews.useful_vote,reviews.cool_vote,reviews.stars "
					+ "from reviews "
					+ "inner join business on business.bid = reviews.bid "
					+ "inner join yelp_user on yelp_user.user_id = reviews.user_id "
					+ "where business.b_name = '"+b.getB_name()+"'" ;
			
			try {
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(str);
				while(rs.next()) {
					list.add(new Reviews_Details(rs.getString(1),rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6)));
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			table.getItems().clear();
			table.getItems().addAll(list);
			
		}
		StackPane layout = new StackPane();
		layout.getChildren().add(table);
		
		Scene scene = new Scene(layout,600,600);
		window.setScene(scene);
		window.showAndWait();
	}
}
