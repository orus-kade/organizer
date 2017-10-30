package ru.sfedu.organizer.model;

/**
 *
 * @author sterie
 */
public enum EntityTypes {
    ARI("Aria"), 
    COM("Composer"), 
    LIB("Libretto"), 
    OPE("Opera"), 
    SIN("Singer"), 
    WRI("Writer");
    
    private final String type;

    private EntityTypes(String type) {
        this.type = type;
    }    
    
    public String getType() {
        return type;
    }
    
    
    
}
