package puzzles.utils.Tree;

import java.util.List;

/**
 * Created by user on 9/18/15.
 */
public interface Node<V> {
    public V getValue();
    public List<Node<V>> getChildren();
    public void addChildren();
}
