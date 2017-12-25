package ru.sfedu.organizer.model;


import com.opencsv.bean.CsvBindByPosition;
import java.util.*;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import static ru.sfedu.organizer.model.Types.*;
    
/**
 *
 * @author sterie
 */
public class Author extends Human {
  
  
  

  private List<Long> aries = new ArrayList<Long>();
  private List<Long> librettos = new ArrayList<Long>();
  private List<Long> notes = new ArrayList<Long>();
  
    /**
     *
     */
    public Author () {
        super(AUTHOR);
  };
  
    /**
     *
     * @param id
     */
    public Author (long id) {
      super(AUTHOR); 
      setId(id);
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
    public List<Long> getLibrettos() {
        return librettos;
    }

    /**
     *
     * @param librettos
     */
    public void setLibrettos(List<Long> librettos) {
        this.librettos.clear();
        this.librettos.addAll(librettos);
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
        return "Author{" + "id=" + getId() + ", type=" + getType() + ", name=" + getName() + ", surname=" + getSurname() + ", patronymic=" + getPatronymic() + ", biography=" + getBiography() + ", birthDate=" + new Date(getBirthDate()) + ", deathDate=" + new Date(getDeathDate()) + ", aries=" + aries + ", librettos=" + librettos + ", notes=" + notes + '}';
    }
 
}
