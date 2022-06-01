package src.ast.arugments;

import src.ast.WarehouseRobotVisitor;

public class Num extends Argument {
    public Integer number;

    public Num(Integer number) {
        this.number = number;
        this.nodeTitle = "Num";
    }

    public static Num add(Num num1, Num num2) {
        return new Num(num1.number + num2.number);
    }

    public static Num subtract(Num num1, Num num2) {
        return new Num(num1.number - num2.number);
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return null;
    }
}
