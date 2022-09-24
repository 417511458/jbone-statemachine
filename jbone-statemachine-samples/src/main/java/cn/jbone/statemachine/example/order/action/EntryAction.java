package cn.jbone.statemachine.example.order.action;

import cn.jbone.statemachine.core.action.StateMachineAction;
import cn.jbone.statemachine.core.action.StateMachineActionAdapter;
import cn.jbone.statemachine.example.order.OrderEvents;
import cn.jbone.statemachine.example.order.OrderStates;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

@Component
public class EntryAction extends StateMachineActionAdapter<OrderStates, OrderEvents> {
    @Override
    public Object doExecute(StateContext<OrderStates, OrderEvents> context) {
        System.out.println("【Action】【EntryAction】from " + context.getSource().getId() + " to " + context.getTarget().getId());
        return null;
    }
}
