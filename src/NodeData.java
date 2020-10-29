import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class NodeData implements node_data {
    private static int count = 0;
    private int key;

    HashMap<Integer, node_data> neighbors;
    String data;
    int tag;

    public NodeData() {
        key = count++;
        neighbors = new HashMap<>();
        tag = 0;
    }

    public NodeData(int k){
        this.key = k;
        tag = 0;
        neighbors = new HashMap<>();
    }

    @Override
    public int getKey() {
        return key;
    }

    @Override
    public Collection<node_data> getNi() {
        return neighbors.values();
    }

    @Override
    public boolean hasNi(int key) {
        return neighbors.containsKey(key);
    }

    @Override
    public void addNi(node_data t) {
        neighbors.put(t.getKey(), t);
    }

    @Override
    public void removeNode(node_data node) {

        neighbors.remove(node.getKey());
    }

    @Override
    public String getInfo() {
        return data;
    }

    @Override
    public void setInfo(String s) {
        this.data = s;
    }

    @Override
    public int getTag() {
        return tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }
}
