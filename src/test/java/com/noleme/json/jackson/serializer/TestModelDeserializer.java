package com.noleme.json.jackson.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * @author Pierre LECERF (pierre@noleme.com)
 * Created on 19/01/2020
 */
public class TestModelDeserializer extends StdDeserializer<TestModel>
{

    protected TestModelDeserializer(Class<?> vc)
    {
        super(vc);
    }

    @Override
    public TestModel deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException
    {
        return null;
    }
}
