package weiling.javaDemo.rocketmq.comsumer;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/*一個消費者有多個Thread.
多Thread接收消息（ConsumeMode.CONCURRENTLY）
單一Thread接收消息（ConsumeMode.ORDERLY）。*/
@Service
@RocketMQMessageListener(topic = "test2",consumerGroup ="consumer-topic2",consumeMode = ConsumeMode.ORDERLY )
public class ComsumerTopic2NoTagService implements RocketMQListener<String> {


    @Override
    public void onMessage(String s) {
        System.out.println(Calendar.getInstance().getTimeInMillis()+"ComsumerTopic2NoTagService 收到消息内容 topic test2："+s+"-"
                +Thread.currentThread().getName());
//        System.out.println(Calendar.getInstance().getTimeInMillis()+"ComsumerTopic2Tag1Service 收到消息内容 topic test2："+s);


    }
}
