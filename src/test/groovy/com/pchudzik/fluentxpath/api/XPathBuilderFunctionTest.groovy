package com.pchudzik.fluentxpath.api

import spock.lang.Specification

import static com.pchudzik.fluentxpath.api.XPathBuilder.xpathFn
import static com.pchudzik.fluentxpath.api.XPathBuilder.xpathOf
import static com.pchudzik.fluentxpath.api.XPathExpression.xpathValue

class XPathBuilderFunctionTest extends Specification {
    def "and operator"() {
        expect:
        "((price > 9.0) and (price < 9.9))" == xpathFn().and([
                xpathFn().greaterThan(xpathOf().element("price"), xpathValue(9.0)),
                xpathFn().lessThan(xpathOf().element("price"), xpathValue(9.9))])
                .build()
    }

    def "or operator"() {
        expect:
        "((price = 9.8) or (price = 9.7))" == xpathFn().or([
                xpathFn().eq(xpathOf().element("price"), xpathValue(9.8)),
                xpathFn().eq(xpathOf().element("price"), xpathValue(9.7))])
                .build()
    }

    def "nodes union"() {
        expect:
        "(//book | //cd)" == xpathFn().union([
                xpathOf().anyElement("book"),
                xpathOf().anyElement("cd")])
                .build()
    }

    def "addition"() {
        expect:
        "(6 + 4)" == xpathFn().add([xpathValue(6), xpathValue(4)]).build()
    }

    def "subtraction"() {
        expect:
        "(6 - 4)" == xpathFn().sub([xpathValue(6), xpathValue(4)]).build()
    }

    def "multiplication"() {
        expect:
        "(6 * 4)" == xpathFn().mul([xpathValue(6), xpathValue(4)]).build()
    }

    def "division"() {
        expect:
        "(8 div 4)" == xpathFn().div([xpathValue(8), xpathValue(4)]).build()
    }

    def "equal"() {
        expect:
        "(price = 9.99)" == xpathFn().eq(xpathOf().element("price"), xpathValue(9.99)).build()
    }

    def "not equal"() {
        expect:
        "(price != 9.88)" == xpathFn().neq(xpathOf().element("price"), xpathValue(9.88)).build()
    }

    def "less than"() {
        expect:
        "(price < 9.8)" == xpathFn().lessThan(xpathOf().element("price"), xpathValue(9.8)).build()
    }

    def "less or equal than"() {
        expect:
        "(price <= 9.8)" == xpathFn().lessOrEqualThan(xpathOf().element("price"), xpathValue(9.8)).build()
    }

    def "greater than"() {
        expect:
        "(price > 9.8)" == xpathFn().greaterThan(xpathOf().element("price"), xpathValue(9.8)).build()
    }

    def "greater or equal than"() {
        expect:
        "(price >= 9.8)" == xpathFn().greaterOrEqualThan(xpathOf().element("price"), xpathValue(9.8)).build()
    }

    def "mod"() {
        expect:
        "(5 mod 2)" == xpathFn().mod(xpathValue(5), xpathValue(2)).build()
    }

    def "node-name"() {
        expect:
        "node-name(//@noNSAttr)" == xpathFn().nodeName(xpathOf().anyAttribute("noNSAttr")).build()
    }

    def "nilled"() {
        expect:
        "nilled(//child[2])" == xpathFn().nilled(xpathOf().anyElement("child").atIndex(2)).build()
    }

    def "data"() {
        expect:
        "data(//product[1]/colorChoices)" == xpathFn().data(
                xpathOf()
                        .anyElement("product")
                        .atIndex(1)
                        .descendantElement("colorChoices"))
                .build()
    }

    def "base-uri"() {
        expect:
        "base-uri(//catalog[2]/product/@href)" == xpathFn().baseUri(
                xpathOf()
                        .anyElement("catalog").atIndex(2)
                        .descendantElement("product")
                        .descendantAttribute("href"))
                .build()
    }

    def "base-uri null"() {
        expect:
        "base-uri()" == xpathFn().baseUri().build()
    }

    def "document-uri null"() {
        expect:
        "document-uri()" == xpathFn().documentUri().build()
    }

    def "document-uri"() {
        expect:
        "document-uri(doc('http://www.functx.com/input/order.xml'))" == xpathFn().documentUri(
                xpathFn().doc(xpathValue("http://www.functx.com/input/order.xml")))
                .build()
    }

    def "number"() {
        expect:
        "number('100')" == xpathFn().number(xpathValue("100")).build()
    }

    def "abs"() {
        expect:
        "abs(11)" == xpathFn().abs(xpathValue(11)).build()
    }

    def "ceiling"() {
        expect:
        "ceiling(3.14)" == xpathFn().ceiling(xpathValue(3.14)).build()
    }

    def "floor"() {
        expect:
        "floor(3.14)" == xpathFn().floor(xpathValue(3.14)).build()
    }

    def "round"() {
        expect:
        "round(3.14)" == xpathFn().round(xpathValue(3.14)).build()
    }

