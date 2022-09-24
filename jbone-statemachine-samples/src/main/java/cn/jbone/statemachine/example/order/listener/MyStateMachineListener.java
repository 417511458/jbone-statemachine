package cn.jbone.statemachine.example.order.listener;

import cn.jbone.statemachine.core.listener.StateMachineListener;
import cn.jbone.statemachine.example.order.Order;
import cn.jbone.statemachine.example.order.OrderEvents;
import cn.jbone.statemachine.example.order.OrderService;
import cn.jbone.statemachine.example.order.OrderStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.state.State;
import org.springframework.stereotype.Component;

@Component
public class MyStateMachineListener extends StateMachineListener<OrderStates, OrderEvents> {
    @Autowired
    OrderService orderService;
    @Override
    public void stateChanged(StateContext<OrderStates, OrderEvents> stateContext, State<OrderStates, OrderEvents> from, State<OrderStates, OrderEvents> to) {
        Order order = (Order) stateContext.getMessageHeader("order");
        order.setState(stateContext.getTarget().getId());
        orderService.updateOrder(order);
    }
}
