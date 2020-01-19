package com.noleme.json.jackson;

import com.fasterxml.jackson.databind.JsonDeserializer;
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
     * @param type
     * @param deserializer
     * @param <T>
     */
    public static <T> void register(Class<T> type, JsonDeserializer<T> deserializer)
    {
        Json.mapper().registerModule(new SimpleModule(type.getName()) {
            { this.addDeserializer(type, deserializer); }
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
        Json.mapper().registerModule(new SimpleModule(name) {
            { deserializers.forEach(this::addDeserializer); }
        });
    }
}
