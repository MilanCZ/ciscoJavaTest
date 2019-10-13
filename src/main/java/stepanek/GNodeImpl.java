package stepanek;

import org.jetbrains.annotations.NotNull;

public class GNodeImpl implements GNode {

    private String name;

    private GNode[] children;

    public GNodeImpl(@NotNull String name) {
        this.name = name;
        this.children = new GNode[0];
    }

    public void setChildren(@NotNull GNode[] children) {
        this.children = children;
    }

    public GNodeImpl(@NotNull String name, @NotNull GNode[] children) {
        this.name = name;
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public GNode[] getChildren() {
        return children;
    }
}
