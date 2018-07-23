package org.example.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.example.sharedlib.ExampleDependedBean;
import org.example.sharedlib.ExampleRequestBean;
import org.example.sharedlib.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
@Stateless
public class ExampleEjbFacade {

    private final Logger LOG;

    private final String id;

    @EJB
    private ExampleEjbService1 exampleService1;

    @EJB
    private ExampleEjbService2 exampleService2;

    // weblogic: funktioniert in kombination mit einem loadOnStartup = 1 Servlet nicht.
    // weblogic: fehler: org.jboss.weld.exceptions.DeploymentException: WELD-001409: Ambiguous dependencies for type ExampleRequestBean with qualifiers @Default
    //                      at injection point [BackedAnnotatedField] @Inject private org.example.ejb.ExampleEjbFacade.exampleRequestBean
    @Inject
    private ExampleRequestBean exampleRequestBean;

    @Inject
    private ExampleDependedBean exampleDependedBean;

    public ExampleEjbFacade() {
        id = RandomUtil.randomId(ExampleEjbFacade.class);
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
        LOG.info("originId: {}, {}caller: {}, calling: {}", originId, indent, callerId, exampleRequestBean.getId());
        exampleRequestBean.logIds(indent + "  ", originId, id);

        LOG.info("originId: {}, {}caller: {}, calling: {}", originId, indent, callerId, exampleService1.getId());
        exampleService1.logIds(indent + "  ", originId, id);

        LOG.info("originId: {}, {}caller: {}, calling: {}", originId, indent, callerId, exampleService2.getId());
        exampleService2.logIds(indent + "  ", originId, id);

        LOG.info("originId: {}, {}caller: {}, calling: {}", originId, indent, callerId, exampleDependedBean.getId());
        exampleDependedBean.logIds(indent + "  ", originId, id);
    }

    public String getId() {
        return id;
    }

}
