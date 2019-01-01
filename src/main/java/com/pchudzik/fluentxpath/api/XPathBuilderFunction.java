package com.pchudzik.fluentxpath.api;

import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.pchudzik.fluentxpath.api.XPathExpression.raw;
import static com.pchudzik.fluentxpath.api.XPathExpression.xpathValue;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singleton;
import static java.util.stream.Collectors.toList;

public class XPathBuilderFunction extends BaseExpression {
    protected XPathBuilderFunction() {
    }

    protected XPathBuilderFunction(XPathExpression parent, XPathExpression value) {
        super(parent, value);
    }

    public XPathExpression union(Collection<XPathExpression> expressions) {
        return new MultipicableExpression(expressions, raw("|"));
    }

    public XPathExpression or(Collection<XPathExpression> expressions) {
        return new MultipicableExpression(expressions, raw("or"));
    }

    public XPathExpression and(Collection<XPathExpression> expressions) {
        return new MultipicableExpression(expressions, raw("and"));
    }

    public XPathExpression eq(XPathExpression left, XPathExpression right) {
        return new XPathOperatorExpression(left, raw("="), right);
    }

    public XPathExpression neq(XPathBuilderExpression left, XPathExpression right) {
        return new XPathOperatorExpression(left, raw("!="), right);
    }

    public XPathExpression greaterThan(XPathExpression expression, XPathExpression value) {
        return new XPathOperatorExpression(expression, raw(">"), value);
    }

    public XPathExpression greaterOrEqualThan(XPathExpression left, XPathExpression right) {
        return new XPathOperatorExpression(left, raw(">="), right);
    }

    public XPathExpression lessThan(XPathExpression expression, XPathExpression value) {
        return new XPathOperatorExpression(expression, raw("<"), value);
    }

    public XPathExpression lessOrEqualThan(XPathExpression left, XPathExpression right) {
        return new XPathOperatorExpression(left, raw("<="), right);
    }

    public XPathExpression add(Collection<XPathExpression> expressions) {
        return new MultipicableExpression(expressions, raw("+"));
    }

    public XPathExpression sub(Collection<XPathExpression> expressions) {
        return new MultipicableExpression(expressions, raw("-"));
    }

    public XPathExpression mul(Collection<XPathExpression> expressions) {
        return new MultipicableExpression(expressions, raw("*"));
    }

    public XPathExpression div(Collection<XPathExpression> expressions) {
        return new MultipicableExpression(expressions, raw("div"));
    }

    public XPathExpression mod(XPathExpression left, XPathExpression right) {
        return new XPathOperatorExpression(left, raw("mod"), right);
    }

    public XPathExpression number(XPathExpression expression) {
        return new XPathFunction("number", expression);
    }

    public XPathExpression text() {
        return raw("text()");
    }

    public XPathExpression nodeName(XPathExpression expression) {
        return new XPathFunction("node-name", expression);
    }

    public XPathExpression nilled(XPathExpression expression) {
        return new XPathFunction("nilled", expression);
    }

    public XPathExpression data(XPathExpression expression) {
        return new XPathFunction("data", expression);
    }

    public XPathExpression baseUri(XPathExpression expression) {
        final String baseUri = "base-uri";
        return expression == null
                ? new XPathFunction(baseUri, emptyList())
                : new XPathFunction(baseUri, expression);
    }

    public XPathExpression documentUri(XPathExpression expression) {
        final String documentUri = "document-uri";
        return expression == null
                ? new XPathFunction(documentUri, emptyList())
                : new XPathFunction(documentUri, expression);
    }

    public XPathExpression doc(XPathExpression url) {
        return new XPathFunction("doc", url);
    }

    public XPathExpression abs(XPathExpression expression) {
        return new XPathFunction("abs", expression);
    }

    public XPathExpression ceiling(XPathExpression expression) {
        return new XPathFunction("ceiling", expression);
    }

    public XPathExpression floor(XPathExpression expression) {
        return new XPathFunction("floor", expression);
    }

    public XPathExpression round(XPathExpression expression) {
        return new XPathFunction("round", expression);
    }

