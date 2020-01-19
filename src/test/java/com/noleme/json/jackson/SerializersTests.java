package com.noleme.json.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.noleme.json.Json;
import com.noleme.json.jackson.serializer.TestModel;
import com.noleme.json.jackson.serializer.TestModelSerializer;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Pierre LECERF (pierre@noleme.com)
 * Created on 18/01/2020
 */
public class SerializersTests
{
    static {
        Serializers.register(TestModel.class, new TestModelSerializer());
    }

    @Test
    public void serializeOneTest()
    {
        TestModel data = new TestModel()
            .setId("A1234B")
            .setLabel("Label")
            .addListValue("v1")
            .addListValue("v2")
        ;

        JsonNode json = Json.toJson(data);

        assertEquals("{\"label\":\"Label\",\"list\":[\"v1\",\"v2\"]}", json.toString());
    }

    @Test
    public void serializeManyTest()
    {
        Serializers.register(TestModel.class, new TestModelSerializer());

        List<TestModel> models = Arrays.asList(
            new TestModel()
                .setId("A1234B")
                .setLabel("Label")
                .addListValue("v1")
                .addListValue("v2"),
            new TestModel()
                .setId("A1234B")
                .setLabel("Other")
                .addListValue("v2")
                .addListValue("v3"),
            new TestModel()
                .setId("A1234B")
                .setLabel("Meuh")
                .addListValue("v3")
                .addListValue("v4")
        );

        JsonNode json = Json.toJson(models);

        assertEquals("[{\"label\":\"Label\",\"list\":[\"v1\",\"v2\"]},{\"label\":\"Other\",\"list\":[\"v2\",\"v3\"]},{\"label\":\"Meuh\",\"list\":[\"v3\",\"v4\"]}]", json.toString());
    }
}
