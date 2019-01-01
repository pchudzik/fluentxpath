package com.pchudzik.fluentxpath.api;

public class XPathBuilder {
    public static XPathBuilderExpression xpathOf() {
        return new XPathBuilderExpression();
    }

    public static XPathBuilderFunction xpathFn() {
        return new XPathBuilderFunction();
    }

    public static XPathBuilderAxis xpathAxis() {
        return new XPathBuilderAxis();
    }

    public static XPathBuilderExpression xpathAttribute(String attribute) {
        return new XPathBuilderExpression(null, XPathExpression.raw("@" + attribute));
    }
}
