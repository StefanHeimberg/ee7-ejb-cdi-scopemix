package org.example.ejb.job;

import java.util.Set;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.example.sharedlib.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
@Dependent
public class ExampleJob {

    public static ExampleJob getBeanInstance() {
        final Logger LOG = LoggerFactory.getLogger(ExampleJob.class);
        try {
            final BeanManager bm = InitialContext.doLookup("java:comp/BeanManager");

            final Class beanType = ExampleJob.class;
            final Set<Bean<?>> beans = bm.getBeans(beanType);
            final Bean<?> bean = bm.resolve(beans);
            final CreationalContext<?> cc = bm.createCreationalContext(bean);
            final ExampleJob beanInstance = (ExampleJob) bm.getReference(bean, beanType, cc);
            return beanInstance;
        } catch (final NamingException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    private final Logger LOG;

    private final String id;

    public ExampleJob() {
        id = RandomUtil.randomId(ExampleJob.class);
        LOG = LoggerFactory.getLogger(id);
        LOG.info("constructor called");
    }

    public void execute(final String originId) {
        LOG.info("execute called");
        MyStaticSingleton.getInstance().logIds("*", originId, id);
    }

    public String getId() {
        return id;
    }

}
