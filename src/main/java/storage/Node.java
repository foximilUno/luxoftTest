package storage;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;

public class Node {

    @Getter
    private final String parent;
    @Getter
    private final String value;
    @Getter
    private final ArrayList<Node> childs;

    public Node(String value, String parent) {
        this.value = value;
        this.parent = parent;
        this.childs = new ArrayList<>();
    }

    public Node addChild(Node node) {
        if (!childs.contains(node)) {
            childs.add(node);
            return node;
        } else
            return childs.get(childs.indexOf(node));
    }

    public String print(int level) {
        String curNodeEntity = "Node { value='" + value + "}";
        StringBuilder childsEntity = new StringBuilder();
        for (Node child : childs) {
            childsEntity.append("\n").append("\t".repeat(level + 1)).append(child.print(level + 2));
        }
        return curNodeEntity + childsEntity;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    @Override
    public boolean equals(Object node) {
        if (this.getParent() != null && ((Node) node).getParent() != null)
            return this.value.equals(((Node) node).value) && this.getParent().equals(((Node) node).parent);
        else
            return this.value.equals(((Node) node).value);
    }

    public String unifyString() {
        ArrayList<Character> sorted = new ArrayList<>();
        for (char cx : this.value.toLowerCase().toCharArray()) {
            sorted.add(cx);
        }
        sorted.sort(Comparator.naturalOrder());
        StringBuilder sb = new StringBuilder();
        for (char cx : sorted) {
            sb.append(cx);
        }
        return sb.toString();
    }
}
