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
  private List<Integer> composers;
  private List<Integer> writers;
  private String history;
  private List<Integer> aries;
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

    public List<Integer> getComposers() {
        return composers;
    }

    public void setComposers(List<Integer> composers) {
        this.composers = composers;
    }

    public List<Integer> getWriters() {
        return writers;
    }

    public void setWriters(List<Integer> writers) {
        this.writers = writers;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public List<Integer> getAries() {
        return aries;
    }

    public void setAries(List<Integer> aries) {
        this.aries = aries;
    }

    public Libretto getLibretto() {
        return libretto;
    }

    public void setLibretto(Libretto libretto) {
        this.libretto = libretto;
    }

  

}
