package ru.sfedu.organizer.model;


import java.util.*;


/**
 * Class Note
 */
public class Note {

  //
  // Fields
  //

  private long id;
  private String description;
  private Generic object;
  
  //
  // Constructors
  //
  public Note () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Generic getObject() {
        return object;
    }

    public void setObject(Generic object) {
        this.object = object;
    }


}
