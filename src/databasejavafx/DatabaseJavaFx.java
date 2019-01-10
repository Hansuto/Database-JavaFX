/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databasejavafx;

import dataModel.FilmDAO; 
import inputoutput.PostgreSQLConnect;
import inputoutput.ConnectionData;
import inputoutput.XmlParser;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.scene.layout.VBox;


/**
 *
 * @author Chris Taliaferro
 */
public class DatabaseJavaFx extends Application 
{
    
    private static final Logger logger = Logger.getLogger(DatabaseJavaFx.class.getName());
    private ObservableList<FilmDAO> data = FXCollections.observableArrayList();
    
     /**
     * @param args 
     */
    public static void main(String[] args) 
    {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) 
    {
        TableView tableView = new TableView();
        tableView.setEditable(false);
        final Label label = new Label("Films");
        label.setFont(new Font("Arial", 14));
        
        TableColumn title = new TableColumn("Title");
        title.setMinWidth(200);
        title.setCellValueFactory(new PropertyValueFactory<FilmDAO,String>("filmName"));
        
        TableColumn description = new TableColumn("Description");
        description.setMinWidth(700);
        description.setCellValueFactory(new PropertyValueFactory<FilmDAO,String>("filmDescription"));
        
        TableColumn rate = new TableColumn("Rental Rate");
        rate.setMinWidth(100);
        rate.setCellValueFactory(new PropertyValueFactory<FilmDAO,Double>("filmPrice"));
        
        TableColumn rating = new TableColumn("Rating");
        rating.setMinWidth(100);
        rating.setCellValueFactory(new PropertyValueFactory<FilmDAO,String>("filmRating"));
        
        //add the columns to the tableView
        tableView.getColumns().addAll(title, description, rate, rating);
        //Jbutton
        final Button fetchData = new Button ("Fetch films from database");
        fetchData.setMinWidth(1123);
        //adding an actionlistener to that button
        fetchData.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) 
            {
                fetchData(tableView);
            }
            
        });
        //new scene
        Scene scene = new Scene(new Group());
        final VBox vbox = new VBox();
        vbox.setPrefHeight(400);
        
        //sets style
        vbox.setStyle("-fx-border-width: 1px; -fx-border-color: #594572; -fx-padding: 10;");
        vbox.getChildren().addAll(label, tableView);
        ((Group) scene.getRoot()).getChildren().addAll(vbox, fetchData);
        
        //set up UI
        stage.setTitle ("Films for Rent");
        //create Scene using layour manager
        stage.setScene(scene);
        //makes UI visible
        stage.show();
    }
    
    private void fetchData (TableView tableView)
    {
        //attempt to connect to database
        try (Connection con = getConnection())
        {
            //populates the UI
            tableView.setItems(fetchFilms(con));
        }
        catch (SQLException | ClassNotFoundException ex)
        {
            logger.log(Level.SEVERE, null, ex);
        }
    }
    
    private Connection getConnection() throws ClassNotFoundException, SQLException
    {
        //tracks progress for user
        logger.info("Getting a database connection");
        //read database properties and pass location of XML file as arguement
        XmlParser xml = new XmlParser("inputOutput/properties.xml");
        ConnectionData data = xml.getConnectionData();
        
        //create connection using data from the XML file
        PostgreSQLConnect connect = new PostgreSQLConnect(data);
        Connection dbConnect = connect.getConnection();
        
        return dbConnect;
    }        

    private ObservableList<FilmDAO> fetchFilms (Connection con) throws SQLException
    {
        logger.info("Fetching films from database");
        ObservableList<FilmDAO> films = FXCollections.observableArrayList();
        
        String select = "SELECT title, rental_rate, rating, description " +
                        "FROM film " +
                        "ORDER BY title;";
        
        logger.info("Select statement " + select);
        
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(select);
        
        while (rs.next())
        {
            //create the DAO
            FilmDAO film = new FilmDAO();
            film.setFilmName(rs.getString("title"));
            film.setFilmRating(rs.getString("rating"));
            film.setFilmDescription(rs.getString("description"));
            film.setFilmPrice(rs.getDouble("rental_rate"));
            
            films.add(film);
        }
        logger.info("Found " + films.size() + " films");
        
        return films;
    }
}
