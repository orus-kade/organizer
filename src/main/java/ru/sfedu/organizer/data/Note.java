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
  private long objectId;
  private ObjectTypes objectType;
  
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

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public ObjectTypes getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectTypes objectType) {
        this.objectType = objectType;
    }


   
 

}
