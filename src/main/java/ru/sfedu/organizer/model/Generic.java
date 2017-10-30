
package ru.sfedu.organizer.model;


public class Generic {
    private long id;
    private Types type;

    public Generic(Types type) {
        this.type = type;
    }    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Types getType() {
        return type;
    }

    public void setType(Types type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Entity{" + "id=" + id + ", type=" + type + '}';
    }
    
}
