package ru.sfedu.organizer.model;


import java.util.*;
import static ru.sfedu.organizer.model.Types.*;
    


/**
 * Class Author
 */
public class Author extends Human {

  //
  // Fields
  //

  private List<Generic> operas;
  private List<Generic> aries;
  private List<Generic> librettos;
  
  //
  // Constructors
  //
  public Author () {
      super(AUTHOR);
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

    public List<Generic> getLibrettos() {
        return librettos;
    }

    public void setLibrettos(List<Generic> librettos) {
        this.librettos = librettos;
    }

 
}
