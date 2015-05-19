package net.elenx.fishmash;

import java.util.List;

public class Cycle<Type>
{
    private Type[] array;
    private int index = 0;
    private int lastIndex;

    public Cycle(List<Type> list)
    {
        array = (Type[]) list.toArray();
        lastIndex = list.size() - 1;
    }

    public Type next()
    {
        if(index == lastIndex)
        {
            index = 0;
        }
        else
        {
            index++;
        }

        return array[index];
    }

    public Type previous()
    {
        if(index == 0)
        {
            index = lastIndex;
        }
        else
        {
            index--;
        }

        return array[index];
    }
}
