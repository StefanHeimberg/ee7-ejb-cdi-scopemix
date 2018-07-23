package org.example.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import org.example.ejb.job.ExampleJob;
import org.example.ejb.job.ExampleJobScheduler;
import org.example.sharedlib.ExampleDependedBean;
import org.example.sharedlib.ExampleRequestBean;
import org.example.sharedlib.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
@Singleton
@Startup
public class ExampleEjbStartup {

    private final Logger LOG;

    private final String id;

    @EJB
    private ExampleEjbService1 exampleService1;

    @EJB
    private ExampleEjbFacade exampleEjbFacade;

    @Inject
    private ExampleRequestBean exampleRequestBean;

    @Inject
    private ExampleDependedBean exampleDependedBean;

    public ExampleEjbStartup() {
        id = RandomUtil.randomId(ExampleEjbStartup.class);
        LOG = LoggerFactory.getLogger(id);
        LOG.info("constructor called");
    }

    @PostConstruct
    public void postConstruct() {
        LOG.info("postConstruct called");
        logIds("", id, id);

        final ExampleRequestBean exampleRequestBeanStatic = ExampleRequestBean.getBeanInstance();
        LOG.info("originId: {}, {}caller: {}, exampleRequestBeanStatic.class: {}", id, "!" + "  ", id, exampleRequestBeanStatic.getClass().getName());
        exampleRequestBeanStatic.logIds("!", id, id);

        final ExampleJob job = ExampleJob.getBeanInstance();

        final ExampleJobScheduler jobScheduler = ExampleJobScheduler.getBeanInstance();
        jobScheduler.schedule(job, false);
        jobScheduler.schedule(job, false);
        jobScheduler.schedule(job, true);
        jobScheduler.schedule(job, false);
    }

    @PreDestroy
    public void preDestroy() {
        LOG.info("preDestroy called");
    }

    public void logIds(final String indent, final String originId, final String callerId) {
        LOG.info("originId: {}, {}caller: {}, calling: {}", originId, indent, callerId, exampleRequestBean.getId());
        exampleRequestBean.logIds(indent + "  ", originId, id);

        LOG.info("originId: {}, {}caller: {}, calling: {}", originId, indent, callerId, exampleService1.getId());
        exampleService1.logIds(indent + "  ", originId, id);

        LOG.info("originId: {}, {}caller: {}, calling: {}", originId, indent, callerId, exampleEjbFacade.getId());
        exampleEjbFacade.logIds(indent + "  ", originId, id);

        LOG.info("originId: {}, {}caller: {}, calling: {}", originId, indent, callerId, exampleDependedBean.getId());
        exampleDependedBean.logIds(indent + "  ", originId, id);
    }

    public String getId() {
        return id;
    }

}
