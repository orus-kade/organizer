package ru.sfedu.organizer.model;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import static ru.sfedu.organizer.model.Types.*;



/**
 * Class Note
 */
public class Note extends Generic{

  @Attribute
  @CsvBindByPosition (position = 0)   
  private long id;
  
  private Types type;
  
  @Element (required = false)
  @CsvBindByPosition (position = 1)
  private String description;
  
  @Element (required = false)
  @CsvBindByPosition (position = 2)
  private long objectId;
  
  @Element (required = false)
  @CsvBindByPosition (position = 3)
  private String objectType;  

  public Note () {
      this.type = NOTE;
  };
  
  public Note (long id) {
      this.id = id;
      this.type = NOTE;
  };

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

  
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
