package weiling.javaDemo.rocketmq.comsumer;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
@RocketMQMessageListener(topic = "test1",consumerGroup ="consumer-topic1",consumeMode = ConsumeMode.ORDERLY ,selectorType = SelectorType.TAG,
        selectorExpression = "1")
public class ComsumerTopic1Tag1Service implements RocketMQListener<String> {


    @Override
    public void onMessage(String s) {
        System.out.println(Calendar.getInstance().getTimeInMillis()+"ComsumerTopic1Tag1Service 收到消息内容 topic test1："+s);

    }
}
