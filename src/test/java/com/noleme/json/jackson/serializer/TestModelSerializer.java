package com.noleme.json.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.noleme.json.jackson.Serializers;

import java.io.IOException;

/**
 * @author Pierre LECERF (pierre@noleme.com)
 * Created on 18/01/2020
 */
public class TestModelSerializer extends JsonSerializer<TestModel>
{
    @Override
    public void serialize(TestModel model, JsonGenerator gen, SerializerProvider serializers) throws IOException
    {
        gen.writeStartObject();
        gen.writeStringField("label", model.getLabel());
        Serializers.writeArrayFieldString(gen, "list", model.getList());
        gen.writeEndObject();
    }
}