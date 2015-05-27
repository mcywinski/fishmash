package net.elenx.fishmash.utilities;

import java.util.List;

public class Cycle<Type>
{
    private final Type[] array;
    private int index = 0;
    private final int lastIndex;

    public Cycle(List<Type> list)
    {
        // I can always cast this Object[] to Type[], because I accept only lists of class Type
        //noinspection unchecked
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