    def "round-half-to-even"() {
        expect:
        "round-half-to-even(1.5)" == xpathFn().roundHalfToEven(xpathValue(1.5)).build()
    }

    def "position"() {
        expect:
        "//book[(position() <= 3)]" == xpathOf()
                .anyElement("book")
                .has(xpathFn().lessOrEqualThan(
                xpathFn().position(),
                xpathValue(3)))
                .build()
    }

    def "last"() {
        expect:
        "//book[last()]" == xpathOf().anyElement("book").has(xpathFn().last()).build()
    }

    def "current-dateTime"() {
        expect:
        "current-dateTime()" == xpathFn().currentDateTime().build()
    }

    def "current-date"() {
        expect:
        "current-date()" == xpathFn().currentDate().build()
    }

    def "current-time"() {
        expect:
        "current-time()" == xpathFn().currentTime().build()
    }

    def "implicit-timezone"() {
        expect:
        "implicit-timezone()" == xpathFn().implicitTimezone().build()
    }

    def "default-collation"() {
        expect:
        "default-collation()" == xpathFn().defaultCollation().build()
    }

    def "static-base-uri"() {
        expect:
        "static-base-uri()" == xpathFn().staticBaseUri().build()
    }

    def "not"() {
        expect:
        "not(author)" == xpathFn().not(xpathOf().element("author")).build()
    }

    def "boolean"() {
        expect:
        "boolean(author)" == xpathFn().toBoolean(xpathOf().element("author")).build()
    }

    def "true"() {
        expect:
        "true()" == xpathFn().trueValue().build()
    }

    def "false"() {
        expect:
        "false()" == xpathFn().falseValue().build()
    }

    def "string"() {
        expect:
        "string(123)" == xpathFn().string(xpathValue(123)).build()
    }

    def "codepoints-to-string"() {
        expect:
        "codepoints-to-string((84, 104, 233, 114, 232, 115, 101))" == xpathFn()
                .codepointsToString([84, 104, 233, 114, 232, 115, 101].collect { xpathValue(it) })
                .build()
    }

    def "string-to-codepoints"() {
        expect:
        "string-to-codepoints('Thérèse')" == xpathFn().stringToCodepoints(xpathValue("Thérèse")).build()
    }

    def "codepoint-equalr"() {
        expect:
        "codepoint-equal('abc', 'abc')" == xpathFn().codepointEqual(xpathValue("abc"), xpathValue("abc")).build()
    }

    def "compare"() {
        expect:
        "compare('Strasse', 'Straße', 'http://datypic.com/german')" == xpathFn()
                .compare(
                xpathValue("Strasse"),
                xpathValue("Straße"),
                xpathValue("http://datypic.com/german"))
                .build()

        and:
        "compare(upper-case('a'), upper-case('B'))" == xpathFn()
                .compare(xpathFn().upperCase(xpathValue("a")), xpathFn().upperCase(xpathValue("B")))
                .build()
    }

    def "concat"() {
        expect:
        "concat('XPath ', 'is ', 'FUN!')" == xpathFn()
                .concat(["XPath ", "is ", "FUN!"].collect { xpathValue(it) })
                .build()
    }

    def "string-join"() {
        expect:
        "string-join(('We', 'are', 'having', 'fun!'), ' ')" == xpathFn()
                .stringJoin(xpathValue(" "), ["We", "are", "having", "fun!"].collect { xpathValue(it) })
                .build()

        and:
        "string-join(('We', 'are', 'having', 'fun!'))" == xpathFn()
                .stringJoin(null, ["We", "are", "having", "fun!"].collect { xpathValue(it) })
                .build()

        and:
        "string-join((), 'sep')" == xpathFn().stringJoin(xpathValue("sep"), []).build()
    }

    def "string-length"() {
        expect:
        "string-length('Beatles')" == xpathFn().stringLength(xpathValue("Beatles")).build()

        and:
        "string-length()" == xpathFn().stringLength().build()
    }

    def "normalize-space"() {
        expect:
        "normalize-space(' The   XML ')" == xpathFn().normalizeSpace(xpathValue(" The   XML ")).build()

        and:
        "normalize-space()" == xpathFn().normalizeSpace().build()
    }

    def "upper-case"() {
        expect:
        "upper-case('The XML')" == xpathFn().upperCase(xpathValue("The XML")).build()
    }

    def "lower-case"() {
        expect:
        "lower-case('The XML')" == xpathFn().lowerCase(xpathValue("The XML")).build()
    }

    def "translate"() {
        expect:
        "translate('12:30', '0123', 'abcd')" == xpathFn().translate(
                xpathValue("12:30"),
                xpathValue("0123"),
                xpathValue("abcd")
        ).build()
    }

