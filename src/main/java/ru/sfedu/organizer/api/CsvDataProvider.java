
package ru.sfedu.organizer.api;

import ru.sfedu.organizer.data.Object;


public class CsvDataProvider implements IDataProvider{

    @Override
    public int saveRecord(Object obj) {
        return 0;
        
    }

    @Override
    public int deleteRecord(Object obj) {
        return 0;
        
    }

    @Override
    public Object getRecordById(long id) {
        return null;
       
    }

    @Override
    public int initDataSource() {
        return 0;
        
    }
    
}
