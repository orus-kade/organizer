package ru.sfedu.organizer.model;


import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import java.util.*;
import static ru.sfedu.organizer.model.Types.*;


/**
 * Class Singer
 */
public class Singer extends Human {

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

  @CsvBindByPosition (position = 5) 
  private String voice;
  
  private List<Generic> aries;

  public Singer () {
      super(SINGER);
  };
  
    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public List<Generic> getAries() {
        return aries;
    }

    public void setAries(List<Generic> aries) {
        this.aries = aries;
    }

    @Override
    public String toString() {
        return "Singer{" + "id=" + getId() + ", name=" + getName() + ", biography=" + getBiography() + ", birthDate=" + getBirthDate() + ", deathDate=" + getDeathDate() + ", voice=" + voice + ", aries=" + aries + '}';
    }



  

}
