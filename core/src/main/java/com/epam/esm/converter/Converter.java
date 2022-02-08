package com.epam.esm.converter;

/**
 *
 * @param <T> is Entity from layer service and fields can be change with layer service
 * @param <K> is Entity from layer dao and fields should not change
 */
public interface Converter <T, K> {
    /**
     * Converter entity in dto(entity service)
     * @param k entity from dao
     * @return entity from service
     */
    T toDTO(K k);

    /**
     * Converter dao entity in entity dao
     * @param t entity from service
     * @return entity from dao
     */
    K fromDTO(T t);
}
