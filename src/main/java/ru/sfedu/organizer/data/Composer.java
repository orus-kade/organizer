package ru.sfedu.organizer.data;


import java.util.*;


/**
 * Class Composer
 */
public class Composer extends Human {

  //
  // Fields
  //

  private List<Integer> operas;
  private List<Integer> aries;
  
  //
  // Constructors
  //
  public Composer () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

    public List<Integer> getOperas() {
        return operas;
    }

    public void setOperas(List<Integer> operas) {
        this.operas = operas;
    }

    public List<Integer> getAries() {
        return aries;
    }

    public void setAries(List<Integer> aries) {
        this.aries = aries;
    }

  
}
