package ru.sfedu.organizer.model;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import java.util.*;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import static ru.sfedu.organizer.model.Types.*;



/**
 * Class Aria
 */
public class Aria extends Generic{

  @Attribute  
  @CsvBindByPosition (position = 0) 
  private long id; 
  
  private Types type;

  @Element
  @CsvBindByPosition (position = 1) 
  private String title;
  
  @Element
  @CsvBindByPosition (position = 2) 
  private String text; 
  
  @Element
  @CsvCustomBindByPosition (converter = Opera.class, position = 3) 
  private Generic opera;
  
  private List<Generic> composers;
  private List<Generic> authors;
  private List<Generic> singers;
  
  
  

  public Aria () { 
      this.type = ARIA;
  };
  
  public Aria (long id) { 
      this.id = id;
      this.type = ARIA;
  };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Generic> getComposers() {
        return composers;
    }

    public void setComposers(List<Generic> composers) {
        this.composers = composers;
    }

    public List<Generic> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Generic> authors) {
        this.authors = authors;
    }

    public List<Generic> getSingers() {
        return singers;
    }

    public void setSingers(List<Generic> singers) {
        this.singers = singers;
    }

    public Generic getOpera() {
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
