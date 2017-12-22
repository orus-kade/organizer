

package ru.sfedu.organizer.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import ru.sfedu.organizer.api.IDataProvider;
import ru.sfedu.organizer.model.*;

/**
 *
 * @author orus-kade
 */
public class MyGenerator {
    
//    public String generateString(int length){
//        return generateString(length, length);
//    }
//    
//    
//    public String generateString(int beginLength, int endLength){
//        int length = Math.round((float)Math.random() );
//        
//    }
    
//    public static Long generateDateLong() throws ParseException, Exception{
//        return generateDateLong("1800-01-01", "2017-01-01");
//    }
//    
//    public static Long generateDateLong(String begin, String end) throws ParseException, Exception{
//        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
//        long beginLong = ft.parse(begin).getTime();      
//        long endLong = ft.parse(end).getTime();
//        if (beginLong > endLong) throw new Exception("begin date > end date"); 
//        return generateDateLong(begin, end);      
//    }
//    
//    public static Long generateDateLong(Date begin, Date end){
//        return generateDateLong(begin.getTime(), end.getTime());        
//    }
//    
//    public static Long generateDateLong(long begin, long end){
//        if (begin > end) return (long)0;
//        long dif = end - begin;
//        long date = Math.round(Math.random() * dif + begin);        
//        return date;
//    }

    /**
     *
     * @return
     */

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
    
    /**
     *
     * @return
     */
    public static long generateId(){
        return (new Date()).getTime();
    }
    
    /**
     *
     * @param provider
     * @param id
     * @return
     */
    public static Note generateNote(IDataProvider provider, long id){
        Note note = generateNote(provider);
        note.setId(id);
        return note;
    }
    
    /**
     *
     * @param provider
     * @return
     */
    public static Note generateNote(IDataProvider provider){
        Note note = new Note();
        Types type;
        while(true){
            type = generateType();            
            Result result = null;
            switch (type){
                case ARIA : result = provider.getAllRecords(new Aria());
                    break;
                case COMPOSER : result = provider.getAllRecords(new Composer());
                    break;
                case LIBRETTO : result = provider.getAllRecords(new Libretto());
                    break;
                case OPERA : result = provider.getAllRecords(new Opera());
                    break;
                case SINGER : result = provider.getAllRecords(new Singer());
                    break;
                case AUTHOR : result = provider.getAllRecords(new Author());
                    break;     
            }
            if (result.getStatus().equals(ResultStatuses.OK)){
                note.setObjectId(result.getList().stream().findAny().get().getId());
                note.setObjectType(type.toString());
                note.setDescription(generateDescription());
                break;
            }                      
        }
        return note;
    }
    
    /**
     *
     * @return
     */
    public static String generateDescription(){
        List<String> words = new ArrayList<String>();
        words.addAll(Arrays.asList(new String[]{"may", "the", "force", "be", "with", "you", "always"}));
        int count = Math.round((float)Math.random()*6 + 1);
        String txt = "";
        for(int i = 0; i<count; i++){
            txt += " " + words.get(Math.round((float)Math.random()*6));
        }
        return txt;
    }
}
