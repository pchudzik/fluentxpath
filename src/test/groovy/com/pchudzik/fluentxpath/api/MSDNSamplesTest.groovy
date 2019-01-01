package com.pchudzik.fluentxpath.api

import spock.lang.Specification

import static com.pchudzik.fluentxpath.api.XPathBuilder.*
import static com.pchudzik.fluentxpath.api.XPathExpression.xpathValue

//https://msdn.microsoft.com/en-us/library/ms256086.aspx
class MSDNSamplesTest extends Specification {
    static final sampleBookStoreXml = """
        <?xml version="1.0"?>
        <bookstore specialty="novel">
          <book style="autobiography">
            <author>
              <first-name>Joe</first-name>
              <last-name>Bob</last-name>
              <award>Trenton Literary Review Honorable Mention</award>
            </author>
            <price>12</price>
          </book>
          <book style="textbook">
            <author>
              <first-name>Mary</first-name>
              <last-name>Bob</last-name>
              <publication>Selected Short Stories of
                <first-name>Mary</first-name>
                <last-name>Bob</last-name>
              </publication>
            </author>
            <editor>
              <first-name>Britney</first-name>
              <last-name>Bob</last-name>
            </editor>
            <price>55</price>
          </book>
          <magazine style="glossy" frequency="monthly">
            <price>2.50</price>
            <subscription price="24" per="year"/>
          </magazine>
          <book style="novel" id="myfave">
            <author>
              <first-name>Toni</first-name>
              <last-name>Bob</last-name>
              <degree from="Trenton U">B.A.</degree>
              <degree from="Harvard">Ph.D.</degree>
              <award>Pulitzer</award>
              <publication>Still in Trenton</publication>
              <publication>Trenton Forever</publication>
            </author>
            <price intl="Canada" exchange="0.7">6.50</price>
            <excerpt>
              <p>It was a dark and stormy night.</p>
              <p>But then all nights in Trenton seem dark and
              stormy to someone who has gone through what
              <emph>I</emph> have.</p>
              <definition-list>
                <term>Trenton</term>
                <definition>misery</definition>
              </definition-list>
            </excerpt>
          </book>
          <my:book xmlns:my="uri:mynamespace" style="leather" price="29.50">
            <my:title>Who's Who in Trenton</my:title>
            <my:author>Robert Bob</my:author>
          </my:book>
        </bookstore>
        """

    def "All <author> elements within the current context"() {
        expect:
        "author" == xpathOf().element("author").build()
    }

    def "All <book> elements whose style attribute value is equal to the specialty attribute value of the <bookstore> element at the root of the document"() {
        expect:
        "//book[(/bookstore/@specialty = @style)]" == xpathOf()
                .anyElement("book")
                .has(
                xpathFn().eq(
                        xpathOf().descendantElement("bookstore").descendantAttribute("specialty"),
                        xpathOf().attribute("style")))
                .build()
    }

    def "All <title> elements one or more levels deep in the <bookstore> element (arbitrary descendants). Note that this is different from the expression in the next row"() {
        expect:
        "bookstore//title" == xpathOf().element("bookstore").anyElement("title").build()
    }

    def "All <title> elements that are grandchildren of <bookstore> elements"() {
        expect:
        "//bookstore/*/title" == xpathOf()
                .anyElement("bookstore")
                .descendantElement()
                .descendantElement("title")
                .build()
    }

    def "All <title> elements one or more levels deep in the current context. Note that this situation is essentially the only one in which the period notation is required"() {
        expect:
        ".//title" == xpathOf()
                .currentContext()
                .anyElement("title")
                .build()
    }

    def "All grandchildren elements of the current context"() {
        expect:
        "*/*" == xpathOf()
                .element()
                .descendantElement()
                .build()
    }

    def "All elements with the specialty attribute"() {
        expect:
        "*[@specialty]" == xpathOf()
                .element()
                .has(xpathOf().attribute("specialty"))
                .build()
    }

