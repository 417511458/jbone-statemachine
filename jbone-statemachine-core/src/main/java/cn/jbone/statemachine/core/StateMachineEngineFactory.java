package cn.jbone.statemachine.core;

import org.springframework.statemachine.config.StateMachineFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 状态机引擎工厂
 */
public class StateMachineEngineFactory {
    static Object lock = new Object();
    /**
     * 状态机引擎容器
     */
    static  Map<StateMachineFactory,StateMachineEngine> stateMachineEngineMap = new HashMap<>();
    public static <S,E> StateMachineEngine<S,E> getStateMachineEngine(StateMachineFactory<S,E> stateMachineFactory){
        if(!stateMachineEngineMap.containsKey(stateMachineFactory)){
            synchronized (lock){
                if(!stateMachineEngineMap.containsKey(stateMachineFactory)){
                    StateMachineEngine stateMachineEngine = new StateMachineEngine<>(stateMachineFactory);
                    stateMachineEngineMap.put(stateMachineFactory,stateMachineEngine);
                }
            }
        }

        return stateMachineEngineMap.get(stateMachineFactory);
    }
}