    def "escape-uri"() {
        expect:
        "escape-uri('http://example.com/test#car', true())" == xpathFn()
                .escapeUri(xpathValue("http://example.com/test#car"), xpathFn().trueValue())
                .build()

        and:
        "escape-uri('http://example.com/~bébé', false())" == xpathFn()
                .escapeUri(xpathValue("http://example.com/~bébé"), xpathFn().falseValue())
                .build()
    }

    def "contains"() {
        expect:
        "contains('XML', 'XM')" == xpathFn().contains(xpathValue("XML"), xpathValue("XM")).build()
    }

    def "starts-with"() {
        expect:
        "starts-with('XML', 'XM')" == xpathFn().startsWith(xpathValue("XML"), xpathValue("XM")).build()
    }

    def "ends-with"() {
        expect:
        "ends-with('XML', 'ML')" == xpathFn().endsWith(xpathValue("XML"), xpathValue("ML")).build()
    }

    def "substring-before"() {
        expect:
        "substring-before('12/10', '/')" == xpathFn().substringBefore(xpathValue("12/10"), xpathValue("/")).build()
    }

    def "substring-after"() {
        expect:
        "substring-after('12/10', '/')" == xpathFn().substringAfter(xpathValue("12/10"), xpathValue("/")).build()
    }

    def "matches"() {
        expect:
        "matches('Merano', 'ran')" == xpathFn().matches(xpathValue("Merano"), xpathValue("ran")).build()
    }

    def "replace"() {
        expect:
        "replace('Bella Italia', 'l', '*')" == xpathFn().replace(xpathValue("Bella Italia"), xpathValue("l"), xpathValue("*")).build()
    }

    def "tokenize"() {
        expect:
        "tokenize('XPath is fun', '\\s+')" == xpathFn().tokenize(xpathValue("XPath is fun"), xpathValue("\\s+")).build()
    }

    def "index-of"() {
        expect:
        "index-of((15, 40, 25, 40, 10), 40)" == xpathFn().indexOf(
                [xpathValue(15), xpathValue(40), xpathValue(25), xpathValue(40), xpathValue(10)],
                xpathValue(40)
        ).build()

        and:
        "index-of(('a', 'dog', 'and', 'a', 'duck'), 'a')" == xpathFn().indexOf(
                [xpathValue("a"), xpathValue("dog"), xpathValue("and"), xpathValue("a"), xpathValue("duck")],
                xpathValue("a")
        ).build()
    }

    def "remove"() {
        expect:
        "remove(('ab', 'cd', 'ef'), 0)" == xpathFn().remove(
                [xpathValue("ab"), xpathValue("cd"), xpathValue("ef")],
                xpathValue(0)
        ).build()

        and:
        "remove(('ab', 'cd', 'ef'), 4)" == xpathFn().remove(
                [xpathValue("ab"), xpathValue("cd"), xpathValue("ef")],
                xpathValue(4)
        ).build()
    }

    def "empty"() {
        expect:
        "empty(remove(('ab', 'cd'), 1))" == xpathFn().empty(xpathFn().remove(
                [xpathValue("ab"), xpathValue("cd")],
                xpathValue(1)
        )).build()
    }

    def "exists"() {
        expect:
        "exists(remove(('ab'), 1))" == xpathFn().exists(xpathFn().remove(
                [xpathValue("ab")], xpathValue(1)
        )).build()
    }

    def "distinct-values"() {
        expect:
        "distinct-values((1, 2, 3, 1, 2))" == xpathFn().distinctValues([
                xpathValue(1), xpathValue(2), xpathValue(3), xpathValue(1), xpathValue(2)])
                .build()
    }

    def "insert-before"() {
        expect:
        "insert-before(('ab', 'cd'), 0, 'gh')" == xpathFn().insertBefore(
                [xpathValue("ab"), xpathValue("cd")],
                xpathValue(0),
                xpathValue("gh")
        ).build()

        and:
        "insert-before(('ab', 'cd'), 5, 'gh')" == xpathFn().insertBefore(
                [xpathValue("ab"), xpathValue("cd")],
                xpathValue(5),
                xpathValue("gh")
        ).build()
    }

    def "reverse"() {
        expect:
        "reverse(('ab', 'cd', 'ef'))" == xpathFn().reverse(
                [xpathValue("ab"), xpathValue("cd"), xpathValue("ef")]
        ).build()
    }

    def "subsequence"() {
        expect:
        "subsequence(('a', 'b', 'c', 'd'), 2)" == xpathFn().subsequence(
                [xpathValue("a"), xpathValue("b"), xpathValue("c"), xpathValue("d")],
                xpathValue(2)
        ).build()

        and:
        "subsequence(('a', 'b', 'c', 'd'), 2, 2)" == xpathFn().subsequence(
                [xpathValue("a"), xpathValue("b"), xpathValue("c"), xpathValue("d")],
                xpathValue(2),
                xpathValue(2)
        ).build()
    }

    def "unordered"() {
        expect:
        "unordered((1, 2, 3))" == xpathFn().unordered([xpathValue(1), xpathValue(2), xpathValue(3)]).build()
    }
}