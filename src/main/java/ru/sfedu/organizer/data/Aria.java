package ru.sfedu.organizer.data;


import java.util.*;


/**
 * Class Aria
 */
public class Aria extends Entity{

  //
  // Fields
  //

  private String title;
  private String text;
  private List<Integer> composers;
  private List<Integer> writers;
  private List<Integer> famousSingers;
  
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

    public List<Integer> getFamousSingers() {
        return famousSingers;
    }

    public void setFamousSingers(List<Integer> famousSingers) {
        this.famousSingers = famousSingers;
    } 

}
