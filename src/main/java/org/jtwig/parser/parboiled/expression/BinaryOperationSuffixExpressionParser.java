package org.jtwig.parser.parboiled.expression;

import java.util.*;
import java.util.function.Function;
import org.jtwig.model.expression.BinaryOperationExpression;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.jtwig.parser.parboiled.base.SpacingParser;
import org.jtwig.parser.parboiled.expression.operator.BinaryOperatorParser;
import org.jtwig.render.expression.calculator.operation.binary.BinaryOperator;
import org.jtwig.util.Collections2;
import org.parboiled.Rule;
import org.parboiled.annotations.Label;

public class BinaryOperationSuffixExpressionParser extends ExpressionParser<BinaryOperationExpression> {
    final Collection<BinaryOperator> operators;

    public BinaryOperationSuffixExpressionParser(ParserContext context, Collection<BinaryOperator> operators) {
        super(BinaryOperationSuffixExpressionParser.class, context);
        this.operators = operators;
    }

    @Override
    @Label("BinaryOperationSuffix Expression")
    public Rule ExpressionRule() {
        Rule initialExpression = EMPTY;
        Map<Integer, List<BinaryOperator>> index = Collections2.multimap(operators, precedence());

        List<Integer> integers = new ArrayList<>(index.keySet());
        Collections.sort(integers);
        for (Integer integer : integers) {
            initialExpression = BinaryOperation(initialExpression, index.get(integer));
        }
        return initialExpression;
    }

    Function<BinaryOperator, Integer> precedence() {
        return new Function<BinaryOperator, Integer>() {
            @Override
            public Integer apply(BinaryOperator input) {
                return input.precedence();
            }
        };
    }

    Rule BinaryOperation(Rule expressionRule, List<BinaryOperator> operators) {
        return Sequence(
                expressionRule,
                parserContext().parser(SpacingParser.class).Spacing(),
                ZeroOrMore(
                        parserContext().parser(PositionTrackerParser.class).PushPosition(),
                        parserContext().parser(BinaryOperatorParser.class).BinaryOperator(operators),
                        parserContext().parser(SpacingParser.class).Spacing(),
                        Sequence(
                                parserContext().parser(PrimaryExpressionParser.class).ExpressionRule(),
                                expressionRule
                        ),
                        push(new BinaryOperationExpression(
                                parserContext().parser(PositionTrackerParser.class).pop(2),
                                pop(2),
                                parserContext().parser(BinaryOperatorParser.class).pop(1),
                                pop()
                        ))
                )

        );
    }
}
