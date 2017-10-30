package ru.sfedu.organizer.model;


import java.util.*;
import static ru.sfedu.organizer.model.ClassType.*;



/**
 * Class Aria
 */
public class Aria extends Generic{

  //
  // Fields
  //

  private String title;
  private String text;
  private List<Generic> composers;
  private List<Generic> writers;
  private List<Generic> famousSingers;
  
  //
  // Constructors
  //
  public Aria () { 
      super(ARI);
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

    @Override
    public String toString() {
        return "Aria{" +  "id=" + getId() + ", type=" + getType()  + ", title=" + title + ", text=" + text + ", composers=" + composers + ", writers=" + writers + ", famousSingers=" + famousSingers + '}';
    }


}
