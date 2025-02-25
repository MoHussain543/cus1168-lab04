package academy.javapro;

class ExpressionParser {
    private final String input;
    private int position;

    public ExpressionParser(String input) {
        this.input = input;
        this.position = 0;
    }

    public double parseExpression() {
        double result = parseTerm();
        while (position < input.length() && input.charAt(position) == '+') {
            position++; // Skip '+'
            result += parseTerm();
        }
        return result;
    }

    private double parseTerm() {
        double result = parseFactor();
        while (position < input.length() && input.charAt(position) == '*') {
            position++; // Skip '*'
            result *= parseFactor();
        }
        return result;
    }

    private double parseFactor() {
        if (position < input.length() && input.charAt(position) == '(') {
            position++; // Skip '('
            double result = parseExpression();
            if (position < input.length() && input.charAt(position) == ')') {
                position++; // Skip ')'
            } else {
                throw new RuntimeException("Mismatched parentheses at position " + position);
            }
            return result;
        }
        return parseNumber();
    }

    private double parseNumber() {
        StringBuilder number = new StringBuilder();
        while (position < input.length() && (Character.isDigit(input.charAt(position)) || input.charAt(position) == '.')) {
            number.append(input.charAt(position));
            position++;
        }
        if (number.length() == 0) {
            throw new RuntimeException("Unexpected character at position " + position);
        }
        return Double.parseDouble(number.toString());
    }

    public static void main(String[] args) {
        String[] testCases = {
                "2 + 3 * (4 + 5)",
                "2 + 3 * 4",
                "(2 + 3) * 4",
                "2 * (3 + 4) * (5 + 6)",
                "1.5 + 2.5 * 3"
        };

        for (String expression : testCases) {
            System.out.println("\nTest Case: " + expression);
            try {
                ExpressionParser parser = new ExpressionParser(expression.replaceAll("\\s+", ""));
                double result = parser.parseExpression();
                System.out.println("Result: " + result);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
