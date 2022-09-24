package cn.jbone.statemachine.example.order.action;

import cn.jbone.statemachine.core.action.StateMachineActionAdapter;
import cn.jbone.statemachine.example.order.OrderEvents;
import cn.jbone.statemachine.example.order.OrderStates;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

@Component
public class DeliverAction extends StateMachineActionAdapter<OrderStates, OrderEvents> {
    @Override
    public Object doExecute(StateContext<OrderStates, OrderEvents> context) {
        System.out.println("【Action】【DeliverAction】" + context.getMessageHeader("order") + "执行发货Action " + context.getEvent());
        throw new RuntimeException("发货失败");
    }
}
