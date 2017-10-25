
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
  private List<Aria> aries;
  
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

  /**
   * Set the value of voice
   * @param newVar the new value of voice
   */
  private void setVoice (Voices newVar) {
    voice = newVar;
  }

  /**
   * Get the value of voice
   * @return the value of voice
   */
  private Voices getVoice () {
    return voice;
  }

  /**
   * Set the value of aries
   * @param newVar the new value of aries
   */
  private void setAries (List<Aria> newVar) {
    aries = newVar;
  }

  /**
   * Get the value of aries
   * @return the value of aries
   */
  private List<Aria> getAries () {
    return aries;
  }

  //
  // Other methods
  //

}
