package ru.sfedu.organizer.model;


import java.util.*;
import static ru.sfedu.organizer.model.EntityTypes.*;
    


/**
 * Class Writer
 */
public class Writer extends Human {

  //
  // Fields
  //

  private List<Entity> operas;
  private List<Entity> aries;
  private List<Entity> librettos;
  
  //
  // Constructors
  //
  public Writer () {
      super(WRI);
  };
  
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

    public List<Entity> getLibrettos() {
        return librettos;
    }

    public void setLibrettos(List<Entity> librettos) {
        this.librettos = librettos;
    }

 
}
