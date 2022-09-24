package cn.jbone.statemachine.example.order.configuration;

import cn.jbone.statemachine.core.StateMachineEngine;
import cn.jbone.statemachine.example.order.OrderEvents;
import cn.jbone.statemachine.example.order.OrderStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.StateMachineFactory;

@Configuration
public class OrderStateMachineEngineConfig {
    @Autowired
    private StateMachineFactory<OrderStates, OrderEvents> orderStateMachineFactory;
    @Bean
    StateMachineEngine<OrderStates,OrderEvents> stateMachineEngine(){
        return new StateMachineEngine<>(orderStateMachineFactory);
    }
}
