
package ru.sfedu.organizer.model;


public class Entity {
    private long id;
    private EntityTypes type;

    public Entity(EntityTypes type) {
        this.type = type;
    }    
    
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

    @Override
    public String toString() {
        return "Entity{" + "id=" + id + ", type=" + type + '}';
    }
    
}
