package ru.sfedu.organizer.data;


import java.util.*;
    


/**
 * Class Writer
 */
public class Writer extends Human {

  //
  // Fields
  //

  private List<Object> operas;
  private List<Object> aries;
  private List<Object> librettos;
  
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

    public List<Object> getLibrettos() {
        return librettos;
    }

    public void setLibrettos(List<Object> librettos) {
        this.librettos = librettos;
    }

 
}
