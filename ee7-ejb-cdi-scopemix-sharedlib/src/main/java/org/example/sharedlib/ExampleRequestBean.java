package org.example.sharedlib;

import java.util.Set;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
@RequestScoped
public class ExampleRequestBean {

    public static ExampleRequestBean getBeanInstance() {
        final Logger LOG = LoggerFactory.getLogger(ExampleRequestBean.class);
        try {
            final BeanManager bm = InitialContext.doLookup("java:comp/BeanManager");

            final Class beanType = ExampleRequestBean.class;
            final Set<Bean<?>> beans = bm.getBeans(beanType);
            final Bean<?> bean = bm.resolve(beans);
            final CreationalContext<?> cc = bm.createCreationalContext(bean);
            final ExampleRequestBean beanInstance = (ExampleRequestBean) bm.getReference(bean, beanType, cc);
            return beanInstance;
        } catch (final NamingException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    private final Logger LOG;

    private final String id;

    public ExampleRequestBean() {
        this.id = RandomUtil.randomId(ExampleRequestBean.class);
        LOG = LoggerFactory.getLogger(id);
        LOG.info("constructor called");
    }

    @PostConstruct
    public void postConstruct() {
        LOG.info("postConstruct called");
    }

    @PreDestroy
    public void preDestroy() {
        LOG.info("preDestroy called");
    }

    public void logIds(final String indent, final String originId, final String callerId) {
        LOG.info("originId: {}, {}caller: {}, this.id: {}", originId, indent, callerId, id);
    }

    public String getId() {
        return id;
    }

}
