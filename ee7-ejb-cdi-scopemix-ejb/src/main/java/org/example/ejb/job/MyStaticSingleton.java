package org.example.ejb.job;

import org.example.sharedlib.ExampleRequestBean;
import org.example.sharedlib.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
public final class MyStaticSingleton {

    private static MyStaticSingleton instance;

    public static MyStaticSingleton getInstance() {
        if (null == instance) {
            instance = new MyStaticSingleton();
        }
        return instance;
    }

    private final Logger LOG;

    private final String id;

    private MyStaticSingleton() {
        id = RandomUtil.randomId(MyStaticSingleton.class);
        LOG = LoggerFactory.getLogger(id);
        LOG.info("constructor called");
    }

    public void logIds(final String indent, final String originId, final String callerId) {
        final ExampleRequestBean exampleRequestBean = ExampleRequestBean.getBeanInstance();
        LOG.info("originId: {}, {}caller: {}, calling.class: {}", originId, indent, callerId, exampleRequestBean.getClass());

        LOG.info("originId: {}, {}caller: {}, calling: {}", originId, indent, callerId, exampleRequestBean.getId());
        exampleRequestBean.logIds(indent + "  ", originId, id);
    }


}
