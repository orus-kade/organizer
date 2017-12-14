

package ru.sfedu.organizer.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author orus-kade
 */
public class Generator {
    
    public String generateString(int length){
        return generateString(length, length);
    }
    
    
    public String generateString(int begin, int end){
        int length = Math.round((float)Math.random() );
        
    }
    
    public Long generateDateLong() throws ParseException, Exception{
        return generateDateLong("1800-01-01", "2017-01-01");
    }
    
    public Long generateDateLong(String begin, String end) throws ParseException, Exception{
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        long beginLong = ft.parse(begin).getTime();      
        long endLong = ft.parse(end).getTime();
        if (beginLong > endLong) throw new Exception("begin date > end date"); 
        return generateDateLong(begin, end);      
    }
    
    public Long generateDateLong(Date begin, Date end){
        return generateDateLong(begin.getTime(), end.getTime());
        
    }
    
    public Long generateDateLong(long begin, long end){
        if (begin > end) return (long)0;
        long dif = end - begin;
        long date = Math.round(Math.random() * dif + begin);        
        return date;
    }
//    public Date generateDate(){
//        
//    }
    
//    public Long generatLong(){
//        
//    } 

}
