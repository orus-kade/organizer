package ru.sfedu.organizer.data;


import java.util.*;


/**
 * Class Libretto
 */
public class Libretto extends Object{

  //
  // Fields
  //

  private Opera opera;
  private List<Object> writers;
  private List<Object> famousSingers;
  
  //
  // Constructors
  //
  public Libretto () { };
  
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

    public List<Object> getWriters() {
        return writers;
    }

    public void setWriters(List<Object> writers) {
        this.writers = writers;
    }

    public List<Object> getFamousSingers() {
        return famousSingers;
    }

    public void setFamousSingers(List<Object> famousSingers) {
        this.famousSingers = famousSingers;
    }

    

  
}
