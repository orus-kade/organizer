package ru.sfedu.organizer.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import static ru.sfedu.organizer.model.Types.*;



/**
 * Class Note
 */
public class Note extends Generic{
  
  @Element (required = false)
  @CsvBindByName //(position = 1)
  private String description;
  
  @Element (required = false)
  @CsvBindByName //(position = 2)
  private long objectId;
  
  @Element (required = false)
  @CsvBindByName //(position = 3)
  private String objectType;  

    /**
     *
     */
    public Note () {
        super(NOTE);
    };
  
    /**
     *
     * @param id
     */
    public Note (long id) {
        super(NOTE);
        setId(id);
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

    @Override
    public String toString() {
        return "Note{" + "id=" + getId() + ", type=" + getType() + ", description=" + description + ", objectId=" + objectId + ", objectType=" + objectType + '}';
    }    
}
