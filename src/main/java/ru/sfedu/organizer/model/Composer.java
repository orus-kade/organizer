package ru.sfedu.organizer.model;


import java.util.*;

import static ru.sfedu.organizer.model.Types.COMPOSER;

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

    @Override
    public String toString() {
        return "Composer{" + "name=" + getName() + ", biography=" + getBiography() + ", birthDate=" + getBirthDate() + ", deathDate=" + getDeathDate() + ", operas=" + operas + ", aries=" + aries + '}';
    }  
}
