package ru.sfedu.organizer.model;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByName;
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
      super(COMPOSER);
  };
  
    public List<Generic> getAries() {
        return aries;
    }

    public void setAries(List<Generic> aries) {
        this.aries = aries;
    }

    @Override
    public String toString() {
        return "Composer{"  + "id=" + getId() + "name=" + getName() + ", biography=" + getBiography() + ", birthDate=" + getBirthDate() + ", deathDate=" + getDeathDate() + ", aries=" + aries + '}';
    }  
}
