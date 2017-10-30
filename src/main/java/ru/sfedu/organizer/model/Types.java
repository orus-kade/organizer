
package ru.sfedu.organizer.model;

import static ru.sfedu.organizer.Constants.*;

/**
 *
 * @author sterie
 */
public enum Types {
    ARIA("Aria", Aria.class, FIELDS_ARIA),
    COMPOSER("Composer", Composer.class, FIELDS_COMPOSER), 
    LIBRETTO("Libretto", Libretto.class, FIELDS_LIBRETTO), 
    OPERA("Opera", Opera.class, FIELDS_OPERA), 
    SINGER("Singer", Singer.class, FIELDS_SINGER), 
    WRITER("Writer", Writer.class, FIELDS_WRITER);
    
    private final String type;
    private final Class className;
    private final String[] fields;

    private Types(String type, Class className, String[] fields) {
        this.type = type;
        this.className = className;
        this.fields = fields;
    }

    public String getType() {
        return type;
    }

    public Class getClassName() {
        return className;
    }

    public String[] getFields() {
        return fields;
    }

}