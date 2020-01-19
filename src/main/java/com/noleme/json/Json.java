package com.noleme.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.POJONode;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Copied from play.libs.Json
 * We could trim this down quite a bit, but since this was made during a migration from a Play environment to a more agnostic environment, the shortest path was a clone of their helper.
 *
 * Note: The original implementation threw unspecified RuntimeExceptions, we changed those to a catchable JsonException (still unchecked).
 * Note: Added the Jackson Afterburner module and several helper methods.
 *
 * @author Pierre Lecerf (pierre.lecerf@gmail.com)
 * Created on 24/04/2019
 */
public class Json
{
    private static final ObjectMapper defaultObjectMapper = newDefaultMapper();
    private static volatile ObjectMapper objectMapper = null;

    private Json() {}

    /**
     *
     * @return
     */
    public static ObjectMapper newDefaultMapper()
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new AfterburnerModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    /**
     * Gets the ObjectMapper used to serialize and deserialize objects to and from JSON values.
     * <p>
     * This can be set to a custom implementation using Json.setObjectMapper.
     *
     * @return the ObjectMapper currently being used
     */
    public static ObjectMapper mapper()
    {
        if (objectMapper == null)
            return defaultObjectMapper;
        else
            return objectMapper;
    }

    /**
     *
     * @param o
     * @param prettyPrint
     * @param escapeNonASCII
     * @return
     */
    private static String generateJson(Object o, boolean prettyPrint, boolean escapeNonASCII)
    {
        try {
            ObjectWriter writer = mapper().writer();
            if (prettyPrint)
                writer = writer.with(SerializationFeature.INDENT_OUTPUT);
            if (escapeNonASCII)
                writer = writer.with(JsonGenerator.Feature.ESCAPE_NON_ASCII);
            return writer.writeValueAsString(o);
        }
        catch (IOException e) {
            throw new JsonException(e);
        }
    }

    /**
     * Converts an object to JsonNode.
     *
     * @param data Value to convert in Json.
     * @return the JSON node.
     */
    public static JsonNode toJson(final Object data)
    {
        try {
            return mapper().valueToTree(data);
        }
        catch (Exception e) {
            throw new JsonException(e);
        }
    }

    /**
     * Converts a JsonNode to a Java value
     *
     * @param <A>   the type of the return value.
     * @param json  Json value to convert.
     * @param clazz Expected Java value type.
     * @return the return value.
     */
    public static <A> A fromJson(JsonNode json, Class<A> clazz)
    {
        try {
            return mapper().treeToValue(json, clazz);
        }
        catch (Exception e) {
            throw new JsonException(e);
        }
    }

    /**
     * Creates a new empty ObjectNode.
     *
     * @return new empty ObjectNode.
     */
    public static ObjectNode newObject()
    {
        return mapper().createObjectNode();
    }

    /**
     * Creates a new empty ArrayNode.
     *
     * @return a new empty ArrayNode.
     */
    public static ArrayNode newArray()
    {
        return mapper().createArrayNode();
    }

    /**
     * Converts a JsonNode to its string representation.
     *
     * @param json the JSON node to convert.
     * @return the string representation.
     */
    public static String stringify(JsonNode json)
    {
        return generateJson(json, false, false);
    }

    /**
     * Converts a JsonNode to its string representation, escaping non-ascii characters.
     *
     * @param json the JSON node to convert.
     * @return the string representation with escaped non-ascii characters.
     */
    public static String asciiStringify(JsonNode json)
    {
        return generateJson(json, false, true);
    }

    /**
     * Converts a JsonNode to its string representation.
     *
     * @param json the JSON node to convert.
     * @return the string representation, pretty printed.
     */
    public static String prettyPrint(JsonNode json)
    {
        return generateJson(json, true, false);
    }

    /**
     * Parses a String representing a json, and return it as a JsonNode.
     *
     * @param src the JSON string.
     * @return the JSON node.
     */
    public static JsonNode parse(String src)
    {
        try {
            return mapper().readTree(src);
        }
        catch (Throwable t) {
            throw new JsonException(t);
        }
    }

    /**
     * Parses a InputStream representing a json, and return it as a JsonNode.
     *
     * @param src the JSON input stream.
     * @return the JSON node.
     */
    public static JsonNode parse(java.io.InputStream src)
    {
        try {
            return mapper().readTree(src);
        }
        catch (Throwable t) {
            throw new JsonException(t);
        }
    }

    /**
     * Parses a byte array representing a json, and return it as a JsonNode.
     *
     * @param src the JSON input bytes.
     * @return the JSON node.
     */
    public static JsonNode parse(byte[] src)
    {
        try {
            return mapper().readTree(src);
        }
        catch (Throwable t) {
            throw new JsonException(t);
        }
    }

    /**
     * Inject the object mapper to use.
     * <p>
     * This is intended to be used when Play starts up.  By default, Play will inject its own object mapper here,
     * but this mapper can be overridden either by a custom module.
     *
     * @param mapper the object mapper.
     */
    public static void setObjectMapper(ObjectMapper mapper)
    {
        objectMapper = mapper;
    }

    /**
     *
     * @param node
     * @return
     */
    public static String asText(JsonNode node)
    {
        return node.isNull() ? null : node.asText();
    }

    /**
     *
     * @param node
     * @return
     */
    public static Long asLong(JsonNode node)
    {
        return node.isNull() ? null : node.asLong();
    }

    /**
     *
     * @param node
     * @return
     */
    public static Integer asInteger(JsonNode node)
    {
        return node.isNull() ? null : node.asInt();
    }

    /**
     *
     * @param node
     * @return
     */
    public static Double asDouble(JsonNode node)
    {
        return node.isNull() ? null : node.asDouble();
    }

    /**
     *
     * @param node
     * @return
     */
    public static Boolean asBoolean(JsonNode node)
    {
        return node.isNull() ? null : node.asBoolean();
    }

    /**
     *
     * @param node
     * @return
     */
    public static ObjectNode asObjectNode(JsonNode node)
    {
        if (!node.isObject())
            throw new JsonException("The provided node cannot be interpreted as an ObjectNode");
        return node.isNull() ? null : (ObjectNode) node;
    }

    /**
     *
     * @param node
     * @return
     */
    public static ArrayNode asArrayNode(JsonNode node)
    {
        if (!node.isArray())
            throw new JsonException("The provided node cannot be interpreted as an ArrayNode");
        return node.isNull() ? null : (ArrayNode) node;
    }

    /**
     *
     * @param node
     * @return
     */
    public static Object asValue(JsonNode node)
    {
        if (!node.isValueNode())
            throw new JsonException("The provided node is not a ValueNode");
        if (node.isNull())
            return null;
        if (node.isTextual())
            return node.asText();
        if (node.isBoolean())
            return node.asBoolean();
        if (node.isDouble())
            return node.asDouble();
        if (node.isLong())
            return node.asLong();
        if (node.isInt())
            return node.asInt();
        if (node.isPojo())
            return ((POJONode)node).getPojo();
        return node;
    }

    /**
     *
     * @param data
     * @return
     */
    public static ObjectNode toJsonObject(final Map<String, ?> data)
    {
        ObjectNode array = Json.newObject();
        for (Map.Entry<String, ?> item : data.entrySet())
            array.set(item.getKey(), Json.toJson(item.getValue()));
        return array;
    }

    /**
     *
     * @param data
     * @return
     */
    public static ArrayNode toJsonArray(final List<?> data)
    {
        ArrayNode array = Json.newArray();
        for (Object item : data)
            array.add(Json.toJson(item));
        return array;
    }
}