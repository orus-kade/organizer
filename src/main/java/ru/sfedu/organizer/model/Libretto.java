package ru.sfedu.organizer.model;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import java.util.*;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import static ru.sfedu.organizer.model.Types.*;

/**
 * Class Libretto
 */
public class Libretto extends Generic{
  
  @Element (required = false) 
  @CsvBindByName 
  private String text;  
  
  @Element (required = false)
  @CsvBindByName 
  private long operaId;
  
  private List<Long> authors = new ArrayList<Long>();
  private List<Long> notes = new ArrayList<Long>();
  
    /**
     *
     */
    public Libretto () {
      super(LIBRETTO);
  };
  
    /**
     *
     * @param id
     */
    public Libretto (long id) {
        super(id, LIBRETTO);
  };

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

    @Override
    public String toString() {
        return "Libretto{" + "id=" + getId() + ", type=" + getType() + ", text=" + text + ", operaId=" + operaId + ", authors=" + authors + ", notes=" + notes +'}';
    }
    
    
}
