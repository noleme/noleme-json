package com.noleme.json.jackson.serializer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pierre LECERF (pierre@noleme.com)
 * Created on 18/01/2020
 */
public class TestModel
{
    private String id;
    private String label;
    private List<String> list;

    public TestModel()
    {
        this.list = new ArrayList<>();
    }

    public String getId()
    {
        return this.id;
    }

    public TestModel setId(String id)
    {
        this.id = id;
        return this;
    }

    public String getLabel()
    {
        return this.label;
    }

    public TestModel setLabel(String label)
    {
        this.label = label;
        return this;
    }

    public List<String> getList()
    {
        return this.list;
    }

    public TestModel setList(List<String> list)
    {
        this.list = list;
        return this;
    }

    public TestModel addListValue(String value)
    {
        this.list.add(value);
        return this;
    }
}
