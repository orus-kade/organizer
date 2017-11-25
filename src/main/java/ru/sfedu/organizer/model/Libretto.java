package ru.sfedu.organizer.model;


import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import java.util.*;
import static ru.sfedu.organizer.model.Types.*;

/**
 * Class Libretto
 */
public class Libretto extends Generic{

  @CsvBindByPosition (position = 0)   
  private long id;   
  
  private Types type;
    
  @CsvBindByPosition (position = 1)   
  private String text;  
  
  @CsvCustomBindByPosition (converter = Opera.class, position = 2)
  private Opera opera;
  
  private List<Generic> authors;
  
  public Libretto () {
      this.type = LIBRETTO;
  };
  
  public Libretto (long id) {
      this.id = id;
      this.type = LIBRETTO;
  };

    public List<Generic> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Generic> authors) {
        this.authors = authors;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Opera getOpera() {
        return opera;
    }

    public void setOpera(Opera opera) {
        this.opera = opera;
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
 
}
