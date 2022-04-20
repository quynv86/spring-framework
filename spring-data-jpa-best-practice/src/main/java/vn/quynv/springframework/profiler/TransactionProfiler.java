package vn.quynv.springframework.profiler;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Aspect
@Component
@Slf4j
public class TransactionProfiler extends TransactionSynchronizationAdapter {
    @Before("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void registerTransactionSynchronization() {
        TransactionSynchronizationManager.registerSynchronization(this);
    }

    @Override
    public void beforeCommit(boolean readOnly) {
        log.info("Before Commit (global)...");
        super.beforeCommit(readOnly);
    }

    @Override
    public void beforeCompletion() {
        log.info("Before Completion (global)...");
        super.beforeCompletion();
    }

    @Override
    public void afterCommit() {
        log.info("After Commit (global)...");
        super.afterCommit();
    }

    @Override
    public void afterCompletion(int status) {
        log.info("After Completion (global)...");
        super.afterCompletion(status);
    }
}
