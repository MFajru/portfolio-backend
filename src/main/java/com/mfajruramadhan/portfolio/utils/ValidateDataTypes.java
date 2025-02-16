package com.mfajruramadhan.portfolio.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ValidateDataTypes {

    public List<String> validate(Object request, Class<?> entityClass) {
        List<String> error = new ArrayList<>();
        for (Field field: entityClass.getDeclaredFields()) {
            try {
                Field requestField = request.getClass().getDeclaredField(field.getName());
                if (field.getType() != requestField.getType()) {
                    String errMess = requestField + " is not " + field.getType();
                    error.add(errMess);
                }
            } catch (NoSuchFieldException e) {
                error.add("request field not found");
            }
        }
        return error;
    }
 }
