package ru.sfedu.organizer.model;


import java.util.*;
import ru.sfedu.organizer.model.Voices;
import static ru.sfedu.organizer.model.EntityTypes.*;


/**
 * Class Singer
 */
public class Singer extends Human {

  //
  // Fields
  //

  private Voices voice;
  private List<Entity> aries;
  
  //
  // Constructors
  //
  public Singer () {
      super(SIN);
  };
  
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

    public List<Entity> getAries() {
        return aries;
    }

    public void setAries(List<Entity> aries) {
        this.aries = aries;
    }



  

}
