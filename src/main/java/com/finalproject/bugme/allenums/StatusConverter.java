package com.finalproject.bugme.allenums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status,String> {

    @Override
    public String convertToDatabaseColumn(Status status){
        if(status==null){
            return null;
        }
        return status.getStatusString();
    }

    @Override
    public Status convertToEntityAttribute(String statusString){
        if(statusString==null){
            return null;
        }
        return Stream.of(Status.values()).filter(s->s.getStatusString().equals(statusString)).findFirst().orElseThrow(IllegalArgumentException::new);
    }

}
