package stepanek.test.questions;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import stepanek.GNode;
import stepanek.GNodeImpl;
import stepanek.Question2;

import java.util.ArrayList;

public class Question2Test {

    Question2 q2;
    GNode sample;
    ArrayList<ArrayList<GNode>> result;

    @BeforeClass
    public void init() {
        q2 = new Question2();
        sample = new GNodeImpl("A", new GNode[]{
                new GNodeImpl("B", new GNode[]{
                    new GNodeImpl("E"),
                    new GNodeImpl("F")
                }),
                new GNodeImpl("C", new GNode[]{
                    new GNodeImpl("G"),
                    new GNodeImpl("H"),
                    new GNodeImpl("I")
                }),
                new GNodeImpl("D", new GNode[]{
                    new GNodeImpl("J")
                }),
        });
        result = q2.paths(sample);
    }

    @Test
    public void pathCount() {
        Assert.assertEquals(result.size(),6);
    }

    @Test
    public void pathSizes() {
        result.forEach(path -> Assert.assertEquals(path.size(),3));
    }

    @Test
    public void order() {
        // test few select points in the static example
        Assert.assertEquals(result.get(0).get(0).getName(), "A");
        Assert.assertEquals(result.get(3).get(2).getName(), "H");
        Assert.assertEquals(result.get(5).get(1).getName(), "D");
        Assert.assertEquals(result.get(5).get(2).getName(), "J");
    }

}
