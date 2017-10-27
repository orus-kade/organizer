package ru.sfedu.organizer.data;


import java.time.LocalDate;
import java.util.*;


/**
 * Class Human
 */
public class Human  extends Entity{

  //
  // Fields
  //

  protected String name;
  protected String biography;
  protected LocalDate birthDate;
  protected LocalDate deathDate;
  
  //
  // Constructors
  //
  public Human () { };
  
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(LocalDate deathDate) {
        this.deathDate = deathDate;
    }

  

}