    def "The style attribute of the current context"() {
        expect:
        "@style" == xpathOf()
                .attribute("style")
                .build()
    }

    def "Returns an empty node set, because attributes do not contain element children. This expression is allowed by the XML Path Language (XPath) grammar, but is not strictly valid"() {
        expect:
        "price/@exchange/total" == xpathOf()
                .element("price")
                .descendantAttribute("exchange")
                .descendantElement("total")
                .build()
    }

    def "All <book> elements with style attributes, of the current context"() {
        expect:
        "book[@style]" == xpathOf()
                .element("book")
                .has(xpathOf().attribute("style"))
                .build()
    }

    def "The style attribute for all <book> elements of the current context"() {
        expect:
        "book/@style" == xpathOf()
                .element("book")
                .descendantAttribute("style")
                .build()
    }

    def "All attributes of the current element context"() {
        expect:
        "@*" == xpathOf()
                .attribute()
                .build()
    }

    def "The <book> element from the my namespace"() {
        expect:
        "my:book" == xpathOf()
                .element("my:book")
                .build()
    }

    def "All elements from the my namespace"() {
        expect:
        "my:*" == xpathOf()
                .element("my:*")
                .build()
    }

    def "All attributes from the my namespace (this does not include unqualified attributes on elements from the my namespace)"() {
        expect:
        "@my:*" == xpathOf()
                .attribute("my:*")
                .build()
    }

    def "The last <book> element of the current context node"() {
        expect:
        "book[last()]" == xpathOf()
                .element("book")
                .is(xpathFn().last())
                .build()
    }

    def "The last <author> child of each <book> element of the current context node"() {
        expect:
        "book/author[last()]" == xpathOf()
                .element("book")
                .descendantElement("author")
                .is(xpathFn().last())
                .build()
    }

    def "The last <author> element from the entire set of <author> children of <book> elements of the current context node."() {
        expect:
        "(book/author)[last()]" == xpathOf()
                .setOf(xpathOf()
                .element("book")
                .descendantElement("author"))
                .is(xpathFn().last())
                .build()
    }

    def "All <book> elements that contain at least one <excerpt> element child"() {
        expect:
        "book[excerpt]" == xpathOf()
                .element("book")
                .has(xpathOf().element("excerpt"))
                .build()
    }

    def "All <title> elements that are children of <book> elements that also contain at least one <excerpt> element child"() {
        expect:
        "book[excerpt]/title" == xpathOf()
                .element("book")
                .has(xpathOf().element("excerpt"))
                .descendantElement("title")
                .build()
    }

    def "All <author> elements that contain at least one <degree> element child, and that are children of <book> elements that also contain at least one <excerpt> element"() {
        expect:
        "book[excerpt]/author[degree]" == xpathOf()
                .element("book").has(xpathOf().element("excerpt"))
                .descendantElement("author").has(xpathOf().element("degree"))
                .build()
    }

    def "All <book> elements that contain <author> children that in turn contain at least one <degree> child"() {
        expect:
        "book[author/degree]" == xpathOf()
                .element("book")
                .has(xpathOf().element("author").descendantElement("degree"))
                .build()
    }

    def "All <author> elements that contain at least one <degree> element child and at least one <award> element child v1"() {
        expect:
        "author[degree][award]" == xpathOf()
                .element("author")
                .has(xpathOf().element("degree"))
                .has(xpathOf().element("award"))
                .build()
    }

    def "All <author> elements that contain at least one <degree> element child and at least one <award> element child v2"() {
        expect:
        "author[(degree and award)]" == xpathOf()
                .element("author")
                .has(
                xpathFn().and([
                        xpathOf().element("degree"),
                        xpathOf().element("award")]))
                .build()
    }

    def "All <author> elements that contain at least one <degree> or <award> and at least one <publication> as the children"() {
        expect:
        "author[((degree or award) and publication)]" == xpathOf()
                .element("author")
                .has(
                xpathFn().and([
                        xpathFn().or([xpathOf().element("degree"), xpathOf().element("award")]),
                        xpathOf().element("publication")]))
                .build()
    }

