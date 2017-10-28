package ru.sfedu.organizer.data;


import java.util.*;
    


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
  public Writer () { };
  
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
