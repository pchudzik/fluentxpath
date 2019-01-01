package com.pchudzik.fluentxpath.api;

import static com.pchudzik.fluentxpath.api.XPathExpression.raw;

public class XPathBuilderAxis {
    protected XPathBuilderAxis() {
    }

    public XPathExpression axis(XPathExpression leftExpression, XPathExpression rightExpression) {
        return raw(leftExpression.build() + "::" + rightExpression.build());
    }

    public XPathExpression attribute(String attributeName) {
        return axis(raw("attribute"), raw(attributeName));
    }

    public XPathExpression ancestorOfSelf(XPathExpression expression) {
        return axis(raw("ancestor-or-self"), expression);
    }

    public XPathExpression ancestor(XPathExpression expression) {
        return axis(raw("ancestor"), expression);
    }

    public XPathExpression child(XPathExpression expression) {
        return axis(raw("child"), expression);
    }

    public XPathExpression descendant(XPathExpression expression) {
        return axis(raw("descendant"), expression);
    }

    public XPathExpression descendantOrSelf(XPathExpression expression) {
        return axis(raw("descendant-or-self"), expression);
    }

    public XPathExpression following(XPathExpression expression) {
        return axis(raw("following"), expression);
    }

    public XPathExpression followingSibling(XPathExpression expression) {
        return axis(raw("following-sibling"), expression);
    }

    public XPathExpression namespace(XPathExpression expression) {
        return axis(raw("namespace"), expression);
    }

    public XPathExpression parent(XPathExpression expression) {
        return axis(raw("parent"), expression);
    }

    public XPathExpression preceding(XPathExpression expression) {
        return axis(raw("preceding"), expression);
    }

    public XPathExpression precedingSibling(XPathExpression expression) {
        return axis(raw("preceding-sibling"), expression);
    }

    public XPathExpression self(XPathExpression expression) {
        return axis(raw("self"), expression);
    }
}
