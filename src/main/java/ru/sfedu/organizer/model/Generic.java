
package ru.sfedu.organizer.model;


public class Generic {
    private long id;
    private ClassType type;

    public Generic(ClassType type) {
        this.type = type;
    }    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ClassType getType() {
        return type;
    }

    public void setType(ClassType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Entity{" + "id=" + id + ", type=" + type + '}';
    }
    
}
