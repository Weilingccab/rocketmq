package weiling.javaDemo.rocketmq.comsumer;

import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
@RocketMQMessageListener(topic = "test3",consumerGroup ="consumer-topic3",messageModel = MessageModel.BROADCASTING)
public class ComsumerTopic3NoTagService implements RocketMQListener<String> {


    @Override
    public void onMessage(String s) {
        System.out.println(Calendar.getInstance().getTimeInMillis()+"ComsumerTopic3NoTagService 收到消息内容 topic test3："+s);

    }
}
