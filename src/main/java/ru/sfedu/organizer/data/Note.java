package ru.sfedu.organizer.data;


import java.util.*;


/**
 * Class Note
 */
public class Note {

  //
  // Fields
  //

  private int id;
  private String description;
  private int objectId;
  private Objects objectType;
  
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public Objects getObjectType() {
        return objectType;
    }

    public void setObjectType(Objects objectType) {
        this.objectType = objectType;
    }

   
 

}
