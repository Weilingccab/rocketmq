package weiling.javaDemo.rocketmq.producer;


import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;


@Service
@RocketMQTransactionListener(rocketMQTemplateBeanName = "rocketMQTemplate2")
public class TransactionListener2Impl implements RocketMQLocalTransactionListener {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        // ... local transaction process, return rollback, commit or unknown
        logger.info("[executeLocalTransaction2][执行本地事务，消息：{} arg：{}]", msg, arg);
//        return RocketMQLocalTransactionState.COMMIT;
        return RocketMQLocalTransactionState.UNKNOWN;
//        return  RocketMQLocalTransactionState.ROLLBACK;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        // ... check transaction status and return rollback, commit or unknown
        logger.info("[checkLocalTransaction2][回查消息：{}]", msg);
        return RocketMQLocalTransactionState.COMMIT;
    }

}
