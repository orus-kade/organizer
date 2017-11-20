package ru.sfedu.organizer.model;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;



/**
 * Class Note
 */
public class Note extends Generic{


  @CsvBindByPosition (position = 0)   
  private long id;
  
  @CsvBindByPosition (position = 1)
  private String description;
  
  @CsvCustomBindByPosition(converter = Generic.class, position = 2)
  private Generic object;
  

  public Note () {
      super(Types.NOTE);
  };
  
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

    @Override
    public String toString() {
        return "Note{" + "id=" +  getId() + ", description=" + description + ", object=" + object + '}';
    }


}
