package org.example.web;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import org.example.ejb.ExampleEjbFacade;
import org.example.ejb.ExampleEjbService1;
import org.example.ejb.ExampleEjbStartup;
import org.example.ejblite.ExampleEjbLiteService;
import org.example.sharedlib.ExampleDependedBean;
import org.example.sharedlib.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
@WebServlet(urlPatterns = "/startup", loadOnStartup = 1)
public class ExampleWebStartup extends HttpServlet {

    private final Logger LOG;

    private final String id;

    @EJB
    private ExampleEjbService1 exampleService1;

    @EJB
    private ExampleEjbFacade exampleEjbFacade;

    @EJB
    private ExampleEjbLiteService exampleEjbLiteService;

    @EJB
    private ExampleEjbStartup exampleEjbStartup;

    // payara: funktioniert mit loadOnStartup nicht.
    // payara: java.lang.Exception: java.lang.IllegalStateException: ContainerBase.addChild: start: org.apache.catalina.LifecycleException: org.apache.catalina.LifecycleException: org.jboss.weld.context.ContextNotActiveException: WELD-001303: No active contexts for scope type javax.enterprise.context.RequestScoped
    //    @Inject
    //    private ExampleRequestBean exampleRequestBean;
    @Inject
    private ExampleDependedBean exampleDependedBean;

    public ExampleWebStartup() {
        this.id = RandomUtil.randomId(ExampleWebStartup.class);
        LOG = LoggerFactory.getLogger(id);
        LOG.info("constructor called");
    }

    @PostConstruct
    public void postConstruct() {
        LOG.info("postConstruct called");
        logIds("", id, id);
    }

    @PreDestroy
    public void preDestroy() {
        LOG.info("preDestroy called");
    }

    public void logIds(final String indent, final String originId, final String callerId) {
//        LOG.info("originId: {}, {}caller: {}, calling: {}", originId, indent, callerId, exampleRequestBean.getId());
//        exampleRequestBean.logIds(indent + "  ", originId, id);

        LOG.info("originId: {}, {}caller: {}, calling: {}", originId, indent, callerId, exampleService1.getId());
        exampleService1.logIds(indent + "  ", originId, id);

        LOG.info("originId: {}, {}caller: {}, calling: {}", originId, indent, callerId, exampleEjbFacade.getId());
        exampleEjbFacade.logIds(indent + "  ", originId, id);

        LOG.info("originId: {}, {}caller: {}, calling: {}", originId, indent, callerId, exampleEjbLiteService.getId());
        exampleEjbLiteService.logIds(indent + "  ", originId, id);

        LOG.info("originId: {}, {}caller: {}, calling: {}", originId, indent, callerId, exampleEjbStartup.getId());
        exampleEjbStartup.logIds(indent + "  ", originId, id);

        LOG.info("originId: {}, {}caller: {}, calling: {}", originId, indent, callerId, exampleDependedBean.getId());
        exampleDependedBean.logIds(indent + "  ", originId, id);
    }

}
