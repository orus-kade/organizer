
package ru.sfedu.organizer.data;


public class Entity {
    private long id;
    private EntityTypes type;
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EntityTypes getType() {
        return type;
    }

    public void setType(EntityTypes type) {
        this.type = type;
    }
    
}
