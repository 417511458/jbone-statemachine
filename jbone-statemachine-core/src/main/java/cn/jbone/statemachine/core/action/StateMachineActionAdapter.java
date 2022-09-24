package cn.jbone.statemachine.core.action;

import org.springframework.statemachine.StateContext;

/**
 * 状态机Action适配器，用于默认实现preExecute和afterExecute
 *
 * 所有子Action全部集成本类
 * @param <S> 状态
 * @param <E> 事件
 */
public abstract class StateMachineActionAdapter<S,E> extends StateMachineAction<S,E>{
    @Override
    public void preExecute(StateContext<S, E> context) {

    }

    @Override
    public void afterExecute(StateContext<S, E> context) {

    }

}
