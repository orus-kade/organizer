package ru.sfedu.organizer.model;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import java.util.*;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import static ru.sfedu.organizer.model.Types.*;


/**
 * Class Singer
 */
public class Singer extends Human {

  //
  // Fields
  //

  @Element (required = false)
  @CsvBindByName //(position = 7) 
  private String voice;
  
  private List<Long> aries = new ArrayList<Long>();
  private List<Long> notes = new ArrayList<Long>();

    /**
     *
     */
    public Singer () {
      super(SINGER);
  };
  
    /**
     *
     * @param id
     */
    public Singer (long id) {
      super(id, SINGER);
  };
  
    /**
     *
     * @return
     */
    public String getVoice() {
        return voice;
    }

    /**
     *
     * @param voice
     */
    public void setVoice(String voice) {
        this.voice = voice;
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

    @Override
    public String toString() {
        return "Singer{" + "id=" + getId() + ", type=" + getType() + ", name=" + getName() + ", surname=" + getSurname() + ", patronymic=" + getPatronymic() + ", biography=" + getBiography() + ", birthDate=" + new Date(getBirthDate()) + ", deathDate=" + new Date(getDeathDate()) + ", voice=" + voice + ", aries=" + aries + ", notes=" + notes +'}';
    }    
}
