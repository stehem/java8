package puzzles.utils.Tree;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Created by user on 9/18/15.
 */
@Getter
@RequiredArgsConstructor
public class Tree<V> {

    @NonNull
    private Node<V> root;
    private List<List<V>> paths = newArrayList();


    public void walk(Node<V> root) {
        walk(root, newArrayList());
    }

    private void walk(Node<V> node, List<V> path) {
        path.add(node.getValue());
        if (node.getChildren().isEmpty()) {
            paths.add(path);
            return;
        }
        for (Node<V> child : node.getChildren()) {
            walk(child, newArrayList(path));
        }
    }
}
