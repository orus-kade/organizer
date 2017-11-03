
package ru.sfedu.organizer.api;

import com.opencsv.bean.CsvToBeanFilter;
import com.opencsv.bean.MappingStrategy;

/**
 *
 * @author sterie
 */
public class CsvFilter implements CsvToBeanFilter{
    
    private final MappingStrategy strategy;
    private final String id;

    public CsvFilter(MappingStrategy strategy, long id) {
        this.strategy = strategy;
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
