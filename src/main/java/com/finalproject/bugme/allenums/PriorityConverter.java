package com.finalproject.bugme.allenums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class PriorityConverter  implements AttributeConverter<Priority,String> {

    @Override
    public String convertToDatabaseColumn(Priority priority){
        if(priority==null){
            return null;
        }
        return priority.getPriorityString();
    }

    @Override
    public Priority convertToEntityAttribute(String priorityString){
        if(priorityString==null){
            return null;
        }
        return Stream.of(Priority.values()).filter(s->s.getPriorityString().equals(priorityString)).findFirst().orElseThrow(IllegalArgumentException::new);
    }


}
