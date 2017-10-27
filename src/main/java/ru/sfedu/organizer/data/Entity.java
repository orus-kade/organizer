
package ru.sfedu.organizer.data;


public class Entity {
    private int id;
    private EntityTypes type;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EntityTypes getType() {
        return type;
    }

    public void setType(EntityTypes type) {
        this.type = type;
    }
    
}
