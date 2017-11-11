package ru.sfedu.organizer.model;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import java.util.*;
import static ru.sfedu.organizer.model.Types.*;



/**
 * Class Aria
 */
public class Aria extends Generic{

    
  @CsvBindByPosition (position = 0) 
  private long id; 

  @CsvBindByPosition (position = 1) 
  private String title;
  
  @CsvBindByPosition (position = 2) 
  private String text; 
  
  @CsvCustomBindByPosition (converter = Generic.class, position = 3) 
  private Generic opera;
  
  private List<Generic> composers;
  private List<Generic> authors;
  private List<Generic> singers;
  
  
  

  public Aria () { 
      super(ARIA);
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

    public void setOpera(Generic opera) {
        this.opera = opera;
    }

    
    @Override
    public String toString() {
        return "Aria{" +  "id=" + getId() + ", type=" + getType()  + ", title=" + title + ", text=" + text + ", composers=" + composers + ", writers=" + authors + ", famousSingers=" + singers + ", Opera=" + opera + '}';
    }
  
}
