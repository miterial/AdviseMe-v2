package com.lanagj.adviseme.converter;

import org.springframework.core.convert.ConversionException;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GeneralConverterService {

    private final ConversionService conversionService;

    public GeneralConverterService(ConversionService conversionService) {

        this.conversionService = conversionService;
    }

    /**
     * Convert single object of type {@literal S} to type {@literal T}
     *
     * @param source initial object
     * @param tClass type of resulting object
     * @return converted object
     */
    public <T, S> T convert(S source, Class<T> tClass) {

        return this.conversionService.convert(source, tClass);
    }

    /**
     * Convert list of objects of type {@literal S} to type {@literal T}
     *
     * @param sourceList initial list
     * @param sClass     type of initial object
     * @param tClass     type of resulting object
     * @return converted list of objects
     * @throws ConversionException      if a conversion exception occurred
     * @throws IllegalArgumentException if targetType is {@code null},
     *                                  or {@code sourceType} is {@code null} but source is not {@code null}
     * @throws ClassCastException       if result of the conversion cannot be casted to {@literal List<T>}
     */
    @SuppressWarnings("unchecked")
    public <T, S> List<T> convertList(List<S> sourceList, Class<S> sClass, Class<T> tClass) {

        return (List<T>) this.conversionService.convert(sourceList,
                TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(sClass)),
                TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(tClass)));
    }
}
