package cn.jbone.statemachine.example.order.action;

import cn.jbone.statemachine.core.action.StateMachineActionAdapter;
import cn.jbone.statemachine.example.order.OrderEvents;
import cn.jbone.statemachine.example.order.OrderStates;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

@Component
public class PayAction extends StateMachineActionAdapter<OrderStates, OrderEvents> {
    @Override
    public Object doExecute(StateContext<OrderStates, OrderEvents> context) {
        System.out.println("【Action】【PayAction】" + context.getMessageHeader("order") + "执行支付Action " + context.getEvent());
        return null;
    }

    @Override
    public void afterExecute(StateContext<OrderStates, OrderEvents> context) {
        System.out.println("【Action】【PayAction】【afterExecute】通知其他系统支付完成");
    }
}
