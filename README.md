# fluentXPath

[![Build Status](https://travis-ci.org/pchudzik/fluentxpath.svg?branch=master)](https://travis-ci.org/pchudzik/fluentxpath)

Allows to fluently build xpath expressions in java. When string concatenation or using string
templates is troubling you when creating xpath expressions.

Note that does not offer anything but builder for xpaths. It doesn't check if your xpath is valid
it's nothing but syntactic sugar so you don't have to concatenate strings.

If you are using groovy or kotlin or any other jvm language with string interpolation you should
probably use string interpolation instead of this library.

## Sample

### Basic

```
String builderCommentsLink = xpathOf()
    .anyElement("div")
    .has(
            xpathFn().and(asList(
                    xpathFn().eq(xpathAttribute("data-test-article-id"), xpathValue(articleId)),
                    xpathFn().contains(xpathFn().lowerCase(xpathAttribute("data-test-article-category")), xpathValue(articleCategory)))))
    .descendantElement("span")
    .has(
            xpathFn().contains(
                    xpathAttribute("class"),
                    xpathValue("links")))
    .descendantElement("a").has(xpathFn().contains(xpathFn().lowerCase(xpathFn().text()), xpathValue(articleCommentsLinkText)))
    .build();
```

### More

* [src/main/java/com/pchudzik/fluentxpath/api/Demo.java](src/main/java/com/pchudzik/fluentxpath/api/Demo.java)
* [src/test/groovy/com/pchudzik/fluentxpath/api/MSDNSamplesTest.groovy](src/test/groovy/com/pchudzik/fluentxpath/api/MSDNSamplesTest.groovy)
* [src/test/groovy/com/pchudzik/fluentxpath/api/XPathBuilderExpressionTest.groovy](src/test/groovy/com/pchudzik/fluentxpath/api/XPathBuilderExpressionTest.groovy)
* [src/test/groovy/com/pchudzik/fluentxpath/api/XPathBuilderFunctionTest.groovy](src/test/groovy/com/pchudzik/fluentxpath/api/XPathBuilderFunctionTest.groovy)
* [src/test/groovy/com/pchudzik/fluentxpath/api/XPathBuilderAxisTest.groovy](src/test/groovy/com/pchudzik/fluentxpath/api/XPathBuilderAxisTest.groovy)

### Other

You can dynamically build xpaths like with JPA's criteria builder adding conditions, loops, etc:
```
xpathOf()
    .anyElement("div")
    .has(
            xpathFn().and(asList(
                    xpathFn().or(articleIdIn(newArticleIds)),
                    xpathFn().contains(xpathFn().lowerCase(xpathAttribute("data-test-article-category")), xpathValue(articleCategory)))))
    .descendantElement("span")
    .has(
            xpathFn().contains(
                    xpathAttribute("class"),
                    xpathValue("links")))
    .descendantElement("a").has(xpathFn().contains(xpathFn().lowerCase(xpathFn().text()), xpathValue(articleCommentsLinkText)))
    .build();
```


## Usage

Add maven dependency:

```TODO```

Add static imports:

```
import static com.pchudzik.fluentxpath.api.XPathBuilder.*
import static com.pchudzik.fluentxpath.api.XPathExpression.xpathValue
``` 

Start building your xpaths.

Snapshots can be downloaded from nexus snapshot repository:

```TODO```

## Missing functions

1. Pull requests are welcome
1. Implement it on your own:
```
private static class XPathCount implements XPathExpression {
    private final XPathExpression expression;

    private XPathCount(XPathExpression expression) {
        this.expression = expression;
    }

    @Override
    public String build() {
        return "count((" +
                expression.build() +
                "))";
    }
}

xpathFn().greaterThan(
        new XPathCount(xpathOf().anyElement("a").has(xpathFn().contains(xpathAttribute("class"), xpathValue("links")))),
        xpathValue(4))
    .build();
``` 

# Changelog

* 1.0.0 - ????? - First released version