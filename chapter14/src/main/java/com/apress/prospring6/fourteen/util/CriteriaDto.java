package com.apress.prospring6.fourteen.util;

import jakarta.validation.constraints.NotEmpty;

public class CriteriaDto {
    private String fieldName;

    @NotEmpty
    private String fieldValue;

    private Boolean exactMatch;

    private String noResults;

    public String getFieldName(){return fieldName;}

    public void setFieldName(){this.fieldName = fieldName;}

    public String getFieldValue(){return fieldValue;}

    public void setFieldValue(){this.fieldValue = fieldValue;}

    public Boolean getExactMatch(){return exactMatch;}

    public void setExactMatch(Boolean exactMatch){this.exactMatch = exactMatch;}

    public String getNoResults(){return noResults;}

    public void setNoResults(String noResults){this.noResults = noResults;}

    public boolean isEmpty(){return fieldName == null && fieldValue == null;}

}