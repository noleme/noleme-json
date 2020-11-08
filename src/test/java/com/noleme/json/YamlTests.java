package com.noleme.json;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Pierre LECERF (pierre@noleme.com)
 * Created on 18/01/2020
 */
public class YamlTests
{
    @Test
    public void buildTest()
    {
        ObjectNode node = Yaml.newObject()
            .put("name", "MyName")
            .put("age", 123)
            .put("ratio", 0.2)
        ;
        node.set("list", Yaml.newArray()
            .add(1).add(2).add(3)
        );
        node.set("obj", Yaml.newObject()
            .put("key", "value")
        );

        assertEquals("name: \"MyName\"\n" +
            "age: 123\n" +
            "ratio: 0.2\n" +
            "list:\n" +
            "- 1\n" +
            "- 2\n" +
            "- 3\n" +
            "obj:\n" +
            "  key: \"value\"\n", Yaml.stringify(node));
    }
}
