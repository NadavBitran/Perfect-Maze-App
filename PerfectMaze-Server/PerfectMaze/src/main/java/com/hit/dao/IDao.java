package com.hit.dao;

import java.io.Serializable;
import java.util.List;

/**
 * This interface represents a generic Data Access Object for CRUD operations.
 * @param <TKey>   The type of the key used to identify entities.
 * @param <TValue> The type of the entity to be persisted.
 */
public interface IDao<TKey extends Serializable, TValue extends Serializable> {

    /**
     * Saves/Updates the provided entity.
     *
     * @param value The entity to be saved.
     * @return      {@code true} if the entity was successfully saved, {@code false} otherwise.
     */
    public boolean save(TKey key, TValue value);

    /**
     * Deletes the entity identified by the provided key.
     *
     * @param key The key identifying the entity to be deleted.
     * @return    {@code true} if the entity was successfully deleted, {@code false} otherwise.
     */
    public boolean delete(TKey key);

    /**
     * Finds and returns the entity identified by the provided key.
     *
     * @param key The key identifying the entity to be found.
     * @return    The entity identified by the provided key, or {@code null} if not found.
     */
    public TValue find(TKey key);
}
