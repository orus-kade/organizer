
package ru.sfedu.organizer.api;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanFilter;
import com.opencsv.bean.MappingStrategy;


/**
 *
 * @author orus-kade
 */
public class CsvFilter implements CsvToBeanFilter{
    
    private final String id;
    private final MappingStrategy strategy;
    

    /**
     *
     * @param id 
     * @param mappingStrategy
     */
    public CsvFilter(long id, ColumnPositionMappingStrategy mappingStrategy) {
        this.id = Long.toString(id);
        this.strategy = mappingStrategy;
        
    }   

    /**
     *
     * @param strings
     * @return
     */
    @Override
    public boolean allowLine(String[] strings) {
 	int index = strategy.getColumnIndex("ID");
 	String value = strings[index];
 	boolean result = id.equals(value);
 	return result;
    }    
}
