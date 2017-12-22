
package ru.sfedu.organizer.model;

/**
 *
 * @author orus-kade
 */
public enum Types {

    /**
     *
     */
    ARIA("ARIA"),

    /**
     *
     */
    COMPOSER("COMPOSER"), 

    /**
     *
     */
    LIBRETTO("LIBRETTO"), 

    /**
     *
     */
    OPERA("OPERA"), 

    /**
     *
     */
    SINGER("SINGER"), 

    /**
     *
     */
    AUTHOR("AUTHOR"),

    /**
     *
     */
    NOTE("NOTE");
    
    private String str;

    private Types(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }   
    
    
}