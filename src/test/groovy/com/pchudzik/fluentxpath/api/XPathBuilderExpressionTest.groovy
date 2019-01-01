package com.pchudzik.fluentxpath.api

import spock.lang.Specification

import static com.pchudzik.fluentxpath.api.XPathBuilder.*
import static com.pchudzik.fluentxpath.api.XPathExpression.xpathValue

class XPathBuilderExpressionTest extends Specification {
    /*
    <bookstore>
        <book category="cooking">
            <title lang="en">Everyday Italian</title>
            <author>Giada De Laurentiis</author>
            <year>2005</year>
            <price>30.00</price>
        </book>
        <book category="children">
            <title lang="en">Harry Potter</title>
            <author>J K. Rowling</author>
            <year>2005</year>
            <price>29.99</price>
        </book>
        <book category="web">
            <title lang="en">XQuery Kick Start</title>
            <author>James McGovern</author>
            <author>Per Bothner</author>
            <author>Kurt Cagle</author>
            <author>James Linn</author>
            <author>Vaidyanathan Nagarajan</author>
            <year>2003</year>
            <price>49.99</price>
        </book>
        <book category="web" cover="paperback">
            <title lang="en">Learning XML</title>
            <author>Erik T. Ray</author>
            <year>2003</year>
            <price>39.95</price>
        </book>
     </bookstore>
     */

    def "anyElement"() {
        expect:
        "//book" == xpathOf().anyElement("book").build()
    }

    def "element"() {
        expect:
        "/bookstore" == xpathOf().descendantElement("bookstore").build()
    }

    def "has"() {
        expect:
        "//book[(@category = 'children')]" == xpathOf().anyElement("book").has(
                xpathFn().eq(
                        xpathAttribute("category"),
                        xpathValue("children")))
                .build()
    }

    def "atIndex"() {
        expect:
        "//book[2]" == xpathOf().anyElement("book").atIndex(2).build()
    }

    def "gets text node"() {
        expect:
        "//book/author/text()" == xpathOf()
                .anyElement("book")
                .descendantElement("author")
                .descendantElement(xpathFn().text())
                .build()
    }

    def "the second text node in each <p> element in the context node"() {
        expect:
        "/p/text()[2]" == xpathOf().descendantElement("p").descendantElement(xpathFn().text()).atIndex(2).build()
    }

    def "current context element"() {
        expect:
        "book" == xpathOf().element("book").build()
    }

    def "attribute"() {
        expect:
        "@category" == xpathOf().attribute("category").build()
    }

    def "*"() {
        expect:
        "*" == xpathOf().element().build()
    }

    def "/*"() {
        expect:
        "/*" == xpathOf().descendantElement().build()
    }

    def "current context '.'"() {
        expect:
        "." == xpathOf().currentContext().build()
    }

    def "ancestor '..'"() {
        expect:
        ".." == xpathOf().ancestor().build()
    }
}
