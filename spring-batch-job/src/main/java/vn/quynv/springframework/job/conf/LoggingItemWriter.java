package vn.quynv.springframework.job.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.util.ObjectUtils;
import vn.quynv.springframework.domain.transaction.Transaction;

import java.util.List;

@Slf4j
public class LoggingItemWriter implements ItemWriter<Transaction> {
    @Override
    public void write(List<? extends Transaction> list) throws Exception {
        if(ObjectUtils.isEmpty(list)) {
            log.warn("Input items is empty");
            return;
        }
        list.forEach(trans -> {
            log.info("Writing trans: {}", trans);
        });
    }
}
