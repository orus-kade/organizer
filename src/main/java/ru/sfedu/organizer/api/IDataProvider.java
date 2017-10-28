
package ru.sfedu.organizer.api;

import ru.sfedu.organizer.data.Entity;


public interface IDataProvider {
    public int saveRecord(Entity obj);
    public int deleteRecord(Entity obj);
    public Entity getRecordById(long id);
    public int initDataSource();
}
