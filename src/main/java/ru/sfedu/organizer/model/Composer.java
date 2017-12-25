package ru.sfedu.organizer.model;


import com.opencsv.bean.CsvBindByPosition;
import java.util.*;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import static ru.sfedu.organizer.model.Types.COMPOSER;

/**
 * Class Composer
 */
public class Composer extends Human {

  //
  // Fields
  // 

  private List<Long> aries = new ArrayList<Long>();
  private List<Long> notes = new ArrayList<Long>();
  
  //
  // Constructors
  //

    /**
     *
     */
  public Composer () { 
      super(COMPOSER);
  };
  
    /**
     *
     * @param id
     */
    public Composer (long id) {
      super(id, COMPOSER);
  };

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
        return "Composer{" + "id=" + getId() + ", type=" + getType() + ", name=" + getName() + ", surname=" + getSurname() + ", patronymic=" + getPatronymic() + ", biography=" + getBiography() + ", birthDate=" + new Date(getBirthDate()) + ", deathDate=" + new Date(getDeathDate()) + ", aries=" + aries + ", notes=" + notes +'}';
    }    
}
