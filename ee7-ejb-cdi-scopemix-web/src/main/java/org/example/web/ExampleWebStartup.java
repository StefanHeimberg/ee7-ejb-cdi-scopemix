package org.example.web;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.ejb.ExampleEjbFacade;
import org.example.ejb.ExampleEjbService1;
import org.example.ejblite.ExampleEjbLiteService;
import org.example.sharedlib.ExampleDependedBean;
import org.example.sharedlib.RandomUtil;

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

    // payara: funktioniert mit loadOnStartup nicht.
    // payara: java.lang.Exception: java.lang.IllegalStateException: ContainerBase.addChild: start: org.apache.catalina.LifecycleException: org.apache.catalina.LifecycleException: org.jboss.weld.context.ContextNotActiveException: WELD-001303: No active contexts for scope type javax.enterprise.context.RequestScoped
    //    @Inject
    //    private ExampleRequestBean exampleRequestBean;
    @Inject
    private ExampleDependedBean exampleDependedBean;

    public ExampleWebStartup() {
        this.id = RandomUtil.randomId(ExampleWebStartup.class);
        LOG = LogManager.getLogger(id);
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
        LOG.info("originId: {}, {}caller: {}, this.id: {}", originId, indent, callerId, id);

//        LOG.info("originId: {}, {}caller: {}, exampleRequestBean.class: {}", originId, indent, callerId, exampleRequestBean.getClass().getName());
//        exampleRequestBean.logIds(indent + "  ", originId, id);

        LOG.info("originId: {}, {}caller: {}, exampleService1.class: {}", originId, indent, callerId, exampleService1.getClass().getName());
        exampleService1.logIds(indent + "  ", originId, id);

        LOG.info("originId: {}, {}caller: {}, exampleService2.class: {}", originId, indent, callerId, exampleEjbFacade.getClass().getName());
        exampleEjbFacade.logIds(indent + "  ", originId, id);

        LOG.info("originId: {}, {}caller: {}, exampleEjbLiteService.class: {}", originId, indent, callerId, exampleEjbLiteService.getClass().getName());
        exampleEjbLiteService.logIds(indent + "  ", originId, id);

        LOG.info("originId: {}, {}caller: {}, exampleDependedBean.class: {}", originId, indent, callerId, exampleDependedBean.getClass().getName());
        exampleDependedBean.logIds(indent + "  ", originId, id);
    }

}
