package ru.sfedu.organizer.model;


import java.time.LocalDate;
import java.util.*;


/**
 * Class Human
 */
public abstract class Human  extends Generic{
  
    public abstract String getName(); 

    public abstract void setName(String name);

    public abstract String getBiography();

    public abstract void setBiography(String biography);
    
    public abstract String getBirthDate();

    public abstract void setBirthDate(String birthDate);

    public abstract String getDeathDate();

    public abstract void setDeathDate(String deathDate);

}
