package com.hit.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class implements the IDao interface and provides methods to perform CRUD operations
 * on entities of type TValue, using string keys.
 *
 * @param <TValue> The type of the entity to be persisted.
 */
public class Dao<TValue extends Serializable> implements IDao<String, TValue> {
    private String filePath;

    /**
     * Constructor for creating a Dao instance.
     *
     * @param file The name of the file to be used for storing data.
     */
    public Dao(String file) {
        this.filePath = file;

        try {
            if (!this.isFileExists(filePath)) {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath));
                objectOutputStream.writeObject(new HashMap<String, TValue>());
                objectOutputStream.flush();
                objectOutputStream.close();
            }

        } catch (IOException e) {
            System.out.println("Failed to open file for the first time: " + filePath);
        }
    }

    /**
     * Saves the provided entity into the file.
     *
     * @param tKey   The key associated with the entity to be saved.
     * @param tValue The entity to be saved.
     * @return {@code true} if the entity was successfully saved, {@code false} otherwise.
     */
    @Override
    public boolean save(String tKey, TValue tValue) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filePath));
            Map<String, TValue> entities = (HashMap<String, TValue>) objectInputStream.readObject();
            objectInputStream.close();

            entities.put(tKey, tValue);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath));
            objectOutputStream.writeObject(entities);
            objectOutputStream.flush();
            objectOutputStream.close();

            return true;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error in dao: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes the entity identified by the provided key from the file.
     *
     * @param tKey The key identifying the entity to be deleted.
     * @return {@code true} if the entity was successfully deleted, {@code false} otherwise.
     */
    @Override
    public boolean delete(String tKey) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filePath));
            Map<String, TValue> entities = (HashMap<String, TValue>) objectInputStream.readObject();
            objectInputStream.close();

            entities.remove(tKey);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath));
            objectOutputStream.writeObject(entities);
            objectOutputStream.flush();
            objectOutputStream.close();

            return true;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error in dao: " + e.getMessage());
            return false;
        }
    }

    /**
     * Finds and returns the entity identified by the provided key from the file.
     *
     * @param tKey The key identifying the entity to be found.
     * @return The entity identified by the provided key, or {@code null} if not found.
     */
    @Override
    public TValue find(String tKey) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filePath));
            Map<String, TValue> entities = (HashMap<String, TValue>) objectInputStream.readObject();
            objectInputStream.close();

            return entities.get(tKey);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error in dao: " + e.getMessage());
            return null;
        }
    }

    /**
     * Checks if the file exists.
     *
     * @param filePath The path of the file to check.
     * @return {@code true} if the file exists, {@code false} otherwise.
     */
    private boolean isFileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }
}
