package stepanek;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Question2 {

    private static class TraversalState {
        ArrayList path = new ArrayList();
        GNode node;

        TraversalState(ArrayList previousPath, GNode node) {
            this.node = node;

            this.path.addAll(previousPath);
            this.path.add(node);
        }

        TraversalState(GNode node) {
            this.node = node;
            this.path.add(node);
        }
    }

    @NotNull
    public ArrayList paths(@NotNull GNode node) {
        final ArrayList<ArrayList<GNode>> result = new ArrayList<>();

        final Stack<TraversalState> traversalQueue = new Stack<>();
        traversalQueue.push(new TraversalState(node));

        while(traversalQueue.size() > 0) {
            TraversalState currentState = traversalQueue.pop();
            GNode[] children = currentState.node.getChildren();
            if (children.length == 0) {
                result.add(currentState.path);
            } else {
                for (int i = children.length - 1; i >= 0; i--) {
                    traversalQueue.add(new TraversalState(currentState.path, children[i]));
                }
            }
        }

        return result;
    }

}
