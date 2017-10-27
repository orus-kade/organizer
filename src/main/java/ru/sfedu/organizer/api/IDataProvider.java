
package ru.sfedu.organizer.api;

import ru.sfedu.organizer.data.Object;


public interface IDataProvider {
    public int saveRecord(Object obj);
    public int deleteRecord(Object obj);
    public Object getRecordById(long id);
    public int initDataSource();
}