    def "All <author> elements that contain at least one <degree> element child and that contain no <publication> element children"() {
        expect:
        "author[(degree and not(publication))]" == xpathOf()
                .element("author")
                .has(
                xpathFn().and([
                        xpathOf().element("degree"),
                        xpathFn().not(xpathOf().element("publication"))]))
                .build()
    }

    def "All <author> elements that contain at least one <publication> element child and contain neither <degree> nor <award> element children"() {
        expect:
        "author[(not((degree or award)) and publication)]" == xpathOf()
                .element("author")
                .has(
                xpathFn().and([
                        xpathFn().not(
                                xpathFn().or([
                                        xpathOf().element("degree"),
                                        xpathOf().element("award"),
                                ])),
                        xpathOf().element("publication")
                ]))
                .build()
    }

    def "All <author> elements that contain at least one <last-name> element child with the value Bob"() {
        expect:
        "author[(last-name = 'Bob')]" == xpathOf()
                .element("author")
                .has(
                xpathFn().eq(
                        xpathOf().element("last-name"),
                        xpathValue("Bob")
                ))
                .build()
    }

    def "All <author> elements where the first <last-name> child element has the value Bob. Note that this is equivalent to the expression in the next row"() {
        expect:
        "author[(last-name[1] = 'Bob')]" == xpathOf()
                .element("author")
                .has(
                xpathFn().eq(
                        xpathOf().element("last-name").atIndex(1),
                        xpathValue("Bob")
                ))
                .build()
    }

    def "All <author> elements where the first <last-name> child element has the value Bob"() {
        expect:
        "//author[(last-name[(position() = 1)] = 'Bob')]" == xpathOf()
                .anyElement("author")
                .has(
                xpathFn().eq(
                        xpathOf().element("last-name").has(xpathFn().eq(xpathFn().position(), xpathValue(1))),
                        xpathValue("Bob")
                ))
                .build()

    }

    def "All <degree> elements where the from attribute is not equal to 'Harvard'"() {
        expect:
        "//degree[(@from != 'Harvard')]" == xpathOf()
                .anyElement("degree")
                .has(
                xpathFn().neq(
                        xpathOf().attribute("from"),
                        xpathValue("Harvard")
                ))
                .build()
    }

    def "All <author> elements whose value is Matthew Bob"() {
        expect:
        "//author[(. = 'Matthew Bob')]" == xpathOf()
                .anyElement("author")
                .has(
                xpathFn().eq(
                        xpathOf().currentContext(),
                        xpathValue("Matthew Bob")
                ))
                .build()
    }

    def "All <author> elements that contain a <last-name> child element whose value is Bob, and a <price> sibling element whose value is greater than 50"() {
        expect:
        "author[((last-name = 'Bob') and (../price > 50))]" == xpathOf()
                .element("author")
                .has(
                xpathFn().and([
                        xpathFn().eq(
                                xpathOf().element("last-name"),
                                xpathValue("Bob")),
                        xpathFn().greaterThan(
                                xpathOf().ancestor().descendantElement("price"),
                                xpathValue(50))]))
                .build()
    }

    def "The first three books (1, 2, 3)"() {
        expect:
        "book[(position() <= 3)]" == xpathOf()
                .element("book")
                .has(
                xpathFn().lessOrEqualThan(
                        xpathFn().position(),
                        xpathValue(3)
                ))
                .build()
    }

    def "All <author> elements that do no contain <last-name> child elements with the value Bob"() {
        expect:
        "//author[not((last-name = 'Bob'))]" == xpathOf()
                .anyElement("author")
                .has(
                xpathFn().not(xpathFn().eq(
                        xpathOf().element("last-name"),
                        xpathValue("Bob")
                )))
                .build()
    }

