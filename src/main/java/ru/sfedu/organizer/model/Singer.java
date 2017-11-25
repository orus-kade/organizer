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
  
  private Types type;
  
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
      this.type = SINGER;
  };
  
  public Singer (long id) {
      this.id = id;
      this.type = SINGER;
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

}
