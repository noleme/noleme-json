package com.noleme.json.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
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
     * @param mapper
     * @param name
     * @param type
     * @param serializer
     * @param <T>
     */
    public static <T> void register(ObjectMapper mapper, String name, Class<T> type, JsonSerializer<T> serializer)
    {
        mapper.registerModule(new SimpleModule(name) {
            { this.addSerializer(type, serializer); }
        });
    }

    /**
     *
     * @param mapper
     * @param type
     * @param serializer
     * @param <T>
     */
    public static <T> void register(ObjectMapper mapper, Class<T> type, JsonSerializer<T> serializer)
    {
        register(mapper, type.getName(), type, serializer);
    }

    /**
     *
     * @param name
     * @param type
     * @param serializer
     * @param <T>
     */
    public static <T> void register(String name, Class<T> type, JsonSerializer<T> serializer)
    {
        register(Json.mapper(), name, type, serializer);
    }

    /**
     *
     * @param type
     * @param serializer
     * @param <T>
     */
    public static <T> void register(Class<T> type, JsonSerializer<T> serializer)
    {
        register(Json.mapper(), type, serializer);
    }

    /**
     *
     * @param mapper
     * @param name
     * @param serializers
     * @param <T>
     */
    public static <T> void register(ObjectMapper mapper, String name, Map<Class<T>, JsonSerializer<T>> serializers)
    {
        mapper.registerModule(new SimpleModule(name) {
            { serializers.forEach(this::addSerializer); }
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
        register(Json.mapper(), name, serializers);
    }

    /**
     *
     * @param gen
     * @param fieldName
     * @param value
     * @throws IOException
     */
    public static void writeValueField(JsonGenerator gen, String fieldName, Object value) throws IOException
    {
        gen.writeFieldName(fieldName);
        writeValue(gen, value);
    }

    /**
     *
     * @param gen
     * @param value
     * @throws IOException
     */
    public static void writeValue(JsonGenerator gen, Object value) throws IOException
    {
        if (value instanceof Integer)
            gen.writeNumber((Integer) value);
        else if (value instanceof Long)
            gen.writeNumber((Long) value);
        else if (value instanceof Float)
            gen.writeNumber((Float) value);
        else if (value instanceof Double)
            gen.writeNumber((Double) value);
        else if (value instanceof Boolean)
            gen.writeBoolean((Boolean) value);
        else if (value instanceof byte[])
            gen.writeBinary((byte[]) value);
        else
            gen.writeString(value.toString());
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
    public static <T> void writeObjectField(JsonGenerator gen, String fieldName, Map<String, T> values, FieldSerializer<T> serializer) throws IOException
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
     * @throws IOException
     */
    public static void writeObjectField(JsonGenerator gen, String fieldName, Map<String, ?> values) throws IOException
    {
        gen.writeObjectFieldStart(fieldName);
        for (Map.Entry<String, ?> e : values.entrySet())
        {
            gen.writeFieldName(e.getKey());
            writeValue(gen, e.getValue());
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
    public static <T> void writeArrayField(JsonGenerator gen, String fieldName, Collection<T> values, FieldSerializer<T> serializer) throws IOException
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
     * @param values
     * @throws IOException
     */
    public static void writeArrayField(JsonGenerator gen, String fieldName, Collection<?> values) throws IOException
    {
        gen.writeArrayFieldStart(fieldName);
        for (Object value : values)
            writeValue(gen, value);
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
