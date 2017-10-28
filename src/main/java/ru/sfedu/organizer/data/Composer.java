package ru.sfedu.organizer.data;


import java.util.*;


/**
 * Class Composer
 */
public class Composer extends Human {

  //
  // Fields
  //

  private List<Entity> operas;
  private List<Entity> aries;
  
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

    public List<Entity> getOperas() {
        return operas;
    }

    public void setOperas(List<Entity> operas) {
        this.operas = operas;
    }

    public List<Entity> getAries() {
        return aries;
    }

    public void setAries(List<Entity> aries) {
        this.aries = aries;
    }



  
}
