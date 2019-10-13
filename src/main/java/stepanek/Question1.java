package stepanek;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Question1 {

    @NotNull
    public ArrayList walkGraph(@NotNull GNode node) {

        final ArrayList<GNode> result = new ArrayList<>();

        final Stack<GNode> traversalStack = new Stack<>();
        traversalStack.push(node);

        while(!traversalStack.empty()) {
            GNode currentNode = traversalStack.pop();
            result.add(currentNode);
            traversalStack.addAll(Arrays.asList(currentNode.getChildren()));
        }

        return result;
    }
}
