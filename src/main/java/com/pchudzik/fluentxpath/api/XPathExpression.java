package com.pchudzik.fluentxpath.api;

@FunctionalInterface
public interface XPathExpression {

    static XPathExpression raw(String val) {
        return () -> val;
    }

    static XPathExpression xpathValue(Number value) {
        return raw(value.toString());
    }

    static XPathExpression xpathValue(String value) {
        return raw("'" + value + "'");
    }

    String build();
}
