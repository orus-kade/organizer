package ru.sfedu.organizer.model;


import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import java.util.*;
import static ru.sfedu.organizer.model.Types.*;

/**
 * Class Libretto
 */
public class Libretto extends Generic{

  //
  // Fields
  //
  @CsvBindByPosition (position = 0)   
  private long id;   
    
  @CsvBindByPosition (position = 1)   
  private String text;  
  
  @CsvCustomBindByPosition (converter = Generic.class, position = 1)
  private Generic opera;
  
  private List<Generic> writers;
  private List<Generic> famousSingers;
  
  //
  // Constructors
  //
  public Libretto () {
      super(LIBRETTO);
  };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

    public List<Generic> getWriters() {
        return writers;
    }

    public void setWriters(List<Generic> writers) {
        this.writers = writers;
    }

    public List<Generic> getFamousSingers() {
        return famousSingers;
    }

    public void setFamousSingers(List<Generic> famousSingers) {
        this.famousSingers = famousSingers;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Generic getOpera() {
        return opera;
    }

    public void setOpera(Generic opera) {
        this.opera = opera;
    }

  
}
