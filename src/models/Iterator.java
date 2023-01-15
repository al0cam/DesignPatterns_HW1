package models;

import java.util.List;

public class Iterator {
    private List<Component> collection;
    private int position = 0;

    public Iterator(List<Component> collection) {
        this.collection = collection;
    }

    public boolean hasNext()
    {
        if(position < collection.size())
        {
            return true;
        }
        else 
            return false;
    }

    public Component next()
    {
        Component component = collection.get(position);
        position++;
        return component;
    }
}
