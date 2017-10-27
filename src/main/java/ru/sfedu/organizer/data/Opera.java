package ru.sfedu.organizer.data;


import java.util.*;


/**
 * Class Opera
 */
public class Opera extends Object{

  //
  // Fields
  //

  private String title;
  private List<Object> composers;
  private List<Object> writers;
  private String history;
  private List<Object> aries;
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

    public List<Object> getComposers() {
        return composers;
    }

    public void setComposers(List<Object> composers) {
        this.composers = composers;
    }

    public List<Object> getWriters() {
        return writers;
    }

    public void setWriters(List<Object> writers) {
        this.writers = writers;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public List<Object> getAries() {
        return aries;
    }

    public void setAries(List<Object> aries) {
        this.aries = aries;
    }

    public Libretto getLibretto() {
        return libretto;
    }

    public void setLibretto(Libretto libretto) {
        this.libretto = libretto;
    }

    
}
