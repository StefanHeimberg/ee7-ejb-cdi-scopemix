package org.example.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.sharedlib.ExampleDependedBean;
import org.example.sharedlib.ExampleRequestBean;
import org.example.sharedlib.RandomUtil;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
@Stateless
public class ExampleEjbService2 {

    private final Logger LOG;

    private final String id;

    @Inject
    private ExampleRequestBean exampleRequestBean;

    @Inject
    private ExampleDependedBean exampleDependedBean;

    public ExampleEjbService2() {
        id = RandomUtil.randomId(ExampleEjbService2.class);
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

        LOG.info("originId: {}, {}caller: {}, exampleRequestBean.class: {}", originId, indent, callerId, exampleRequestBean.getClass().getName());
        exampleRequestBean.logIds(indent + "  ", originId, id);

        LOG.info("originId: {}, {}caller: {}, exampleDependedBean.class: {}", originId, indent, callerId, exampleDependedBean.getClass().getName());
        exampleDependedBean.logIds(indent + "  ", originId, id);
    }

}
