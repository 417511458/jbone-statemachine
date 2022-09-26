package cn.jbone.statemachine.example.order;

import cn.jbone.statemachine.core.StateMachineEngine;
import cn.jbone.statemachine.core.StateMachineEngineFactory;
import cn.jbone.statemachine.core.result.StateMachineResult;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.config.StateMachineFactory;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private OrderService orderService;
    @Autowired
    private StateMachineEngine<OrderStates,OrderEvents> stateMachineEngine;
    @Autowired
    private StateMachineFactory<OrderStates,OrderEvents> stateMachineFactory;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String orderId = "order:1";
        System.out.println(orderId + "--------------------------------------start");
        createOrder(orderId);
        payOrder(orderId);
        deliverOrder(orderId);
        receiveOrder(orderId);
        evalOrder(orderId);
        System.out.println(orderId + "--------------------------------------end");
    }
    /**
     * 创建订单
     */
    public void createOrder(String orderId){
        Order order = new Order();
        order.setId(orderId);
        order.setState(OrderStates.WAIT_PAY);
        orderService.createOrder(order);
    }

    /**
     * 支付订单，发送OrderEvents.PAY事件
     */
    public void payOrder(String orderId) throws Exception {
        sendEvent(OrderEvents.PAY,orderId);
    }

    /**
     * 发货，发送OrderEvents.DELIVER事件
     * @param orderId
     * @throws Exception
     */
    public void deliverOrder(String orderId) throws Exception {
        sendEvent(OrderEvents.DELIVER,orderId);
    }

    /**
     * 收货，发送OrderEvents.RECEIVE事件
     */
    public void receiveOrder(String orderId) throws Exception {
        sendEvent(OrderEvents.RECEIVE,orderId);
    }

    /**
     * 评价，发送OrderEvents.EVALUATE事件
     * @param orderId
     * @throws Exception
     */
    public void evalOrder(String orderId) throws Exception {
        sendEvent(OrderEvents.EVALUATE,orderId);
    }


    /**
     * 统一的发送事件驱动状态机的方法
     * 1.加载订单
     * 2.传递参数
     * 3.发送事件驱动
     * 4.获取结果
     */
    public StateMachineResult sendEvent(OrderEvents event, String orderId) throws Exception {
        System.out.println("【SendEvent】" + orderId + event + " start ----------");
        //1.加载订单
        Order order = orderService.getOrder(orderId);
        //2.传递参数
        Map<String,Object> variables = new HashMap<>();
        variables.put("order",order);
        StateMachineResult result = StateMachineEngineFactory.getStateMachineEngine(stateMachineFactory).sendEvent(order.getState(),event,variables);
        System.out.println("result:"+ JSON.toJSONString(result));
        System.out.println("【SendEvent】" + orderId + event + " end ----------");
        return result;
    }
}
