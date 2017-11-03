package ru.sfedu.organizer.model;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import java.util.*;
import static ru.sfedu.organizer.model.Types.*;



/**
 * Class Aria
 */
public class Aria extends Generic{

  //
  // Fields
  //
    
  @CsvBindByPosition (position = 0) 
  private long id; 

  @CsvBindByPosition (position = 1) 
  private String title;
  
  @CsvBindByPosition (position = 2) 
  private String text; 
  
 
  private List<Generic> composers;
  private List<Generic> writers;
  private List<Generic> famousSingers;
  
  @CsvCustomBindByPosition (converter = Generic.class, position = 3) 
  private Generic libretto;
  
  //
  // Constructors
  //
  public Aria () { 
      super(ARIA);
  };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //
   
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Generic> getComposers() {
        return composers;
    }

    public void setComposers(List<Generic> composers) {
        this.composers = composers;
    }

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

    public Generic getLibretto() {
        return libretto;
    }

    public void setLibretto(Generic libretto) {
        this.libretto = libretto;
    }

    
    @Override
    public String toString() {
        return "Aria{" +  "id=" + getId() + ", type=" + getType()  + ", title=" + title + ", text=" + text + ", composers=" + composers + ", writers=" + writers + ", famousSingers=" + famousSingers + ", libretto=" + getLibretto() + '}';
    }


}
