package src.ast.arugments;

import src.ast.WarehouseRobotVisitor;

import java.util.Objects;

public class Name extends Argument {
    public String name;

    public Name(String name) {
        this.name = name;
        this.nodeTitle = "Name";
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name1 = (Name) o;
        return Objects.equals(name, name1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
