package com.atguigu.gmall.item;

import org.junit.jupiter.api.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class StringExpressionParser {

    @Test
    void test01(){
        ExpressionParser parser = new SpelExpressionParser();

        String myExpression = "Hello #{ 1 + 1}";

        Expression expression = parser.parseExpression(myExpression, new TemplateParserContext());

        Object value = expression.getValue();
        System.out.println("value = " + value);

    }
}
