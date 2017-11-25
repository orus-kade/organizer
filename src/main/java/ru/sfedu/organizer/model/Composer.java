package ru.sfedu.organizer.model;


import com.opencsv.bean.CsvBindByPosition;
import java.util.*;

import static ru.sfedu.organizer.model.Types.COMPOSER;

/**
 * Class Composer
 */
public class Composer extends Human {

  //
  // Fields
  //
  @CsvBindByPosition (position = 0)    
  private long id;
  
  private Types type;
  
  @CsvBindByPosition (position = 1)  
  private String name;
  
  @CsvBindByPosition (position = 2)  
  private String biography;
  
  @CsvBindByPosition (position = 3)   
  private String birthDate;
  
  @CsvBindByPosition (position = 4)   
  private String deathDate; 

  private List<Generic> aries;
  
  //
  // Constructors
  //
  public Composer () { 
      this.type = COMPOSER;
  };
  
   public Composer (long id) {
       this.id = id;
      this.type = COMPOSER;
  };
  
    public List<Generic> getAries() {
        return aries;
    }

    public void setAries(List<Generic> aries) {
        this.aries = aries;
    }

    @Override
    public long getId() {
        return id;
    }

  @Override
    public void setId(long id) {
        this.id = id;
    }

  @Override
    public Types getType() {
        return type;
    }

  @Override
    public void setType(Types type) {
        this.type = type;
    }

  @Override
    public String getName() {
        return name;
    }

  @Override
    public void setName(String name) {
        this.name = name;
    }

  @Override
    public String getBiography() {
        return biography;
    }

  @Override
    public void setBiography(String biography) {
        this.biography = biography;
    }

  @Override
    public String getBirthDate() {
        return birthDate;
    }

  @Override
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

  @Override
    public String getDeathDate() {
        return deathDate;
    }

  @Override
    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }
    
    
    
    @Override
    public String toString() {
        return "Composer{"  + "id=" + getId() + " name=" + getName() + ", biography=" + getBiography() + ", birthDate=" + getBirthDate() + ", deathDate=" + getDeathDate() + ", aries=" + aries + '}';
    }  
}
