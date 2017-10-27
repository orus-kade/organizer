package ru.sfedu.organizer.data;


import java.util.*;
import ru.sfedu.organizer.data.Voices;


/**
 * Class Singer
 */
public class Singer extends Human {

  //
  // Fields
  //

  private Voices voice;
  private List<Object> aries;
  
  //
  // Constructors
  //
  public Singer () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

    public Voices getVoice() {
        return voice;
    }

    public void setVoice(Voices voice) {
        this.voice = voice;
    }

    public List<Object> getAries() {
        return aries;
    }

    public void setAries(List<Object> aries) {
        this.aries = aries;
    }



  

}
