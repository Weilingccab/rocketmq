package weiling.javaDemo.rocketmq.producer;


import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProducerService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private RocketMQTemplate2 rocketMQTemplate2;

    private String convertDestination(String topic, String tag) {
        return StringUtils.isBlank(tag) ? topic : StringUtils.join(topic, ":", tag);
    }

    /**
     * 同步-普通-傳送
     */
    public SendResult syncSend(String topic, String tag, String content) {
        String destination = this.convertDestination(topic, tag);
        return rocketMQTemplate.syncSend(destination, content);
    }

    /**
     * 非同步-普通
     */
    public void asyncSend(String topic, String tag, String content, SendCallback sendCallback) {
        String destination = this.convertDestination(topic, tag);
        rocketMQTemplate.asyncSend(destination, content, sendCallback);
    }

    /**
     * 單向-普通
     */
    public void sendOneWay(String topic, String tag, String content) {
        String destination = this.convertDestination(topic, tag);
        rocketMQTemplate.sendOneWay(destination, content);
    }

    /**
     * 同步批量-普通
     */
    public SendResult syncBatchSend(String topic, String tag, List<String> contentList) {
        String destination = this.convertDestination(topic, tag);
        List<Message<String>> messageList = contentList.stream()
                .map(content -> MessageBuilder.withPayload(content).build())
                .collect(Collectors.toList());
        return rocketMQTemplate.syncSend(destination, messageList);
    }


    /**
     * 同步-延遲
     *
     * @param delayLevel 延時等級：現在RocketMq並不支援任意時間的延時，需要設定幾個固定的延時等級，從1s到2h分別對應著等級 1 到 18
     *                   1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     */
    public SendResult syncSendDelay(String topic, String tag, String content, long timeout, int delayLevel) {
        String destination = this.convertDestination(topic, tag);
        Message message = MessageBuilder.withPayload(content).build();
        return rocketMQTemplate.syncSend(destination, message, timeout, delayLevel);
    }

    /**
     * 同步-順序
     *
     * @param hashKey 根據 hashKey 和 佇列size() 取模，保證同一 hashKey 的訊息發往同一個佇列，以實現 同一hashKey下的訊息 順序傳送
     *                因此 hashKey 建議取 業務上唯一識別符號，如：訂單號，只需保證同一訂單號下的訊息順序傳送
     */
    public SendResult syncSendOrderly(String topic, String tag, String content, String hashKey) {
        String destination = this.convertDestination(topic, tag);
        Message message = MessageBuilder.withPayload(content).build();
        return rocketMQTemplate.syncSendOrderly(destination, message, hashKey);
    }

    /**
     * 發送事務消息
     */
    public void sendTransactionMessage(String topic, String tag, String content) {
        String destination = this.convertDestination(topic, tag);
        Message msg = MessageBuilder.withPayload(content).build();
        if (topic.equals("test1"))
            rocketMQTemplate.sendMessageInTransaction(destination, msg, null);
        else
            rocketMQTemplate2.sendMessageInTransaction(destination, msg, null);

    }


}
