package ru.sfedu.organizer.model;


import com.opencsv.bean.CsvBindByPosition;
import java.util.*;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import static ru.sfedu.organizer.model.Types.COMPOSER;

/**
 * Class Composer
 */
public class Composer extends Human {

  //
  // Fields
  //
  @Attribute  
  @CsvBindByPosition (position = 0)    
  private long id;
  
  private Types type;
  
  @Element (required = false)
  @CsvBindByPosition (position = 1)  
  private String name;
  
  @Element (required = false)
  @CsvBindByPosition (position = 2)  
  private String biography;
  
  @Element (required = false)
  @CsvBindByPosition (position = 3)   
  private String birthDate;
  
  @Element (required = false)
  @CsvBindByPosition (position = 4)   
  private String deathDate; 

  private List<Long> aries;
  
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

    public List<Long> getAries() {
        return aries;
    }

    public void setAries(List<Long> aries) {
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
     
}
