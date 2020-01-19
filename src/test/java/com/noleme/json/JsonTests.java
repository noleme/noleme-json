package com.noleme.json;

import com.fasterxml.jackson.databind.node.ObjectNode;
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

        assertEquals("{\"name\":\"MyName\",\"age\":123,\"ratio\":0.2,\"list\":[1,2,3],\"obj\":{\"key\":\"value\"}}", node.toString());
    }
}
