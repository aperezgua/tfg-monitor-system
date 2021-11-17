package edu.uoc.tfgmonitorsystem.common.model.document;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Entidad document base para generar el toString automáticamente del resto de superclases.
 */
public abstract class BaseDocument {

    /**
     * Se sobreescribe el toString y se usa ToStringBuilder de javalang.
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
