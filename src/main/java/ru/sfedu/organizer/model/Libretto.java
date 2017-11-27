package ru.sfedu.organizer.model;


import com.opencsv.bean.CsvBindByPosition;
import java.util.*;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import static ru.sfedu.organizer.model.Types.*;

/**
 * Class Libretto
 */
public class Libretto extends Generic{

  @Attribute  
  @CsvBindByPosition (position = 0)   
  private long id;   
  
  private Types type;
  
  @Element (required = false) 
  @CsvBindByPosition (position = 1)   
  private String text;  
  
  @Element (required = false)
  @CsvBindByPosition (position = 2)
  private long operaId;
  
  private List<Long> authors;
  
  public Libretto () {
      this.type = LIBRETTO;
  };
  
  public Libretto (long id) {
      this.id = id;
      this.type = LIBRETTO;
  };

    public long getOperaId() {
        return operaId;
    }

    public void setOperaId(long operaId) {
        this.operaId = operaId;
    }

    public List<Long> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Long> authors) {
        this.authors = authors;
    }

    

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
    public String toString() {
        return "Libretto{" + "id=" + id + ", type=" + type + ", text=" + text + ", operaId=" + operaId + ", authors=" + authors + '}';
    }
    
    
}
