package com.sb.react.sample.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NewException extends RuntimeException {
    private String resourceName ;
    private String fieldName ;
    private Object fieldValue ;

    public NewException(String resouceName,
                                     String fieldName,
                                     Object fieldValue){
        super(String.format("%s not found with %s : '%s'",
                resouceName, fieldName, fieldValue)) ;
        this.resourceName = resouceName;
        this.fieldName = fieldName ;
        this.fieldValue = fieldValue ;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }
}
