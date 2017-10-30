package ru.sfedu.organizer.model;


import java.util.*;
import static ru.sfedu.organizer.model.ClassType.*;

/**
 * Class Libretto
 */
public class Libretto extends Generic{

  //
  // Fields
  //

  private Opera opera;
  private List<Generic> writers;
  private List<Generic> famousSingers;
  
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

    

  
}
