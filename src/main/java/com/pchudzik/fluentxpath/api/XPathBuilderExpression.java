package com.pchudzik.fluentxpath.api;


import static com.pchudzik.fluentxpath.api.XPathExpression.raw;

public class XPathBuilderExpression extends BaseExpression {
    protected XPathBuilderExpression() {
        super();
    }

    protected XPathBuilderExpression(XPathExpression parent, XPathExpression value) {
        super(parent, value);
    }

    public XPathBuilderExpression anyElement(String name) {
        return new XPathBuilderExpression(this, raw("//" + name));
    }

    public XPathBuilderExpression ancestor() {
        return new XPathBuilderExpression(this, raw(".."));
    }

    public XPathBuilderExpression descendantElement(XPathExpression expression) {
        return element(new DescendantExpression(expression));

    }

    public XPathBuilderExpression descendantElement() {
        return descendantElement("*");
    }

    public XPathBuilderExpression descendantElement(String name) {
        return descendantElement(raw(name));
    }

    public XPathBuilderExpression element() {
        return element("*");
    }

    public XPathBuilderExpression element(String name) {
        return element(raw(name));
    }

    public XPathBuilderExpression element(XPathExpression expression) {
        return new XPathBuilderExpression(this, expression);
    }

    public XPathBuilderExpression has(XPathExpression function) {
        return new XPathBuilderExpression(this, new HasExpression(function));
    }

    public XPathBuilderExpression is(XPathExpression function) {
        return has(function);
    }

    public XPathBuilderExpression atIndex(int index) {
        return new XPathBuilderExpression(this, raw("[" + index + "]"));
    }

    public XPathBuilderExpression anyAttribute(String name) {
        return new XPathBuilderExpression(this, raw("//@" + name));
    }

    public XPathBuilderExpression descendantAttribute(String name) {
        return new XPathBuilderExpression(this, raw("/@" + name));
    }

    public XPathBuilderExpression attribute() {
        return attribute("*");
    }

    public XPathBuilderExpression attribute(String name) {
        return new XPathBuilderExpression(this, raw("@" + name));
    }

    public XPathBuilderExpression currentContext() {
        return new XPathBuilderExpression(this, raw("."));
    }

    public XPathBuilderExpression setOf(XPathExpression expression) {
        return new XPathBuilderExpression(this, raw("(" + expression.build() + ")"));
    }

    private static class DescendantExpression implements XPathExpression {
        private final XPathExpression expression;

        public DescendantExpression(XPathExpression expression) {
            this.expression = expression;
        }

        @Override
        public String build() {
            return "/" + expression.build();
        }

        @Override
        public String toString() {
            return build();
        }
    }

    private static class HasExpression implements XPathExpression {
        private final XPathExpression value;

        public HasExpression(XPathExpression value) {
            this.value = value;
        }

        @Override
        public String build() {
            return "[" + value.build() + "]";
        }

        @Override
        public String toString() {
            return build();
        }
    }
}
