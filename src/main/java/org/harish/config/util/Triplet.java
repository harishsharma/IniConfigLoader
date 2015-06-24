package org.harish.config.util;

/**
 * Data abstraction for holding three values.
 * 
 * @author harish.sharma
 *
 * @param <K>
 * @param <V>
 */
public class Triplet<A, B, C> {

    private final A a;
    private final B b;
    private final C c;

    private Triplet(final A a, final B b, final C c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public static <A, B, C> Triplet<A, B, C> of(final A a, final B b, final C c) {
        return new Triplet<A, B, C>(a, b, c);
    }

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }

    public C getC() {
        return c;
    }

    @Override
    public String toString() {
        return a + " : " + b + " : " + c;
    }
}
