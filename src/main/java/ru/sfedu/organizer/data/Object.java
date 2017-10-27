
package ru.sfedu.organizer.data;


public class Object {
    private long id;
    private ObjectTypes type;
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ObjectTypes getType() {
        return type;
    }

    public void setType(ObjectTypes type) {
        this.type = type;
    }
    
}
