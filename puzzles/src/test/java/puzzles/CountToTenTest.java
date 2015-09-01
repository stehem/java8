package puzzles;

import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import puzzles.utils.Tree.Node;
import puzzles.utils.Tree.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;

//https://blog.svpino.com/2015/05/07/five-programming-problems-every-software-engineer-should-be-able-to-solve-in-less-than-1-hour
//this is number 5, the interesting one, brute force, not the Mathy "smart" solution, runs in 0.5 secs
public class CountToTenTest {

    @Test
    public void shouldFindSums() {
        List<List<Integer>> results = getPathsForRootValue(1);
        results.addAll(getPathsForRootValue(12));
        results.addAll(getPathsForRootValue(123));
        assertThat(results, hasItem(asList(1, 2, 34, -5, 67, -8, 9)));
        assertThat(results, hasItem(asList(1, 2, 3, -4, 5, 6, 78, 9)));
        assertThat(results, hasItem(asList(1, 23, -4, 5, 6, 78, -9)));
        assertThat(results, hasItem(asList(1, 23, -4, 56, 7, 8, 9)));
        assertThat(results, hasItem(asList(12, 3, 4, 5, -6, -7, 89)));
        assertThat(results, hasItem(asList(12, 3, -4, 5, 67, 8, 9)));
        assertThat(results, hasItem(asList(12, -3, -4, 5, -6, 7, 89)));
        assertThat(results, hasItem(asList(123, 4, -5, 67, -89)));
        assertThat(results, hasItem(asList(123, -4, -5, -6, -7, 8, -9)));
        assertThat(results, hasItem(asList(123, 45, -67, 8, -9)));
        assertThat(results, hasItem(asList(123, -45, -67, 89)));
    }

    private List<List<Integer>> getPathsForRootValue(final int rootValue) {
        Node<Integer> root = new IntNode(rootValue);
        root.addChildren();
        Tree<Integer> tree = new Tree(root);
        tree.walk(root);
        List<List<Integer>> results = newArrayList();
        for (List<Integer> path : tree.getPaths()) {
            int sum = 0;
            for (Integer i : path) {
                sum += i;
            }
            if (sum == 100) {
                results.add(path);
            }
        }
        return results;
    }


    @Getter
    @RequiredArgsConstructor
    private static class IntNode implements Node<Integer> {
        @NonNull
        private Integer value;
        private List<Node<Integer>> children = newArrayList();

        public void addChildren() {
            if (getValueLastDigit(value).equals(9)) {
                return;
            }
            Set<Integer> values = Sets.newHashSet();
            values.addAll(getNextSequences(getValueLastDigit(value)));
            for (int possibleValue : values) {
                Node<Integer> childPositive = new IntNode(Math.abs(possibleValue));
                Node<Integer> childNegative = new IntNode(Math.abs(possibleValue) * -1);
                children.add(childPositive);
                childPositive.addChildren();
                children.add(childNegative);
                childNegative.addChildren();
            }
        }

        private Integer getValueLastDigit(int value) {
            String asString = Integer.toString(value);
            String lastDigit = newArrayList(asString.split("")).stream().reduce((a, b) -> b).get();
            return Ints.tryParse(lastDigit);
        }

        private List<Integer> getNextSequences(int i) {
            //let's assume it cannot be more than 3 chars
            String max = String.format("%d%d%d", i + 1, i + 2, i + 3);
            String clean = from(Splitter.on("10").splitToList(max)).first().get();
            i = 1;
            List<String> result = new ArrayList<String>();
            while (i <= clean.length()) {
                result.add((String) clean.subSequence(0, i++));
            }
            return result.stream().mapToInt(Ints::tryParse).boxed().collect(Collectors.toList());
        }


    }


}
