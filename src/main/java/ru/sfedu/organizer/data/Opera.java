package ru.sfedu.organizer.data;


import java.util.*;


/**
 * Class Opera
 */
public class Opera extends Entity{

  //
  // Fields
  //

  private String title;
  private List<Entity> composers;
  private List<Entity> writers;
  private String history;
  private List<Entity> aries;
  private Libretto libretto;
  
  //
  // Constructors
  //
  public Opera () { };
  
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

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public List<Entity> getAries() {
        return aries;
    }

    public void setAries(List<Entity> aries) {
        this.aries = aries;
    }

    public Libretto getLibretto() {
        return libretto;
    }

    public void setLibretto(Libretto libretto) {
        this.libretto = libretto;
    }

    
}
