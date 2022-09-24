package cn.jbone.statemachine.example.order.action;

import cn.jbone.statemachine.core.action.StateMachineAction;
import cn.jbone.statemachine.core.action.StateMachineActionAdapter;
import cn.jbone.statemachine.example.order.OrderEvents;
import cn.jbone.statemachine.example.order.OrderStates;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

@Component
public class EvalAction extends StateMachineActionAdapter<OrderStates, OrderEvents> {
    @Override
    public Object doExecute(StateContext<OrderStates, OrderEvents> context) {
        System.out.println("【Action】【EvalAction】" + context.getMessageHeader("order") + "执行评价Action " + context.getEvent());
        return null;
    }
}
