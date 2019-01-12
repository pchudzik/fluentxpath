package com.pchudzik.fluentxpath.api;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.pchudzik.fluentxpath.api.XPathBuilder.*;
import static com.pchudzik.fluentxpath.api.XPathExpression.xpathValue;
import static java.util.Arrays.asList;

class Demo {
    public static void main(String[] args) {
        String articleId = "1234";
        Stream<String> newArticleIds = Stream.of("1", "2", "3", "4");
        String articleCategory = "News";
        String articleCommentsLinkText = "comments";

        String commentsLink = "//div[(@data-test-article-id = '" + articleId + "') and contains(lower-case(@data-test-article-category), '" + articleCategory + "')]" +
                "/span[contains(@class, 'links')]" +
                "/a[contains(lower-case(text()), '" + articleCommentsLinkText + "')]";

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

        String linksToCreatedArticles = xpathOf()
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

        System.out.println("string concatenation: " + commentsLink);
        System.out.println("             builder: " + builderCommentsLink);
        System.out.println("            multiple: " + linksToCreatedArticles);
        System.out.println("\n");
        System.out.println("custom: " + linksForArticleExpression());
    }

    private static List<XPathExpression> articleIdIn(Stream<String> newArticleIds) {
        return newArticleIds.map(singleArticleId -> xpathFn().eq(xpathAttribute("data-test-article-id"), xpathValue(singleArticleId))).collect(Collectors.toList());
    }

    private static String linksForArticleExpression() {
        return xpathFn().greaterThan(
                new XPathCount(xpathOf().anyElement("a").has(xpathFn().contains(xpathAttribute("class"), xpathValue("links")))),
                xpathValue(4))
                .build();
    }

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
}
