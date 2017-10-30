package ru.sfedu.organizer.model;


import java.time.LocalDate;
import java.util.*;


/**
 * Class Human
 */
public class Human  extends Generic{

  //
  // Fields
  //

  private String name;
  private String biography;
  private String birthDate;
  private String deathDate;
  
  //
  // Constructors
  //
  public Human (Types type) { 
      super(type);
  };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

  

}
