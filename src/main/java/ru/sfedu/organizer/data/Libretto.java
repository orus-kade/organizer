
import java.util.*;


/**
 * Class Libretto
 */
public class Libretto extends Object{

  //
  // Fields
  //

  private Opera opera;
  private List<Writer> writers;
  private List<Singer> famousSingers;
  
  //
  // Constructors
  //
  public Libretto () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of opera
   * @param newVar the new value of opera
   */
  private void setOpera (Opera newVar) {
    opera = newVar;
  }

  /**
   * Get the value of opera
   * @return the value of opera
   */
  private Opera getOpera () {
    return opera;
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
   * Set the value of famousSingers
   * @param newVar the new value of famousSingers
   */
  private void setFamousSingers (List<Singer> newVar) {
    famousSingers = newVar;
  }

  /**
   * Get the value of famousSingers
   * @return the value of famousSingers
   */
  private List<Singer> getFamousSingers () {
    return famousSingers;
  }

  //
  // Other methods
  //

}
