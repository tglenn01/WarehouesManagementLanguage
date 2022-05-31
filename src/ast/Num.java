package src.ast;

public class Num extends Argument {
    public Integer number;

    public Num(Integer number) {
        this.number = number;
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return null;
    }
}
