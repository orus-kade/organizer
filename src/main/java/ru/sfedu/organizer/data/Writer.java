
import java.util.*;
    


/**
 * Class Writer
 */
public class Writer extends Human {

  //
  // Fields
  //

  private List<Opera> operas;
  private List<Aria> aries;
  private List<Libretto> librettos;
  
  //
  // Constructors
  //
  public Writer () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of operas
   * @param newVar the new value of operas
   */
  private void setOperas (List<Opera> newVar) {
    operas = newVar;
  }

  /**
   * Get the value of operas
   * @return the value of operas
   */
  private List<Opera> getOperas () {
    return operas;
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
   * Set the value of librettos
   * @param newVar the new value of librettos
   */
  private void setLibrettos (List<Libretto> newVar) {
    librettos = newVar;
  }

  /**
   * Get the value of librettos
   * @return the value of librettos
   */
  private List<Libretto> getLibrettos () {
    return librettos;
  }

  //
  // Other methods
  //

}
