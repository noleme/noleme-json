package com.noleme.json;

/**
 * @author Pierre Lecerf (pierre.lecerf@gmail.com)
 * Created on 29/09/2019
 */
public class JsonException extends RuntimeException
{
    public JsonException()
    {
        super();
    }

    public JsonException(String message)
    {
        super(message);
    }

    public JsonException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public JsonException(Throwable cause)
    {
        super(cause);
    }
}
