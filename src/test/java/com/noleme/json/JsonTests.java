package com.noleme.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.noleme.json.jackson.serializer.TestModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Pierre LECERF (pierre@noleme.com)
 * Created on 18/01/2020
 */
public class JsonTests
{
    @Test
    public void buildTest()
    {
        ObjectNode node = Json.newObject()
            .put("name", "MyName")
            .put("age", 123)
            .put("ratio", 0.2)
        ;
        node.set("list", Json.newArray()
            .add(1).add(2).add(3)
        );
        node.set("obj", Json.newObject()
            .put("key", "value")
        );

        assertEquals("{\"name\":\"MyName\",\"age\":123,\"ratio\":0.2,\"list\":[1,2,3],\"obj\":{\"key\":\"value\"}}", Json.stringify(node));
    }

    @Test
    public void generateJsonSchema()
    {
        JsonNode schema = Json.generateSchema(TestModel.class);

        assertEquals("{\"$schema\":\"http://json-schema.org/draft-04/schema#\",\"title\":\"Test Model\",\"type\":\"object\",\"additionalProperties\":false,\"properties\":{\"id\":{\"type\":\"string\"},\"label\":{\"type\":\"string\"},\"list\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}}}}", Json.stringify(schema));
    }
}
