package ru.sfedu.organizer.model;

import com.opencsv.bean.CsvBindByName;
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

  @Element (required = false)
  @CsvBindByName 
  private String title;
  
  @Element (required = false)
  @CsvBindByName 
  private String text; 
  
  @Element 
  @CsvBindByName 
  private long operaId;
  
  
  private List<Long> composers = new ArrayList<Long>();
  private List<Long> authors = new ArrayList<Long>();
  private List<Long> singers = new ArrayList<Long>(); 
  private List<Long> notes = new ArrayList<Long>();

    /**
     *
     */
    public Aria () { 
      super(ARIA);       
  };
  
    /**
     *
     * @param id
     */
    public Aria (long id) { 
      super(id, ARIA);     
  };

    /**
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     *
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     *
     * @return
     */
    public long getOperaId() {
        return operaId;
    }

    /**
     *
     * @param operaId
     */
    public void setOperaId(long operaId) {
        this.operaId = operaId;
    }

    /**
     *
     * @return
     */
    public List<Long> getComposers() {
        return composers;
    }

    /**
     *
     * @param composers
     */
    public void setComposers(List<Long> composers) {
        this.composers.clear();
        this.composers.addAll(composers);
    }

    /**
     *
     * @return
     */
    public List<Long> getAuthors() {
        return authors;
    }

    /**
     *
     * @param authors
     */
    public void setAuthors(List<Long> authors) {
        this.authors.clear();
        this.authors.addAll(authors);
    }

    /**
     *
     * @return
     */
    public List<Long> getSingers() {
        return singers;
    }

    /**
     *
     * @param singers
     */
    public void setSingers(List<Long> singers) {
        this.singers.clear();
        this.singers.addAll(singers);
    }

    /**
     *
     * @return
     */
    public List<Long> getNotes() {
        return notes;
    }

    /**
     *
     * @param notes
     */
    public void setNotes(List<Long> notes) {
        this.notes.clear();
        this.notes.addAll(notes);
    }    
    

    @Override
    public String toString() {
        return "Aria{" + "id=" + getId() + ", type=" + getType() + ", title=" + title + ", text=" + text + ", operaId=" + operaId + ", composers=" + composers + ", authors=" + authors + ", singers=" + singers + ", notes=" + notes +'}';
    }
    
    
  
}
