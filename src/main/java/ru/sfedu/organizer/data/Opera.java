
import java.util.*;


/**
 * Class Opera
 */
public class Opera extends Object{

  //
  // Fields
  //

  private String title;
  private List<Composer> composers;
  private List<Writer> writers;
  private String history;
  private List<Aria> aries;
  private Libretto libretto;
  
  //
  // Constructors
  //
  public Opera () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of title
   * @param newVar the new value of title
   */
  private void setTitle (String newVar) {
    title = newVar;
  }

  /**
   * Get the value of title
   * @return the value of title
   */
  private String getTitle () {
    return title;
  }

  /**
   * Set the value of composers
   * @param newVar the new value of composers
   */
  private void setComposers (List<Composer> newVar) {
    composers = newVar;
  }

  /**
   * Get the value of composers
   * @return the value of composers
   */
  private List<Composer> getComposers () {
    return composers;
  }

  /**
   * Set the value of writers
   * @param newVar the new value of writers
   */
  private void setWriters (List<Writer> newVar) {
    writers = newVar;
  }

  /**
   * Get the value of writers
   * @return the value of writers
   */
  private List<Writer> getWriters () {
    return writers;
  }

  /**
   * Set the value of history
   * @param newVar the new value of history
   */
  private void setHistory (String newVar) {
    history = newVar;
  }

  /**
   * Get the value of history
   * @return the value of history
   */
  private String getHistory () {
    return history;
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

  /**
   * Set the value of libretto
   * @param newVar the new value of libretto
   */
  private void setLibretto (Libretto newVar) {
    libretto = newVar;
  }

  /**
   * Get the value of libretto
   * @return the value of libretto
   */
  private Libretto getLibretto () {
    return libretto;
  }

  //
  // Other methods
  //

}
