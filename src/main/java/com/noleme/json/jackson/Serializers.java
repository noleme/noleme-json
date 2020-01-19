package com.noleme.json.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.noleme.json.Json;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * @author Pierre LECERF (pierre@noleme.com)
 * Created on 18/01/2020
 */
public final class Serializers
{
    private Serializers() {}

    /**
     *
     * @param type
     * @param serializer
     * @param <T>
     */
    public static <T> void register(Class<T> type, JsonSerializer<T> serializer)
    {
        Json.mapper().registerModule(new SimpleModule(type.getName()) {
            { this.addSerializer(type, serializer); }
        });
    }

    /**
     *
     * @param name
     * @param serializers
     * @param <T>
     */
    public static <T> void register(String name, Map<Class<T>, JsonSerializer<T>> serializers)
    {
        Json.mapper().registerModule(new SimpleModule(name) {
            { serializers.forEach(this::addSerializer); }
        });
    }

    /**
     *
     * @param gen
     * @param fieldName
     * @param values
     * @param serializer
     * @param <T>
     * @throws IOException
     */
    public static <T> void writeObject(JsonGenerator gen, String fieldName, Map<String, T> values, FieldSerializer<T> serializer) throws IOException
    {
        gen.writeObjectFieldStart(fieldName);
        for (Map.Entry<String, T> e : values.entrySet())
        {
            gen.writeFieldName(e.getKey());
            serializer.serialize(gen, e.getValue());
        }
        gen.writeEndObject();
    }

    /**
     *
     * @param gen
     * @param fieldName
     * @param values
     * @param serializer
     * @param <T>
     * @throws IOException
     */
    public static <T> void writeArray(JsonGenerator gen, String fieldName, Collection<T> values, FieldSerializer<T> serializer) throws IOException
    {
        gen.writeArrayFieldStart(fieldName);
        for (T value : values)
            serializer.serialize(gen, value);
        gen.writeEndArray();
    }

    /**
     *
     * @param gen
     * @param fieldName
     * @param strings
     * @throws IOException
     */
    public static void writeArrayFieldString(JsonGenerator gen, String fieldName, Collection<String> strings) throws IOException
    {
        gen.writeArrayFieldStart(fieldName);
        for (String value : strings)
            gen.writeString(value);
        gen.writeEndArray();
    }

    /**
     *
     * @param gen
     * @param fieldName
     * @param numbers
     * @throws IOException
     */
    public static void writeArrayFieldInteger(JsonGenerator gen, String fieldName, Collection<Integer> numbers) throws IOException
    {
        gen.writeArrayFieldStart(fieldName);
        for (Integer value : numbers)
            gen.writeNumber(value);
        gen.writeEndArray();
    }

    /**
     *
     * @param gen
     * @param fieldName
     * @param numbers
     * @throws IOException
     */
    public static void writeArrayFieldLong(JsonGenerator gen, String fieldName, Collection<Long> numbers) throws IOException
    {
        gen.writeArrayFieldStart(fieldName);
        for (Long value : numbers)
            gen.writeNumber(value);
        gen.writeEndArray();
    }

    /**
     *
     * @param gen
     * @param fieldName
     * @param numbers
     * @throws IOException
     */
    public static void writeArrayFieldFloat(JsonGenerator gen, String fieldName, Collection<Float> numbers) throws IOException
    {
        gen.writeArrayFieldStart(fieldName);
        for (Float value : numbers)
            gen.writeNumber(value);
        gen.writeEndArray();
    }

    /**
     *
     * @param gen
     * @param fieldName
     * @param numbers
     * @throws IOException
     */
    public static void writeArrayFieldDouble(JsonGenerator gen, String fieldName, Collection<Double> numbers) throws IOException
    {
        gen.writeArrayFieldStart(fieldName);
        for (Double value : numbers)
            gen.writeNumber(value);
        gen.writeEndArray();
    }
}
