package org.example.ejb.job;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.example.sharedlib.RandomUtil;
import org.jboss.weld.context.bound.BoundRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
@Dependent
public class ExampleJobScheduler {

    public static ExampleJobScheduler getBeanInstance() {
        final Logger LOG = LoggerFactory.getLogger(ExampleJobScheduler.class);
        try {
            final BeanManager bm = InitialContext.doLookup("java:comp/BeanManager");

            final Class beanType = ExampleJobScheduler.class;
            final Set<Bean<?>> beans = bm.getBeans(beanType);
            final Bean<?> bean = bm.resolve(beans);
            final CreationalContext<?> cc = bm.createCreationalContext(bean);
            final ExampleJobScheduler beanInstance = (ExampleJobScheduler) bm.getReference(bean, beanType, cc);
            return beanInstance;
        } catch (final NamingException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    @Inject
    private BoundRequestContext boundRequestContext;

    private final Logger LOG;

    private final String id;

    final Map<String, Object> requestDataStore = new HashMap<>();

    public ExampleJobScheduler() {
        this.id = RandomUtil.randomId(ExampleJobScheduler.class);
        LOG = LoggerFactory.getLogger(id);
        LOG.info("constructor called");
    }

    public void schedule(final ExampleJob job, final boolean fail) {
        final String jobThreadId = RandomUtil.randomId(Thread.class);
        LOG.info("originId: {}, schedule job: {} with thread: {}", id, job.getId(), jobThreadId);
        final Runnable jobRunnable = () -> {

            // simulate cron. wait 5 seconds
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                LOG.error(ex.getMessage(), ex);
                throw new RuntimeException(ex);
            }

            startRequestScope(id, requestDataStore);

            try {
                LOG.info("originId: {}, start job execution", id);
                job.execute(id);
                LOG.info("originId: {}, stop job execution", id);

                if (fail) {
                    requestDataStore.clear();
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("fail");
                }
            } finally {
                stopRequestScope(id, requestDataStore);
            }
        };
        final Thread jobExecutionThread = new Thread(jobRunnable, jobThreadId);

        LOG.info("originId: {}, starting thread: {}", id, jobThreadId);
        jobExecutionThread.start();
    }

    private void startRequestScope(final String originId, final Map<String, Object> requestDataStore) {
        LOG.info("originId: {}, start request scope", originId, id);
        try {
            boundRequestContext.associate(requestDataStore);
            boundRequestContext.activate();
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    private void stopRequestScope(final String originId, final Map<String, Object> requestDataStore) {
        LOG.info("originId: {}, stop request scope", originId, id);
        try {
            boundRequestContext.invalidate();
            boundRequestContext.deactivate();
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        } finally {
            boundRequestContext.dissociate(requestDataStore);
        }
    }

}
