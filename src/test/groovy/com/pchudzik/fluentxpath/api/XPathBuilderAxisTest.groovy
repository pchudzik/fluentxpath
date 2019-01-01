package com.pchudzik.fluentxpath.api

import spock.lang.Specification

import static com.pchudzik.fluentxpath.api.XPathBuilder.*
import static com.pchudzik.fluentxpath.api.XPathExpression.xpathValue

class XPathBuilderAxisTest extends Specification {
    def "axis"() {
        expect:
        "child::book" == xpathAxis().axis(xpathOf().element("child"), xpathOf().element("book")).build()
    }

    def "attribute::"() {
        expect:
        "attribute::lang" == xpathAxis().attribute("lang").build()
    }

    def "ancestor-or-self::"() {
        expect:
        "ancestor-or-self::book" == xpathAxis().ancestorOfSelf(xpathOf().element("book")).build()
    }

    def "ancestor::"() {
        expect:
        "ancestor::book" == xpathAxis().ancestor(xpathOf().element("book")).build()
    }

    def "child::text"() {
        expect:
        "child::text()" == xpathAxis().child(xpathFn().text()).build()
    }

    def "child::any"() {
        expect:
        "child::*" == xpathAxis().child(xpathOf().element()).build()
    }

    def "descendant::"() {
        expect:
        "descendant::book" == xpathAxis().descendant(xpathOf().element("book")).build()
    }

    def "descendant-or-self::"() {
        expect:
        "descendant-or-self::book" == xpathAxis().descendantOrSelf(xpathOf().element("book")).build()
    }

    def "following::"() {
        expect:
        "following::item" == xpathAxis().following(xpathOf().element("item")).build()
    }

    def "following-sibling::"() {
        expect:
        "following-sibling::item" == xpathAxis().followingSibling(xpathOf().element("item")).build()
    }

    def "namespace::"() {
        expect:
        "namespace::*" == xpathAxis().namespace(xpathOf().element()).build()
    }

    def "parent::*"() {
        expect:
        "//name/parent::*" == xpathOf()
                .anyElement("name")
                .descendantElement(xpathAxis().parent(xpathOf().element()))
                .build()
    }

    def "parent::element"() {
        expect:
        "//name/parent::employee" == xpathOf()
                .anyElement("name")
                .descendantElement(xpathAxis().parent(xpathOf().element("employee")))
                .build()
    }

    def "preceding::"() {
        expect:
        "//employee[(@id = 3)]/preceding::*" == xpathOf()
                .anyElement("employee")
                .has(xpathFn().eq(xpathOf().attribute("id"), xpathValue(3)))
                .descendantElement(xpathAxis().preceding(xpathOf().element()))
                .build()
    }

    def "preceding-sibling::"() {
        expect:
        "//employee[(@id = 3)]/preceding-sibling::*" == xpathOf()
                .anyElement("employee")
                .has(xpathFn().eq(xpathOf().attribute("id"), xpathValue(3)))
                .descendantElement(xpathAxis().precedingSibling(xpathOf().element()))
                .build()
    }

    def "self::"() {
        expect:
        "//name/self::*" == xpathOf()
                .anyElement("name")
                .descendantElement(xpathAxis().self(xpathOf().element()))
                .build()
    }
}
