package ru.sfedu.organizer.data;


import java.util.*;


/**
 * Class Composer
 */
public class Composer extends Human {

  //
  // Fields
  //

  private List<Object> operas;
  private List<Object> aries;
  
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

    public List<Object> getOperas() {
        return operas;
    }

    public void setOperas(List<Object> operas) {
        this.operas = operas;
    }

    public List<Object> getAries() {
        return aries;
    }

    public void setAries(List<Object> aries) {
        this.aries = aries;
    }



  
}
