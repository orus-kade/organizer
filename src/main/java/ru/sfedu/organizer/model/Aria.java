package ru.sfedu.organizer.model;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
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

  @Element (required = false)
  @CsvBindByPosition (position = 1) 
  private String title;
  
  @Element (required = false)
  @CsvBindByPosition (position = 2) 
  private String text; 
  
  @Element 
  @CsvBindByPosition (position = 3)
  private long operaId;
  
  
  private List<Long> composers = new ArrayList<Long>();
  private List<Long> authors = new ArrayList<Long>();
  private List<Long> singers = new ArrayList<Long>(); 
  private List<Long> notes = new ArrayList<Long>();

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

    public long getOperaId() {
        return operaId;
    }

    public void setOperaId(long operaId) {
        this.operaId = operaId;
    }

    public List<Long> getComposers() {
        return composers;
    }

    public void setComposers(List<Long> composers) {
        this.composers.clear();
        this.composers.addAll(composers);
    }

    public List<Long> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Long> authors) {
        this.authors.clear();
        this.authors.addAll(authors);
    }

    public List<Long> getSingers() {
        return singers;
    }

    public void setSingers(List<Long> singers) {
        this.singers.clear();
        this.singers.addAll(singers);
    }

    public List<Long> getNotes() {
        return notes;
    }

    public void setNotes(List<Long> notes) {
        this.notes.clear();
        this.notes.addAll(notes);
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
        return "Aria{" + "id=" + id + ", type=" + type + ", title=" + title + ", text=" + text + ", operaId=" + operaId + ", composers=" + composers + ", authors=" + authors + ", singers=" + singers + ", notes=" + notes +'}';
    }
    
    
  
}
