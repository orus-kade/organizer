package ru.sfedu.organizer.model;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import java.util.*;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import static ru.sfedu.organizer.model.Types.*;

/**
 *
 * @author sterie
 */
public class Opera extends Generic{

  @Element (required = false)  
  @CsvBindByName 
  private String title;
  
  @Element (required = false)
  @CsvBindByName
  private String history;
  
  @Element (required = false)
  @CsvBindByName 
  private long librettoId;
  
  private List<Long> aries = new ArrayList<Long>();
  private List<Long> notes = new ArrayList<Long>();
  
    /**
     *
     */
    public Opera () {
      super(OPERA);
  };
  
    /**
     *
     * @param id
     */
    public Opera (long id) {
        super(id, OPERA);
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
    public String getHistory() {
        return history;
    }

    /**
     *
     * @param history
     */
    public void setHistory(String history) {
        this.history = history;
    }

    /**
     *
     * @return
     */
    public long getLibrettoId() {
        return librettoId;
    }

    /**
     *
     * @param librettoId
     */
    public void setLibrettoId(long librettoId) {
        this.librettoId = librettoId;
    }

    /**
     *
     * @return
     */
    public List<Long> getAries() {
        return aries;
    }

    /**
     *
     * @param aries
     */
    public void setAries(List<Long> aries) {
        this.aries.clear();
        this.aries.addAll(aries);
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

    @Override
    public String toString() {
        return "Opera{" + "id=" + getId() + ", type=" + getType() + ", title=" + title + ", history=" + history + ", librettoId=" + librettoId + ", aries=" + aries + ", notes=" + notes +'}';
    }
}
