

package ru.sfedu.organizer.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import ru.sfedu.organizer.model.*;

/**
 *
 * @author orus-kade
 */
public class MyGenerator {
    
//    public String generateString(int length){
//        return generateString(length, length);
//    }
    
    
//    public String generateString(int begin, int end){
//        int length = Math.round((float)Math.random() );
//        
//    }
    
    public static Long generateDateLong() throws ParseException, Exception{
        return generateDateLong("1800-01-01", "2017-01-01");
    }
    
    public static Long generateDateLong(String begin, String end) throws ParseException, Exception{
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        long beginLong = ft.parse(begin).getTime();      
        long endLong = ft.parse(end).getTime();
        if (beginLong > endLong) throw new Exception("begin date > end date"); 
        return generateDateLong(begin, end);      
    }
    
    public static Long generateDateLong(Date begin, Date end){
        return generateDateLong(begin.getTime(), end.getTime());
        
    }
    
    public static Long generateDateLong(long begin, long end){
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

    public static Types generateType(){
        int num = 0;
        num = Math.round((float)Math.random() * 5);
        Types type = Types.ARIA;
        switch (num){
            case 0 : return Types.ARIA;
            case 1 : return Types.AUTHOR;
            case 2 : return Types.COMPOSER;
            case 3 : return Types.LIBRETTO;
            case 4 : return Types.OPERA;
            case 5 : return Types.SINGER;
        }
    return type;
    }
}
