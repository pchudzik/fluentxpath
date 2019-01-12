# fluentXPath

[![Build Status](https://travis-ci.org/pchudzik/fluentxpath.svg?branch=master)](https://travis-ci.org/pchudzik/fluentxpath)

## Introduction 
Allows to fluently build xpath expressions in java. When string concatenation or using string
templates is troubling you when creating xpath expressions.

Note that does not offer anything but builder for xpaths. It doesn't check if your xpath is valid
it's nothing but syntactic sugar so you don't have to concatenate strings.

If you are using groovy or kotlin or any other jvm language with string interpolation you should
probably use string interpolation instead of this library.

## Contents

* [Introduction](#introduction)
* [Contents](#contents)
* [Sample](#sample)
  * [Basic](#basic)
  * [Samples](#samples)
  * [Dynamic xpaths](#dynamic-xpaths)
  * [Missing functions](#missing-functions)
* [Usage](#usage)
  * [Releases](#releases)
  * [Snapshots](#snapshots)
* [Development](#development)
* [Changelog](#changelog)
  * [1.0.0 - 12.01.2019](#100---12012019)

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

### Samples

* [src/main/java/com/pchudzik/fluentxpath/api/Demo.java](src/main/java/com/pchudzik/fluentxpath/api/Demo.java)
* [src/test/groovy/com/pchudzik/fluentxpath/api/MSDNSamplesTest.groovy](src/test/groovy/com/pchudzik/fluentxpath/api/MSDNSamplesTest.groovy)
* [src/test/groovy/com/pchudzik/fluentxpath/api/XPathBuilderExpressionTest.groovy](src/test/groovy/com/pchudzik/fluentxpath/api/XPathBuilderExpressionTest.groovy)
* [src/test/groovy/com/pchudzik/fluentxpath/api/XPathBuilderFunctionTest.groovy](src/test/groovy/com/pchudzik/fluentxpath/api/XPathBuilderFunctionTest.groovy)
* [src/test/groovy/com/pchudzik/fluentxpath/api/XPathBuilderAxisTest.groovy](src/test/groovy/com/pchudzik/fluentxpath/api/XPathBuilderAxisTest.groovy)

### Dynamic xpaths

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

### Missing functions

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

## Usage

### Releases

Add maven dependency:

```
<dependency>
  <groupId>com.pchudzik</groupId>
  <artifactId>fluentxpath</artifactId>
  <version>1.0.0</version>
</dependency>
```

In gradle:

```
compile "com.pchudzik:fluentxpath:1.0.0"
```

Add static imports:

```
import static com.pchudzik.fluentxpath.api.XPathBuilder.*
import static com.pchudzik.fluentxpath.api.XPathExpression.xpathValue
``` 

Start building your xpaths.

### Snapshots

Configure nexus snapshots repository:

```
<repositories>
  <repository>
    <id>sonatype-snapshots</id>
    <url>https://oss.sonatype.org/content/repositories/snapshots</url>
  </repository>
</repositories>
```

Add dependency to fluentxpath

```
<dependency>
  <groupId>com.pchudzik</groupId>
  <artifactId>fluentxpath</artifactId>
  <version>1.0.1-SNAPSHOT</version>
</dependency>
```

In gradle:

```
compile "com.pchudzik:fluentxpath:1.0.1-SNAPSHOT"
``` 

## Development

### Application version

Application version is configured in [gradle.properties](gradle.properties) file and is managed
manually. Remove -SNAPSHOT when releasing version and bump to next -SNAPSHOT version after release.

### Deployment

Snapshot are automatically deployed with every build on master branch (on
[travis-ci](https://travis-ci.org/pchudzik/fluentxpath)). See [.travis.yml](.travis.yml) file for
details.

Releases are deployed manually from local machine using following incantation:

```./gradlew clean publish closeRepository -DnexusUsername=secret -DnexusPassword=secret_password```

After release go to [https://oss.sonatype.org/#stagingRepositories](oss.sonatype.org) and release
repository.

## Changelog

### 1.0.0 - 12.01.2019

* 1.0.0 - 12.01.2019 - First released version
