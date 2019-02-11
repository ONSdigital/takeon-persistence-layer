package uk.gov.ons.collection.PersistenceLayer.utilities;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MapConverter {

    public String mapToString(Map<String, String> matrixVars) {
        String converted_string = new String();
        Set set = matrixVars.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry) iterator.next();
            converted_string += mentry.getKey() + "=" + mentry.getValue() + ";";
        }

        if (converted_string.endsWith(";")) {
            converted_string = converted_string.substring(0, converted_string.length() - 1);
        }
        return converted_string;
    }

}
