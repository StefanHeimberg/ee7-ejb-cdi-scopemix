package org.example.sharedlib;

import java.util.Random;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
public final class RandomUtil {

    public static String randomId(final Class clazz) {
        final Random r = new Random();
        final int min = 1000;
        final int max = 9999;
        final int id = r.nextInt((max - min) + 1) + min;
        return clazz.getSimpleName() + "-" + id;
    }

    private RandomUtil() {
    }

}
