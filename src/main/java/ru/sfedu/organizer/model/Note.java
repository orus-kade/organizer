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

    /**
     *
     */
    public Note () {
      this.type = NOTE;
  };
  
    /**
     *
     * @param id
     */
    public Note (long id) {
      this.id = id;
      this.type = NOTE;
  };

    /**
     *
     * @return
     */
    public long getObjectId() {
        return objectId;
    }

    /**
     *
     * @param objectId
     */
    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    /**
     *
     * @return
     */
    public String getObjectType() {
        return objectType;
    }

    /**
     *
     * @param objectType
     */
    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    @Override
    public void setId(long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    @Override
    public Types getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Note{" + "id=" + id + ", type=" + type + ", description=" + description + ", objectId=" + objectId + ", objectType=" + objectType + '}';
    }
    
    
}
