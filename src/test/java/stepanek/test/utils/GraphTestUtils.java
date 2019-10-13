package stepanek.test.utils;

import stepanek.GNode;
import stepanek.GNodeImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class GraphTestUtils {

    private GraphTestUtils() {
    }

    private static Random random = new Random();

    private abstract static class TreeGenStep {
        abstract Stream<TreeGenStep> run();

        abstract boolean isDone();

        static class Done extends TreeGenStep {
            final GNodeImpl node;

            Done(GNodeImpl node) {
                this.node = node;
            }

            @Override
            Stream<TreeGenStep> run() {
                throw new IllegalStateException("Cannot run a done step");
            }

            @Override
            boolean isDone() {
                return true;
            }
        }

        static class Generating extends TreeGenStep {
            final Supplier<Stream<TreeGenStep>> resume;

            Generating(Supplier<Stream<TreeGenStep>> resume) {
                this.resume = resume;
            }

            @Override
            Stream<TreeGenStep> run() {
                return resume.get();
            }

            @Override
            boolean isDone() {
                return false;
            }
        }
    }

    private static class NodeGenerator {
        int counter = 0;
        GNodeImpl getInstance(String name) {
            counter++;
            return new GNodeImpl(name);
        }

    }

    private static Stream<TreeGenStep> genTree(GNodeImpl node, NodeGenerator nodeGen, int maxChildrenPerNode,
                                               int maxDepth, int currentDepth) {

        return Stream.of(currentDepth >= maxDepth ? new TreeGenStep.Done(node) : new TreeGenStep.Generating(() -> {
            int noOfChildren = random.nextInt(maxChildrenPerNode - 1) + 1;
            List<GNodeImpl> children = new ArrayList<>();
            for (int i = 1; i <= noOfChildren; i++) {
                children.add(nodeGen.getInstance(node.getName() + " / " + currentDepth + "_" + i));
            }
            GNodeImpl[] childrenArr = new GNodeImpl[noOfChildren];
            children.toArray(childrenArr);
            node.setChildren(childrenArr);
            return children.stream()
                    .map(child -> genTree(child, nodeGen, maxChildrenPerNode, maxDepth, currentDepth + 1))
                    .flatMap(Function.identity());
        }));
    }

    public static class SampleGraph {

        private GNode root;
        private int nodeCount;

        public SampleGraph(GNode root, int nodeCount) {
            this.root = root;
            this.nodeCount = nodeCount;
        }

        public GNode getRoot() {
            return root;
        }

        public int getNodeCount() {
            return nodeCount;
        }
    }

    public static SampleGraph generateSampleGraph(int depth, int maxChildrenPerNode) {

        final GNodeImpl root = new GNodeImpl("rootElement");

        NodeGenerator nodeGen = new NodeGenerator();

        Stream<TreeGenStep> treeGenerator = genTree(root, nodeGen, maxChildrenPerNode, depth, 0);

        final AtomicInteger counter = new AtomicInteger(1);

        while (counter.get() > 0) {
            counter.set(0);
            treeGenerator = treeGenerator
                    .filter(step -> {
                        counter.incrementAndGet();
                        return !step.isDone();
                    })
                    .map(TreeGenStep::run)
                    .flatMap(Function.identity()).collect(Collectors.toList()).stream();
        }

        return new SampleGraph(root, nodeGen.counter + 1);
    }

}
