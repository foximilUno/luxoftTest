package storage;

import utility.CsvHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

public class Storage {
    private Node root;

    public Storage() {
        this.root = null;
    }

    public void addNodes(ArrayList<Node> values) {
        ArrayList<Node> forIterate = new ArrayList<>(values);
        for (Node node : values) {
            findLinkedNode(forIterate, null, node.getValue());
        }
    }

    private Node findLinkedNode(ArrayList<Node> values, Node curNode, String parent) {

        if (parent != null) {
            Node parentNode = null;
            for (String searchedParent : findParentInlist(values, parent)) {
                parentNode = findLinkedNode(values, new Node(parent, searchedParent), searchedParent);
            }
            if (findParentInlist(values, parent).size() == 0)
                parentNode = findLinkedNode(values, new Node(parent, null), null);
            if (parentNode.getParent() == null)
                if (this.root == null) {
                    this.root = parentNode;
                } else {
                    if (!this.root.equals(parentNode))
                        throw new IllegalStateException("Data set is not hierarchical");
                    else
                        parentNode = this.root;
                }
            Node childNode = curNode;
            if (childNode != null)
                childNode = parentNode.addChild(childNode);
            return childNode;
        } else {
            return curNode;
        }
    }

    private ArrayList<String> findParentInlist(ArrayList<Node> list, String value) {
        ArrayList<String> parents = new ArrayList<>();
        for (Node node : list) {
            if (node.getValue().equals(value))
                parents.add(node.getParent());
        }
        return parents;
    }
    
    public void printStorage() {
        if (root != null) {
            System.out.println(root.print(0));
        }
        System.out.println("\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-");
    }

    public void printConditionToFile(Predicate predicate, String filename) {
        StringBuilder sb = new StringBuilder();
        checkPredicate(this.root, predicate, sb);
        CsvHelper.writeCsv(filename, sb);
    }

    private void checkPredicate(Node node, Predicate p, StringBuilder sb) {
        for (Node child : node.getChilds()) {
            checkPredicate(child, p, sb);
            if (p.test(child))
                sb.append(child).append("\n");
        }
    }

    public void findSameGroups(String filename) {
        HashMap<String, ArrayList<Node>> finded = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        recur(finded, this.root);
        for (String value : finded.keySet()) {
            if (finded.get(value).size() > 1) {
                for (Node node : finded.get(value)) {
                    sb.append(node.getValue()).append(",");
                }
                sb.append("\n");
            }
        }
        CsvHelper.writeCsv(filename, sb);
    }

    private void recur(HashMap<String, ArrayList<Node>> finded, Node node) {

        if (finded.get(node.unifyString()) == null) {

            ArrayList<Node> curArray = new ArrayList<>();
            curArray.add(node);
            finded.put(node.unifyString(), curArray);
        } else {
            finded.get(node.unifyString()).add(node);
        }
        for (Node child : node.getChilds()) {
            recur(finded, child);
        }
    }
}
