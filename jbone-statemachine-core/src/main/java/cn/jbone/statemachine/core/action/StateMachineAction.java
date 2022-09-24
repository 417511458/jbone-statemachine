package cn.jbone.statemachine.core.action;

import cn.jbone.statemachine.core.constants.StateMachineConstants;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

/**
 * 状态机的基础Action
 * @param <S> 状态
 * @param <E> 事件
 */
public abstract class StateMachineAction<S,E> implements Action<S,E> {
    @Override
    public void execute(StateContext<S, E> context) {
        try {
            preExecute(context);
            putResultData(context,doExecute(context));
            afterExecute(context);
        } catch (Exception e) {
            //如果有异常，将异常赋值给状态机
            context.getStateMachine().setStateMachineError(e);
            context.getStateMachine().getExtendedState().getVariables().put(StateMachineConstants.ERROR_KEY,e);
            throw e;
        }
    }

    /**
     * 设置结果数据
     * @param context 状态机上下文
     * @param data 结果数据
     */
    public void putResultData(StateContext<S, E> context,Object data){
        context.getStateMachine().getExtendedState().getVariables().put(StateMachineConstants.RESULT_KEY,data);
    }

    /**
     * 执行前的执行逻辑
     * @param context
     */
    public abstract void preExecute(StateContext<S, E> context);

    /**
     * 执行后的执行逻辑
     * @param context
     */
    public abstract void afterExecute(StateContext<S, E> context);

    /**
     * 核心执行逻辑，返回值即为StateMachineResult里的data
     * @param context
     */
    public abstract Object doExecute(StateContext<S, E> context);
}
