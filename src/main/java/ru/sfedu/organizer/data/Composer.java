
import java.util.*;


/**
 * Class Composer
 */
public class Composer extends Human {

  //
  // Fields
  //

  private List<Opera> operas;
  private List<Aria> aries;
  
  //
  // Constructors
  //
  public Composer () { };
  
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

  //
  // Other methods
  //

}
