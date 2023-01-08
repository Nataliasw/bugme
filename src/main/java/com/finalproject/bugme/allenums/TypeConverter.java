package com.finalproject.bugme.allenums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class TypeConverter implements AttributeConverter<Type,String> {

    @Override
    public String convertToDatabaseColumn(Type type){
        if(type==null){
            return null;
        }
        return type.getTypeString();
    }

    @Override
    public Type convertToEntityAttribute(String typeString){
        if(typeString==null){
            return null;
        }
        return Stream.of(Type.values()).filter(s->s.getTypeString().equals(typeString)).findFirst().orElseThrow(IllegalArgumentException::new);
    }

}
