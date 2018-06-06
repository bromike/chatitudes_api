package udes.chat_api.utils;

import java.util.ArrayList;
import java.util.List;

public class Converter
{
    public static <E> List<E> iterableToList(Iterable<E> iterable)
    {
        List<E> list = new ArrayList<>();
        iterable.forEach(list::add);

        return list;
    }
}
