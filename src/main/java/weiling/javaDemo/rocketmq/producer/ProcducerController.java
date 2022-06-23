package weiling.javaDemo.rocketmq.producer;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcducerController {

    @Autowired
    private ProducerService producerService;

    @RequestMapping(value = "/syncSend/{topic}/{tag}/{content}", method = RequestMethod.GET)
    public void syncSend(@PathVariable("topic") String topic, @PathVariable("tag") String tag, @PathVariable("content") String content) throws InterruptedException {

        for (int i = 0; i < 10; i++) {
            StringBuffer con = new StringBuffer(content);
            con.append(i);
            SendResult sendResult = producerService.syncSend(topic, tag, con.toString());
            System.out.printf("Product：发送状态=%s, 存储queue=%s ,i=%s\n", sendResult.getSendStatus(),
                    sendResult.getMessageQueue().getQueueId(), i);
//            System.out.println(Calendar.getInstance().getTime()+"syncSend call success：" + sendResult);
        }
//        System.out.println("3 syncSend ： end");

    }

    @RequestMapping(value = "/asyncSend/{topic}/{tag}/{content}", method = RequestMethod.GET)
    public void asyncSend(@PathVariable("topic") String topic, @PathVariable("tag") String tag, @PathVariable("content") String content) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            StringBuffer con = new StringBuffer(content);
            con.append(i);
            producerService.asyncSend(topic, tag, con.toString(), new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("1 asyncSend call  success：" + sendResult);

                }

                @Override
                public void onException(Throwable throwable) {
                    System.out.println("2 asyncSend call error：" + throwable.getMessage());

                }
            });



        }
        System.out.println("3 asyncSend ： end");


    }

    @RequestMapping(value = "/sendOneWay/{topic}/{tag}/{content}", method = RequestMethod.GET)
    public void sendOneWay(@PathVariable("topic") String topic, @PathVariable("tag") String tag, @PathVariable("content") String content) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            StringBuffer con = new StringBuffer(content);
            con.append(i);
            producerService.sendOneWay(topic, tag, con.toString());
//            System.out.println("sendOneWay call success");

        }
        System.out.println("3 asyncSend ： end");

    }


    @RequestMapping(value = "/syncSendDelay/{topic}/{tag}/{content}", method = RequestMethod.GET)
    public void syncSendDelay(@PathVariable("topic") String topic, @PathVariable("tag") String tag, @PathVariable("content") String content) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            StringBuffer con = new StringBuffer(content);
            con.append(i);
            producerService.syncSendDelay(topic,tag,con.toString(),60000,2);
//            System.out.println("syncSendDelay call success");

        }
        System.out.println("3 syncSendDelay ： end");

    }


    /*發送時，確保相同key的會進同一個queue*/
    @RequestMapping(value = "/syncSendOrderly/{topic}/{tag}/{content}", method = RequestMethod.GET)
    public void syncSendOrderly(@PathVariable("topic") String topic, @PathVariable("tag") String tag, @PathVariable("content") String content) throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            StringBuffer con = new StringBuffer(content);
            con.append(i);
            int key = i % 10;
            SendResult sendResult= producerService.syncSendOrderly(topic,tag,con.toString(),String.valueOf(key));
            System.out.printf("Product：发送状态=%s, 存储queue=%s ,i=%s, key=%s\n", sendResult.getSendStatus(),
                    sendResult.getMessageQueue().getQueueId(), i, key);

        }
        System.out.println("3 syncSendOrderly ： end");

    }


    @RequestMapping(value = "/sendTransactionMessage/{topic}/{tag}/{content}", method = RequestMethod.GET)
    public void sendTransactionMessage(@PathVariable("topic") String topic, @PathVariable("tag") String tag, @PathVariable("content") String content) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            StringBuffer con = new StringBuffer(content);
            con.append(i);
            producerService.sendTransactionMessage(topic, tag, con.toString());
        }
        System.out.println("3 syncSendOrderly ： end");

    }
}
