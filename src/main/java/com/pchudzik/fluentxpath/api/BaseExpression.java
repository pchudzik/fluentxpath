package com.pchudzik.fluentxpath.api;

abstract class BaseExpression implements XPathExpression {
    private final XPathExpression parent;

    private final XPathExpression value;

    public BaseExpression() {
        this(null, XPathExpression.raw(""));
    }

    public BaseExpression(XPathExpression parent, XPathExpression value) {
        this.parent = parent;
        this.value = value;
    }

    @Override
    public String build() {
        String parentResult = parent != null
                ? parent.build()
                : "";
        return parentResult + value.build();
    }

    public String toString() {
        return build();
    }
}
