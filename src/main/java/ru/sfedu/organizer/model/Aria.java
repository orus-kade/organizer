package ru.sfedu.organizer.model;


import java.util.*;
import static ru.sfedu.organizer.model.EntityTypes.*;



/**
 * Class Aria
 */
public class Aria extends Entity{

  //
  // Fields
  //

  private String title;
  private String text;
  private List<Entity> composers;
  private List<Entity> writers;
  private List<Entity> famousSingers;
  
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

    public List<Entity> getComposers() {
        return composers;
    }

    public void setComposers(List<Entity> composers) {
        this.composers = composers;
    }

    public List<Entity> getWriters() {
        return writers;
    }

    public void setWriters(List<Entity> writers) {
        this.writers = writers;
    }

    public List<Entity> getFamousSingers() {
        return famousSingers;
    }

    public void setFamousSingers(List<Entity> famousSingers) {
        this.famousSingers = famousSingers;
    }

    @Override
    public String toString() {
        return "Aria{" +  "id=" + getId() + ", type=" + getType()  + ", title=" + title + ", text=" + text + ", composers=" + composers + ", writers=" + writers + ", famousSingers=" + famousSingers + '}';
    }


}
