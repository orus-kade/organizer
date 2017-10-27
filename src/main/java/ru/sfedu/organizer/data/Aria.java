package ru.sfedu.organizer.data;


import java.util.*;


/**
 * Class Aria
 */
public class Aria extends Object{

  //
  // Fields
  //

  private String title;
  private String text;
  private List<Object> composers;
  private List<Object> writers;
  private List<Object> famousSingers;
  
  //
  // Constructors
  //
  public Aria () { };
  
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

    public List<Object> getFamousSingers() {
        return famousSingers;
    }

    public void setFamousSingers(List<Object> famousSingers) {
        this.famousSingers = famousSingers;
    }


}
