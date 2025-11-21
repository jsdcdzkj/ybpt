package com.jsdc.ybpt.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class MathExpressionSolver {

    private static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }

    private static String infixToPostfix(String expression) {
        StringBuilder result = new StringBuilder();
        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (Character.isDigit(ch) || ch == '.') {
                int j = i;
                StringBuilder num = new StringBuilder();
                while (j < expression.length() && (Character.isDigit(expression.charAt(j)) || expression.charAt(j) == '.')) {
                    num.append(expression.charAt(j));
                    j++;
                }
                i = j - 1;
                result.append(new BigDecimal(num.toString()).setScale(4, BigDecimal.ROUND_HALF_UP)).append(" ");
            } else if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop()).append(" ");
                }
                stack.pop();
            } else {
                while (!stack.isEmpty() && precedence(ch) <= precedence(stack.peek())) {
                    result.append(stack.pop()).append(" ");
                }
                stack.push(ch);
            }
        }

        while (!stack.isEmpty()) {
            result.append(stack.pop()).append(" ");
        }

        return result.toString().trim();
    }

    private static BigDecimal evaluatePostfix(String expression) {
        Deque<BigDecimal> stack = new ArrayDeque<>();
        String[] tokens = expression.split(" ");

        for (String token : tokens) {
            if (!token.isEmpty()) {
                if (Character.isDigit(token.charAt(0)) || token.charAt(0) == '.') {
                    stack.push(new BigDecimal(token));
                } else {
                    BigDecimal operand2 = stack.pop();
                    BigDecimal operand1 = stack.pop();
                    BigDecimal result;

                    switch (token) {
                        case "+":
                            result = operand1.add(operand2);
                            break;
                        case "-":
                            result = operand1.subtract(operand2);
                            break;
                        case "*":
                            result = operand1.multiply(operand2);
                            break;
                        case "/":
                            result = operand1.divide(operand2, 2, BigDecimal.ROUND_HALF_UP);
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid operator");
                    }

                    stack.push(result);
                }
            }
        }

        return stack.pop();
    }

    public static BigDecimal evaluateFormula(String userInput){
        String postfixExpression = infixToPostfix(userInput);
        BigDecimal result = evaluatePostfix(postfixExpression);
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入要计算的数学表达式：");
        String userInput = scanner.nextLine();

        String postfixExpression = infixToPostfix(userInput);
        System.out.println("转换为逆波兰表达式：" + postfixExpression);

        BigDecimal result = evaluatePostfix(postfixExpression);
        System.out.println("计算结果为：" + result.setScale(2, BigDecimal.ROUND_HALF_UP));
    }
}



