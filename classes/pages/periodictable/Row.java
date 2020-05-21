package classes.pages.periodictable;

import java.util.ArrayList;

public class Row {
    private ArrayList<Element> group;

    public Row() {
        this.group = new ArrayList<Element>();
    }

    public void add(Element element) {
        this.group.add(element);
    }
}