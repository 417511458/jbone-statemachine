package cn.jbone.statemachine.core.listener;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

/**
 * 监听器
 * 扩展带StateContext的状态变化接口
 * @param <S> 状态
 * @param <E> 事件
 */
public abstract class StateMachineListener<S,E> extends StateMachineListenerAdapter<S,E> {
    @Override
    public void stateContext(StateContext<S, E> stateContext) {
        if(stateContext.getStage() == StateContext.Stage.STATE_CHANGED){
            stateChanged(stateContext,stateContext.getSource(),stateContext.getTarget());
        }
    }

    public abstract void stateChanged(StateContext<S, E> stateContext,State<S, E> from, State<S, E> to);
}
