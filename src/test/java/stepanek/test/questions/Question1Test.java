package stepanek.test.questions;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import stepanek.Question1;
import stepanek.test.utils.GraphTestUtils;

import java.util.ArrayList;
import java.util.Collections;

public class Question1Test {

    Question1 q1;
    GraphTestUtils.SampleGraph graph;
    ArrayList result;

    @BeforeClass
    public void init() {
        q1 = new Question1();
        graph = GraphTestUtils.generateSampleGraph(5, 5);
        result = q1.walkGraph(graph.getRoot());
    }

    @Test
    public void sameAmount() {
        Assert.assertNotNull(result);

        Assert.assertEquals(result.size(), graph.getNodeCount(), "Amount of graph nodes in list is incorrect");

    }

    @Test
    public void noDuplicates() {
        Assert.assertNotNull(result);

        Boolean duplicates = result.stream().anyMatch(e -> Collections.frequency(result, e) > 1);

        Assert.assertFalse(duplicates, "List cannot contain duplicates");

    }

}
