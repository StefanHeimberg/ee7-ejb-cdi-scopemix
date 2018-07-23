package org.example.web;

import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.example.ejb.ExampleEjbFacade;
import org.example.ejb.ExampleEjbService1;
import org.example.ejb.ExampleEjbStartup;
import org.example.ejblite.ExampleEjbLiteService;
import org.example.sharedlib.ExampleDependedBean;
import org.example.sharedlib.ExampleRequestBean;
import org.example.sharedlib.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
@WebServlet(urlPatterns = "/request")
//@RequestScoped kann nicht verwendet werden da es sonst zur exception kommt:
//java.lang.IllegalArgumentException: The class org.example.web.ExampleWebRequest is annotated with an invalid scope
// und ohne @RequestScoped wird dieselbe instanz des ExampleWebRequest über mehrere request/threads verwendet.
public class ExampleWebRequest extends HttpServlet {

    private final Logger LOG;

    private final String id;

    @EJB
    private ExampleEjbService1 exampleService1;

    @EJB
    private ExampleEjbFacade exampleEjbFacade;

    @EJB
    private ExampleEjbStartup exampleEjbStartup;

    @EJB
    private ExampleEjbLiteService exampleEjbLiteService;

    @Inject
    private ExampleRequestBean exampleRequestBean;

    @Inject
    private ExampleDependedBean exampleDependedBean;

    public ExampleWebRequest() {
        this.id = RandomUtil.randomId(ExampleWebRequest.class);
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logIds("", id, id);
    }

    public void logIds(final String indent, final String originId, final String callerId) {
        LOG.info("originId: {}, {}caller: {}, calling: {}", originId, indent, callerId, exampleRequestBean.getId());
        exampleRequestBean.logIds(indent + "  ", originId, id);

        LOG.info("originId: {}, {}caller: {}, calling: {}", originId, indent, callerId, exampleService1.getId());
        exampleService1.logIds(indent + "  ", originId, id);

        LOG.info("originId: {}, {}caller: {}, calling: {}", originId, indent, callerId, exampleEjbFacade.getId());
        exampleEjbFacade.logIds(indent + "  ", originId, id);

        LOG.info("originId: {}, {}caller: {}, calling: {}", originId, indent, callerId, exampleEjbStartup.getId());
        exampleEjbStartup.logIds(indent + "  ", originId, id);

        LOG.info("originId: {}, {}caller: {}, calling: {}", originId, indent, callerId, exampleEjbLiteService.getId());
        exampleEjbLiteService.logIds(indent + "  ", originId, id);

        LOG.info("originId: {}, {}caller: {}, calling: {}", originId, indent, callerId, exampleDependedBean.getId());
        exampleDependedBean.logIds(indent + "  ", originId, id);
    }

}
