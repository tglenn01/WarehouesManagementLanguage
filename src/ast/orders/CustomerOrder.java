package src.ast.orders;

import src.ast.Product;
import src.ast.WarehouseRobotVisitor;

import java.util.HashMap;
import java.util.Map;

// A Customer Order with a number of products and how much they need of each
public class CustomerOrder extends Order {

    public CustomerOrder(Map<Product, Integer> requestedProducts) {
        order = requestedProducts;
    }

    public CustomerOrder() {
        order = new HashMap<>();
    }

    @Override
    public <C, T> T accept(C context, WarehouseRobotVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
