
package ru.sfedu.organizer.api;

import com.opencsv.bean.CsvToBeanFilter;


/**
 *
 * @author sterie
 */
public class CsvFilter implements CsvToBeanFilter{
    
    private final String id;

    public CsvFilter(long id) {
        this.id = Long.toString(id);
    }   
    @Override
    public boolean allowLine(String[] strings) {
 	int index = 0;
 	String value = strings[index];
 	boolean result = id.equals(value);
 	return result;
    }    
}
