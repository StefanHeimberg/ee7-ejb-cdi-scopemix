package org.example.sharedlib;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Dependent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
@Dependent
public class ExampleDependedBean {

    private final Logger LOG;

    private final String id;

    public ExampleDependedBean() {
        this.id = RandomUtil.randomId(ExampleDependedBean.class);
        LOG = LogManager.getLogger(id);
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

}
