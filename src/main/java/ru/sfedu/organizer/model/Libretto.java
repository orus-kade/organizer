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
  
  private List<Long> authors = new ArrayList<Long>();
  private List<Long> notes = new ArrayList<Long>();
  
    /**
     *
     */
    public Libretto () {
      this.type = LIBRETTO;
  };
  
    /**
     *
     * @param id
     */
    public Libretto (long id) {
      this.id = id;
      this.type = LIBRETTO;
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
        return "Libretto{" + "id=" + id + ", type=" + type + ", text=" + text + ", operaId=" + operaId + ", authors=" + authors + ", notes=" + notes +'}';
    }
    
    
}
