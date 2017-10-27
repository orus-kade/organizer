package ru.sfedu.organizer.data;


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
  private Object object;
  
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

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }


}
