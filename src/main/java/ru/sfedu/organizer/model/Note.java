package ru.sfedu.organizer.model;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;



/**
 * Class Note
 */
public class Note extends Generic{

  //
  // Fields
  //
  @CsvBindByPosition (position = 0)   
  private long id;
  
  @CsvBindByPosition (position = 1)
  private String description;
  
  @CsvCustomBindByPosition(converter = Generic.class, position = 2)
  private Generic object;
  
  //
  // Constructors
  //
  public Note () {
      super(Types.NOTE);
  };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

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
