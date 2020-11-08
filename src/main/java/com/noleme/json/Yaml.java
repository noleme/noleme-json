package com.noleme.json;

import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
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
public class Yaml
{
    private static final ObjectMapper defaultObjectMapper = newDefaultMapper();
    private static volatile ObjectMapper objectMapper = null;

    private Yaml() {}

    /**
     *
     * @return
     */
    public static ObjectMapper newDefaultMapper()
    {
        YAMLFactory factory = new YAMLFactory()
            .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
        ;
        ObjectMapper mapper = new ObjectMapper(factory)
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule())
        ;
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SnakeCaseStrategy.SNAKE_CASE);
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
    private static String generateYaml(Object o, boolean prettyPrint, boolean escapeNonASCII)
    {
        try {
            ObjectWriter writer = mapper().writer();
            if (prettyPrint)
                writer = writer.with(SerializationFeature.INDENT_OUTPUT);
            if (escapeNonASCII)
                writer = writer.with(JsonWriteFeature.ESCAPE_NON_ASCII);
            return writer.writeValueAsString(o);
        }
        catch (IOException e) {
            throw new JsonException(e);
        }
    }

    /**
     * Converts an object to JsonNode.
     *
     * @param mapper The ObjectMapper to use for conversion.
     * @param data Value to convert in Json.
     * @return the JSON node.
     */
    public static JsonNode toYaml(ObjectMapper mapper, final Object data)
    {
        try {
            return mapper.valueToTree(data);
        }
        catch (Exception e) {
            throw new JsonException(e);
        }
    }

    /**
     * Converts an object to JsonNode.
     *
     * @param data Value to convert in Json.
     * @return the JSON node.
     */
    public static JsonNode toYaml(final Object data)
    {
        return toYaml(mapper(), data);
    }

    /**
     * Converts a JsonNode to a Java value
     *
     * @param <A>   the type of the return value.
     * @param mapper The ObjectMapper to use for conversion.
     * @param json  Json value to convert.
     * @param clazz Expected Java value type.
     * @return the return value.
     */
    public static <A> A fromYaml(ObjectMapper mapper, JsonNode json, Class<A> clazz)
    {
        try {
            return mapper.treeToValue(json, clazz);
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
    public static <A> A fromYaml(JsonNode json, Class<A> clazz)
    {
        return fromYaml(mapper(), json, clazz);
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
        return generateYaml(json, false, false);
    }

    /**
     * Converts a JsonNode to its string representation, escaping non-ascii characters.
     *
     * @param json the JSON node to convert.
     * @return the string representation with escaped non-ascii characters.
     */
    public static String asciiStringify(JsonNode json)
    {
        return generateYaml(json, false, true);
    }

    /**
     * Converts a JsonNode to its string representation.
     *
     * @param json the JSON node to convert.
     * @return the string representation, pretty printed.
     */
    public static String prettyPrint(JsonNode json)
    {
        return generateYaml(json, true, false);
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
    public static JsonNode parse(InputStream src)
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
        return Json.asText(node);
    }

    /**
     *
     * @param node
     * @return
     */
    public static Long asLong(JsonNode node)
    {
        return Json.asLong(node);
    }

    /**
     *
     * @param node
     * @return
     */
    public static Integer asInteger(JsonNode node)
    {
        return Json.asInteger(node);
    }

    /**
     *
     * @param node
     * @return
     */
    public static Double asDouble(JsonNode node)
    {
        return Json.asDouble(node);
    }

    /**
     *
     * @param node
     * @return
     */
    public static Boolean asBoolean(JsonNode node)
    {
        return Json.asBoolean(node);
    }

    /**
     *
     * @param node
     * @return
     */
    public static ObjectNode asObjectNode(JsonNode node)
    {
        return Json.asObjectNode(node);
    }

    /**
     *
     * @param node
     * @return
     */
    public static ArrayNode asArrayNode(JsonNode node)
    {
        return Json.asArrayNode(node);
    }

    /**
     *
     * @param node
     * @return
     */
    public static Object asValue(JsonNode node)
    {
        return Json.asValue(node);
    }

    /**
     *
     * @param data
     * @return
     */
    public static ObjectNode toYamlObject(final Map<String, ?> data)
    {
        ObjectNode array = Yaml.newObject();
        for (Map.Entry<String, ?> item : data.entrySet())
            array.set(item.getKey(), Yaml.toYaml(item.getValue()));
        return array;
    }

    /**
     *
     * @param data
     * @return
     */
    public static ArrayNode toYamlArray(final List<?> data)
    {
        ArrayNode array = Yaml.newArray();
        for (Object item : data)
            array.add(Yaml.toYaml(item));
        return array;
    }
}