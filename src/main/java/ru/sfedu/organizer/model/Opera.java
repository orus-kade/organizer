package ru.sfedu.organizer.model;


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

  @Attribute  
  @CsvBindByPosition (position = 0)
  private long id;
  
  private Types type;
  
  @Element (required = false)  
  @CsvBindByPosition (position = 1)  
  private String title;
  
  @Element (required = false)
  @CsvBindByPosition (position = 2)
  private String history;
  
  @Element (required = false)
  @CsvBindByPosition (position = 3)
  private long librettoId;
  
  private List<Long> aries = new ArrayList<Long>();
  private List<Long> notes = new ArrayList<Long>();
  
    /**
     *
     */
    public Opera () {
      this.type = OPERA;
  };
  
    /**
     *
     * @param id
     */
    public Opera (long id) {
      this.id = id;
      this.type = OPERA;
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
        return "Opera{" + "id=" + id + ", type=" + type + ", title=" + title + ", history=" + history + ", librettoId=" + librettoId + ", aries=" + aries + ", notes=" + notes +'}';
    }
    
    
}
