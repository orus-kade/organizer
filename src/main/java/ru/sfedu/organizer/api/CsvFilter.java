
package ru.sfedu.organizer.api;

import com.opencsv.bean.CsvToBeanFilter;


/**
 *
 * @author orus-kade
 */
public class CsvFilter implements CsvToBeanFilter{
    
    private final String id;

    /**
     *
     * @param id
     */
    public CsvFilter(long id) {
        this.id = Long.toString(id);
    }   

    /**
     *
     * @param strings
     * @return
     */
    @Override
    public boolean allowLine(String[] strings) {
 	int index = 0;
 	String value = strings[index];
 	boolean result = id.equals(value);
 	return result;
    }    
}
