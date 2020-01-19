package com.noleme.json.jackson;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.noleme.json.Json;

import java.util.Map;

/**
 * @author Pierre LECERF (pierre@noleme.com)
 * Created on 19/01/2020
 */
public final class Deserializers
{
    private Deserializers() {}

    /**
     *
     * @param mapper
     * @param name
     * @param type
     * @param deserializer
     * @param <T>
     */
    public static <T> void register(ObjectMapper mapper, String name, Class<T> type, JsonDeserializer<T> deserializer)
    {
        mapper.registerModule(new SimpleModule(name) {
            { this.addDeserializer(type, deserializer); }
        });
    }

    /**
     *
     * @param mapper
     * @param type
     * @param deserializer
     * @param <T>
     */
    public static <T> void register(ObjectMapper mapper, Class<T> type, JsonDeserializer<T> deserializer)
    {
        register(mapper, type.getName(), type, deserializer);
    }

    /**
     *
     * @param name
     * @param type
     * @param deserializer
     * @param <T>
     */
    public static <T> void register(String name, Class<T> type, JsonDeserializer<T> deserializer)
    {
        register(Json.mapper(), name, type, deserializer);
    }

    /**
     *
     * @param type
     * @param deserializer
     * @param <T>
     */
    public static <T> void register(Class<T> type, JsonDeserializer<T> deserializer)
    {
        register(Json.mapper(), type, deserializer);
    }

    /**
     *
     * @param name
     * @param deserializers
     * @param <T>
     */
    public static <T> void register(ObjectMapper mapper, String name, Map<Class<T>, JsonDeserializer<T>> deserializers)
    {
        mapper.registerModule(new SimpleModule(name) {
            { deserializers.forEach(this::addDeserializer); }
        });
    }

    /**
     *
     * @param name
     * @param deserializers
     * @param <T>
     */
    public static <T> void register(String name, Map<Class<T>, JsonDeserializer<T>> deserializers)
    {
        register(Json.mapper(), name, deserializers);
    }
}
