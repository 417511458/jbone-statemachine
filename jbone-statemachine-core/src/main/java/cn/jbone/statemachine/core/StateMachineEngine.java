package cn.jbone.statemachine.core;

import cn.jbone.statemachine.core.constants.StateMachineConstants;
import cn.jbone.statemachine.core.result.StateMachineResult;
import cn.jbone.statemachine.core.result.StateMachineResultCode;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachineEventResult;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.Consumer;

/**
 * 状态机引擎，负责驱动状态机的运行
 */
public class StateMachineEngine<S,E> {
    /**
     * 每个状态机引擎，对应一个StateMachineFactory
     */
    private StateMachineFactory<S,E> stateMachineFactory;
    public StateMachineEngine(StateMachineFactory<S,E> stateMachineFactory){
        this.stateMachineFactory = stateMachineFactory;
    }

    /**
     * 向状态机发送事件，驱动状态机运行
     * @param event 事件
     * @param variables 变量
     * @return StateMachineResult
     */
    public StateMachineResult sendEvent(E event, Map<String,Object> variables) {
        return sendEvent(null,event,variables);
    }

    /**
     * 向状态机发送事件，驱动状态机运行
     * @param currentState 状态机的当前状态，如果传入了当前状态，先重置状态机状态再驱动
     * @param event 事件
     * @param variables 变量
     * @return StateMachineResult
     */
    public StateMachineResult sendEvent(S currentState, E event, Map<String,Object> variables)  {

        //1.创建状态机
        StateMachine<S,E> stateMachine = stateMachineFactory.getStateMachine();
        //2.重置状态机状态
        resetState(stateMachine,currentState);
        //3.创建消息 & 向状态机发送Event
        Flux<StateMachineEventResult<S, E>> results = stateMachine.sendEvent(Mono.just(MessageBuilder.createMessage(event,new MessageHeaders(variables))));
        //返回结果
        StateMachineResult stateMachineResult = new StateMachineResult();
        //4.处理结果
        results.subscribe(new Consumer<StateMachineEventResult<S, E>>() {
            @Override
            public void accept(StateMachineEventResult<S, E> stateMachineEventResult) {
                //如果状态机拒绝，将返回结果设置为拒绝
                if(stateMachineEventResult.getResultType() == StateMachineEventResult.ResultType.DENIED){
                    stateMachineResult.setResultCode(StateMachineResultCode.DENIED);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                //当出现异常时，设置错误状态码为ERROR，错误消息为抛出的异常消息
                stateMachineResult.setResultCode(StateMachineResultCode.ERROR);
                stateMachineResult.setMessage(throwable.getMessage());
            }
        });
        //5.处理异常
        //如果有异常才处理
        if(stateMachine.hasStateMachineError()){
            //如果是自定义Action里捕获的异常，则会被添加至ExtendedState里
            Exception exception = (Exception) stateMachine.getExtendedState().getVariables().get(StateMachineConstants.ERROR_KEY);
            if(exception != null){
                //如果异常类型是StateMachineException，则返回明确的错误码和错误消息
                if(exception instanceof StateMachineException){
                    StateMachineException stateMachineException = (StateMachineException)exception;
                    stateMachineResult.setCode(stateMachineException.getCode());
                    stateMachineResult.setMessage(stateMachineException.getMessage());
                }else {
                    //其他异常，将错误码设置为ERROR，将错误消息设置为Exception的message
                    stateMachineResult.setCode(StateMachineResultCode.ERROR.getCode());
                    stateMachineResult.setMessage(exception.getMessage());
                }
            }else {
                //如果ExtendedState里没有异常，此处是获取不到具体的错误信息的
                //因此，将错误码设置为统一的ERROR码
                stateMachineResult.setResultCode(StateMachineResultCode.ERROR);
            }
        }
        //统一约定，如果需要返回值，则在Action里，将返回值添加至ExtendedState。
        stateMachineResult.setData(stateMachine.getExtendedState().getVariables().get(StateMachineConstants.RESULT_KEY));
        return stateMachineResult;
    }

    /**
     * 重置状态机状态
     * @param stateMachine 状态机
     * @param currentState 要重置的目标状态
     */
    private void resetState(StateMachine<S,E> stateMachine,S currentState){
        if(currentState == null){
            return;
        }
        StateMachineContext<S, E> context = new DefaultStateMachineContext<>(currentState, null, null, null, null);
        stateMachine.stopReactively().block();
        stateMachine.getStateMachineAccessor().doWithAllRegions(function -> function.resetStateMachineReactively(context).block());
        stateMachine.startReactively().block();
    }
}
