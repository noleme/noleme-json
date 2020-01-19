package com.noleme.json.jackson;

import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;

/**
 * @author Pierre LECERF (pierre@noleme.com)
 * Created on 18/01/2020
 */
public interface FieldSerializer <T>
{
    /**
     *
     * @param gen
     * @param value
     * @throws IOException
     */
    void serialize(JsonGenerator gen, T value) throws IOException;
}