    def "All <author> elements that have at least one <first-name> child with the value Bob"() {
        expect:
        "//author[(first-name = 'Bob')]" == xpathOf()
                .anyElement("author")
                .has(
                xpathFn().eq(
                        xpathOf().element("first-name"),
                        xpathValue("Bob")
                ))
                .build()
    }

    def "All author elements containing any child element whose value is Bob"() {
        expect:
        "//author[(* = 'Bob')]" == xpathOf()
                .anyElement("author")
                .has(
                xpathFn().eq(
                        xpathOf().element(),
                        xpathValue("Bob")))
                .build()
    }

    def "All <author> elements that has a <last-name> child element with the value Bob and a <first-name> child element with the value Joe"() {
        expect:
        "//author[((last-name = 'Bob') and (first-name = 'Joe'))]" == xpathOf()
                .anyElement("author")
                .has(
                xpathFn().and([
                        xpathFn().eq(
                                xpathOf().element("last-name"),
                                xpathValue("Bob")),
                        xpathFn().eq(
                                xpathOf().element("first-name"),
                                xpathValue("Joe"))]))
                .build()
    }

    def "All <price> elements in the context node which have an intl attribute equal to 'Canada'"() {
        expect:
        "//price[(@intl = 'Canada')]" == xpathOf()
                .anyElement("price")
                .has(
                xpathFn().eq(
                        xpathOf().attribute("intl"),
                        xpathValue("Canada")))
                .build()
    }

    def "The first two <degree> elements that are children of the context node"() {
        expect:
        "degree[(position() < 3)]" == xpathOf()
                .element("degree")
                .has(
                xpathFn().lessThan(
                        xpathFn().position(),
                        xpathValue(3)))
                .build()
    }

    def "he second text node in each <p> element in the context node"() {
        expect:
        "p/text()[2]" == xpathOf()
                .element("p")
                .descendantElement(xpathFn().text())
                .atIndex(2)
                .build()
    }

    def "The nearest <book> ancestor of the context node"() {
        expect:
        "ancestor::book[1]" == xpathAxis().ancestor(xpathOf().element("book").atIndex(1)).build()
    }

    def "The nearest <book> ancestor of the context node and this <book> element has an <author> element as its child"() {
        expect:
        "ancestor::book[author][1]" == xpathAxis().ancestor(
                xpathOf().element("book")
                        .has(xpathOf().element("author"))
                        .atIndex(1))
                .build()
    }

    def "The nearest <author> ancestor in the current context and this <author> element is a child of a <book> element"() {
        expect:
        "ancestor::author[parent::book][1]" == xpathAxis().ancestor(
                xpathOf().element("author")
                        .has(xpathAxis().parent(xpathOf().element("book")))
                        .atIndex(1))
                .build()
    }

    static final indexesRelativeToParent = """
        <x>
          <y/>
          <y/>
        </x>
        <x>
          <y/>
          <y/>
        </x>
        """

    def "The first <y> child of each <x>. This is equivalent to the expression in the next row"() {
        expect:
        "x/y[1]" == xpathOf()
                .element("x")
                .descendantElement("y")
                .atIndex(1)
                .build()
    }

    def "The first <y> child of each <x>"() {
        expect:
        "x/y[(position() = 1)]" == xpathOf()
                .element("x")
                .descendantElement("y")
                .has(
                xpathFn().eq(
                        xpathFn().position(),
                        xpathValue(1)))
                .build()
    }

    def "The first <y> from the entire set of <y> children of <x> elements"() {
        expect:
        "(x/y)[1]" == xpathOf()
                .setOf(
                xpathOf()
                        .element("x")
                        .descendantElement("y"))
                .atIndex(1)
                .build()
    }

    def "The second <y> child of the first <x>"() {
        expect:
        "x[1]/y[2]" == xpathOf()
                .element("x")
                .atIndex(1)
                .descendantElement("y")
                .atIndex(2)
                .build()
    }
}
