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
  private List<Integer> writers;
  private List<Integer> famousSingers;
  
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

    public List<Integer> getWriters() {
        return writers;
    }

    public void setWriters(List<Integer> writers) {
        this.writers = writers;
    }

    public List<Integer> getFamousSingers() {
        return famousSingers;
    }

    public void setFamousSingers(List<Integer> famousSingers) {
        this.famousSingers = famousSingers;
    }

  
}
