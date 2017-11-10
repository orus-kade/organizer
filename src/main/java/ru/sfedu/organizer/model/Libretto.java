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
    
  @CsvBindByPosition (position = 1)   
  private String text;  
  
  @CsvCustomBindByPosition (converter = Generic.class, position = 1)
  private Generic opera;
  
  private List<Generic> authors;
  
  public Libretto () {
      super(LIBRETTO);
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

    public Generic getOpera() {
        return opera;
    }

    public void setOpera(Generic opera) {
        this.opera = opera;
    }

  
}
