package ru.sfedu.organizer.model;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import static ru.sfedu.organizer.model.Types.*;



/**
 * Class Note
 */
public class Note extends Generic{


  @CsvBindByPosition (position = 0)   
  private long id;
  
  private Types type;
  
  @CsvBindByPosition (position = 1)
  private String description;
  
  @CsvCustomBindByPosition(converter = Generic.class, position = 2)
  private Generic object;
  

  public Note () {
      this.type = NOTE;
  };
  
  public Note (long id) {
      this.id = id;
      this.type = NOTE;
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
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public Types getType() {
        return type;
    }

    @Override
    public void setType(Types type) {
        this.type = type;
    }
    
    
    @Override
    public String toString() {
        return "Note{" + "id=" +  getId() + ", description=" + description + ", object=" + object + '}';
    }


}