    public XPathExpression roundHalfToEven(XPathExpression expression) {
        return new XPathFunction("round-half-to-even", expression);
    }

    public XPathExpression position() {
        return new XPathFunction("position");
    }

    public XPathExpression last() {
        return new XPathFunction("last");
    }

    public XPathExpression currentDateTime() {
        return new XPathFunction("current-dateTime");
    }

    public XPathExpression currentDate() {
        return new XPathFunction("current-date");
    }

    public XPathExpression currentTime() {
        return new XPathFunction("current-time");
    }

    public XPathExpression implicitTimezone() {
        return new XPathFunction("implicit-timezone");
    }

    public XPathExpression defaultCollation() {
        return new XPathFunction("default-collation");
    }

    public XPathExpression staticBaseUri() {
        return new XPathFunction("static-base-uri");
    }

    public XPathExpression not(XPathExpression expression) {
        return new XPathFunction("not", expression);
    }

    public XPathExpression toBoolean(XPathExpression expression) {
        return new XPathFunction("boolean", expression);
    }

    public XPathExpression trueValue() {
        return new XPathFunction("true");
    }

    public XPathExpression falseValue() {
        return new XPathFunction("false");
    }

    public XPathExpression string(XPathExpression expression) {
        return new XPathFunction("string", expression);
    }

    public XPathExpression codepointsToString(Collection<XPathExpression> expressions) {
        return new XPathFunction("codepoints-to-string", new XPathFunction(expressions));
    }

    public XPathExpression stringToCodepoints(XPathExpression string) {
        return new XPathFunction("string-to-codepoints", string);
    }

    public XPathExpression codepointEqual(XPathExpression first, XPathExpression second) {
        return new XPathFunction("codepoint-equal", asList(first, second));
    }

    public XPathExpression compare(XPathExpression left, XPathExpression right) {
        return compare(left, right, null);
    }

    public XPathExpression compare(XPathExpression left, XPathExpression right, XPathExpression collation) {
        final List<XPathExpression> arguments = Stream
                .of(left, right, collation)
                .filter(Objects::nonNull)
                .collect(toList());
        return new XPathFunction("compare", arguments);
    }

    public XPathExpression concat(Collection<XPathExpression> expressions) {
        return new XPathFunction("concat", expressions);
    }

    public XPathExpression stringJoin(@Nullable XPathExpression separator, Collection<XPathExpression> expressions) {
        return new XPathFunction(
                "string-join",
                Stream
                        .of(new XPathFunction(expressions), separator)
                        .filter(Objects::nonNull)
                        .collect(toList()));
    }

    public XPathExpression stringLength(@Nullable XPathExpression expression) {
        return new XPathFunction(
                "string-length",
                expression != null ? singleton(expression) : emptyList());
    }

    public XPathExpression normalizeSpace() {
        return normalizeSpace(null);
    }

    public XPathExpression normalizeSpace(@Nullable XPathExpression expression) {
        return new XPathFunction(
                "normalize-space",
                expression != null ? singleton(expression) : emptyList());
    }

    public XPathExpression upperCase(XPathExpression value) {
        return new XPathFunction("upper-case", value);
    }

    public XPathExpression lowerCase(XPathExpression value) {
        return new XPathFunction("lower-case", value);
    }


    public XPathExpression translate(XPathExpression value1, XPathExpression value2, XPathExpression value3) {
        return new XPathFunction("translate", asList(value1, value2, value3));
    }

    public XPathExpression escapeUri(XPathExpression value, XPathExpression escRes) {
        return new XPathFunction("escape-uri", asList(value, escRes));
    }

    public XPathExpression contains(XPathExpression left, XPathExpression rigth) {
        return new XPathFunction("contains", asList(left, rigth));
    }

    public XPathExpression startsWith(XPathExpression left, XPathExpression right) {
        return new XPathFunction("starts-with", asList(left, right));
    }

    public XPathExpression endsWith(XPathExpression left, XPathExpression right) {
        return new XPathFunction("ends-with", asList(left, right));
    }

    public XPathExpression substringBefore(XPathExpression value1, XPathExpression value2) {
        return new XPathFunction("substring-before", asList(value1, value2));
    }

