package models;

import java.util.List;

public abstract class ComponentAndIterator {
    protected List<Component> children;

    public void add(Component component)
    {
        children.add(component);
    }

    public void remove(Component component)
    {
        children.remove(component);
    }

    public List<Component> getChildren() {
        return children;
    }

    public Iterator createIterator()
    {
        return new Iterator(children);
    }
}
