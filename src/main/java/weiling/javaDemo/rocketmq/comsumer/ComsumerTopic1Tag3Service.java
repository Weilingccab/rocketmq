package weiling.javaDemo.rocketmq.comsumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
@RocketMQMessageListener(topic = "test1",consumerGroup ="consumer-topic1",selectorType = SelectorType.TAG,
        selectorExpression = "1  || 3")
public class ComsumerTopic1Tag3Service implements RocketMQListener<String> {


    @Override
    public void onMessage(String s) {
        System.out.println(Calendar.getInstance().getTimeInMillis()+"ComsumerTopic1Tag3Service 收到消息内容 topic test1："+s);

    }
}