    public XPathExpression substringAfter(XPathExpression value1, XPathExpression value2) {
        return new XPathFunction("substring-after", asList(value1, value2));
    }

    public XPathExpression matches(XPathExpression value, XPathExpression pattern) {
        return new XPathFunction("matches", asList(value, pattern));
    }

    public XPathExpression replace(XPathExpression string, XPathExpression pattern, XPathExpression replace) {
        return new XPathFunction("replace", asList(string, pattern, replace));
    }

    public XPathExpression tokenize(XPathExpression string, XPathExpression pattern) {
        return new XPathFunction("tokenize", asList(string, pattern));
    }

    public XPathExpression indexOf(Collection<XPathExpression> items, XPathExpression searchItem) {
        return new XPathFunction("index-of", asList(new XPathFunction(items), searchItem));
    }

    public XPathExpression remove(List<XPathExpression> collection, XPathExpression position) {
        return new XPathFunction("remove", asList(new XPathFunction(collection), position));
    }

    public XPathExpression empty(XPathExpression expression) {
        return new XPathFunction("empty", expression);
    }

    public XPathExpression exists(XPathExpression expression) {
        return new XPathFunction("exists", expression);
    }

    public XPathExpression distinctValues(Collection<XPathExpression> values) {
        return new XPathFunction("distinct-values", new XPathFunction(values));
    }

    public XPathExpression insertBefore(Collection<XPathExpression> values, XPathExpression position, XPathExpression value) {
        return new XPathFunction("insert-before", asList(new XPathFunction(values), position, value));
    }

    public XPathExpression reverse(Collection<XPathExpression> values) {
        return new XPathFunction("reverse", new XPathFunction(values));
    }

    public XPathExpression subsequence(Collection<XPathExpression> values, XPathExpression start) {
        return subsequence(values, start, null);
    }

    public XPathExpression subsequence(Collection<XPathExpression> values, XPathExpression start, @Nullable XPathExpression length) {
        final List<XPathExpression> arguments = new ArrayList<>(asList(new XPathFunction(values), start));
        if (length != null) {
            arguments.add(length);
        }

        return new XPathFunction("subsequence", arguments);
    }

    public XPathExpression unordered(Collection<XPathExpression> args) {
        return new XPathFunction("unordered", new XPathFunction(args));
    }

    private static class XPathFunction implements XPathExpression {
        private final String fnName;
        private final Collection<XPathExpression> arguments;

        public XPathFunction(Collection<XPathExpression> arguments) {
            this("", arguments);
        }

        public XPathFunction(String fnName, Collection<XPathExpression> arguments) {
            this.arguments = arguments;
            this.fnName = fnName;
        }

        public XPathFunction(String fnName) {
            this(fnName, emptyList());
        }

        public XPathFunction(String fnName, XPathExpression expression) {
            this(fnName, singleton(expression));
        }

        @Override
        public String build() {
            return fnName + "(" +
                    arguments.stream().map(XPathExpression::build).collect(Collectors.joining(", ")) +
                    ")";
        }
    }

    private static class MultipicableExpression implements XPathExpression {
        private final Collection<XPathExpression> expressions;
        private final XPathExpression operation;

        private MultipicableExpression(Collection<XPathExpression> expressions, XPathExpression operation) {
            this.expressions = expressions;
            this.operation = operation;
        }

        @Override
        public String build() {
            return "(" +
                    expressions.stream()
                            .map(XPathExpression::build)
                            .collect(Collectors.joining(" " + operation.build() + " ")) +
                    ")";
        }

        @Override
        public String toString() {
            return build();
        }
    }

    private static class XPathOperatorExpression implements XPathExpression {
        private final XPathExpression left;

        private final XPathExpression operation;

        private final XPathExpression right;

        public XPathOperatorExpression(XPathExpression left, XPathExpression operator, XPathExpression right) {
            this.left = left;
            this.operation = operator;
            this.right = right;
        }

        @Override
        public String build() {
            return "(" + left.build() + " " + operation.build() + " " + right.build() + ")";
        }

        @Override
        public String toString() {
            return build();
        }
    }
}
