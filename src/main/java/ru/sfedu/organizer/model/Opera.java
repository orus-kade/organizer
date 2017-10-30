package ru.sfedu.organizer.model;


import java.util.*;
import static ru.sfedu.organizer.model.ClassType.*;


/**
 * Class Opera
 */
public class Opera extends Generic{

  //
  // Fields
  //

  private String title;
  private List<Generic> composers;
  private List<Generic> writers;
  private String history;
  private List<Generic> aries;
  private Libretto libretto;
  
  //
  // Constructors
  //
  public Opera () {
      super(OPE);
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

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public List<Generic> getAries() {
        return aries;
    }

    public void setAries(List<Generic> aries) {
        this.aries = aries;
    }

    public Libretto getLibretto() {
        return libretto;
    }

    public void setLibretto(Libretto libretto) {
        this.libretto = libretto;
    }

    
}
