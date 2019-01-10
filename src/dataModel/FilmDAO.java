/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataModel;

import databasejavafx.DatabaseJavaFx;
import java.util.logging.Logger;

/**
 *
 * @author Chris Taliaferro
 */
public class FilmDAO 
{
    
    private String filmName;
    private String filmRating;
    private String filmDescription;
    private Double filmPrice;
    private static final Logger logger = Logger.getLogger(FilmDAO.class.getName());
    
    public FilmDAO()
    {
                
    }
    
    public FilmDAO(String name, String rating, String description, Double price)
    {
        this.filmName = name;
        this.filmRating = rating;
        this.filmDescription = description;
        this.filmPrice = price;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getFilmRating() {
        return filmRating;
    }

    public void setFilmRating(String filmRating) {
        this.filmRating = filmRating;
    }

    public String getFilmDescription() {
        return filmDescription;
    }

    public void setFilmDescription(String filmDescription) {
        this.filmDescription = filmDescription;
    }

    public Double getFilmPrice() {
        return filmPrice;
    }

    public void setFilmPrice(Double filmPrice) {
        this.filmPrice = filmPrice;
    }

    
    
    
}
