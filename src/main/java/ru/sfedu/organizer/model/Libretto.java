package ru.sfedu.organizer.model;


import java.util.*;
import static ru.sfedu.organizer.model.EntityTypes.*;

/**
 * Class Libretto
 */
public class Libretto extends Entity{

  //
  // Fields
  //

  private Opera opera;
  private List<Entity> writers;
  private List<Entity> famousSingers;
  
  //
  // Constructors
  //
  public Libretto () {
      super(LIB);
  };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

    public Opera getOpera() {
        return opera;
    }

    public void setOpera(Opera opera) {
        this.opera = opera;
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

    

  
}
