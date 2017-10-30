package ru.sfedu.organizer.model;


import java.util.*;
import ru.sfedu.organizer.model.Voices;
import static ru.sfedu.organizer.model.Types.*;


/**
 * Class Singer
 */
public class Singer extends Human {

  //
  // Fields
  //

  private long voice;
  private List<Generic> aries;
  
  //
  // Constructors
  //
  public Singer () {
      super(SINGER);
  };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

    public long getVoice() {
        return voice;
    }

    public void setVoice(long voice) {
        this.voice = voice;
    }

    public List<Generic> getAries() {
        return aries;
    }

    public void setAries(List<Generic> aries) {
        this.aries = aries;
    }



  

}
