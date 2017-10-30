package ru.sfedu.organizer.model;


import java.util.*;
import static ru.sfedu.organizer.model.;

/**
 * Class Composer
 */
public class Composer extends Human {

  //
  // Fields
  //

  private List<Generic> operas;
  private List<Generic> aries;
  
  //
  // Constructors
  //
  public Composer () { 
      super(COMPOSER);
  };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

    public List<Generic> getOperas() {
        return operas;
    }

    public void setOperas(List<Generic> operas) {
        this.operas = operas;
    }

    public List<Generic> getAries() {
        return aries;
    }

    public void setAries(List<Generic> aries) {
        this.aries = aries;
    }



  
}
