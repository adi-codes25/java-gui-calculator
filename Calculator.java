import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.Locale;

public class Calculator extends JFrame {

    private final JTextField display;
    private final JTextArea historyArea;

    private double firstNumber = 0;
    private String operator = "";
    private boolean startNewNumber = true;
    private double memory = 0;
    private boolean hasMemory = false;

    private final DecimalFormat formatter =
            new DecimalFormat("0.###############");

    public Calculator() {

        setTitle("Calculator");
        setSize(430, 720);
        setMinimumSize(new Dimension(380, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(8, 8));
        mainPanel.setBackground(new Color(32, 32, 32));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // ---------------- DISPLAY ----------------

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(32, 32, 32));

        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setBackground(new Color(32, 32, 32));
        historyArea.setForeground(new Color(170, 170, 170));
        historyArea.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        historyArea.setLineWrap(true);
        historyArea.setWrapStyleWord(true);

        JScrollPane historyScroll = new JScrollPane(historyArea);
        historyScroll.setBorder(null);
        historyScroll.setPreferredSize(new Dimension(400, 75));
        historyScroll.setBackground(new Color(32, 32, 32));

        display = new JTextField("0");
        display.setEditable(false);
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setBackground(new Color(32, 32, 32));
        display.setForeground(Color.WHITE);
        display.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        display.setBorder(new EmptyBorder(5, 5, 10, 5));

        topPanel.add(historyScroll, BorderLayout.NORTH);
        topPanel.add(display, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // ---------------- BUTTON PANEL ----------------

        JPanel buttonPanel = new JPanel(new GridLayout(6, 4, 5, 5));
        buttonPanel.setBackground(new Color(32, 32, 32));

        // Row 1
        addButton(buttonPanel, "MC", new Color(45, 45, 45));
        addButton(buttonPanel, "MR", new Color(45, 45, 45));
        addButton(buttonPanel, "M+", new Color(45, 45, 45));
        addButton(buttonPanel, "M-", new Color(45, 45, 45));

        // Row 2
        addButton(buttonPanel, "%", new Color(55, 55, 55));
        addButton(buttonPanel, "CE", new Color(55, 55, 55));
        addButton(buttonPanel, "C", new Color(55, 55, 55));
        addButton(buttonPanel, "⌫", new Color(55, 55, 55));

        // Row 3
        addButton(buttonPanel, "1/x", new Color(55, 55, 55));
        addButton(buttonPanel, "x²", new Color(55, 55, 55));
        addButton(buttonPanel, "√x", new Color(55, 55, 55));
        addButton(buttonPanel, "÷", new Color(65, 65, 65));

        // Row 4
        addButton(buttonPanel, "7", new Color(48, 48, 48));
        addButton(buttonPanel, "8", new Color(48, 48, 48));
        addButton(buttonPanel, "9", new Color(48, 48, 48));
        addButton(buttonPanel, "×", new Color(65, 65, 65));

        // Row 5
        addButton(buttonPanel, "4", new Color(48, 48, 48));
        addButton(buttonPanel, "5", new Color(48, 48, 48));
        addButton(buttonPanel, "6", new Color(48, 48, 48));
        addButton(buttonPanel, "-", new Color(65, 65, 65));

        // Row 6
        addButton(buttonPanel, "1", new Color(48, 48, 48));
        addButton(buttonPanel, "2", new Color(48, 48, 48));
        addButton(buttonPanel, "3", new Color(48, 48, 48));
        addButton(buttonPanel, "+", new Color(65, 65, 65));

        // Bottom extra row
        JPanel bottomPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        bottomPanel.setBackground(new Color(32, 32, 32));

        addButton(bottomPanel, "+/-", new Color(48, 48, 48));
        addButton(bottomPanel, "0", new Color(48, 48, 48));
        addButton(bottomPanel, ".", new Color(48, 48, 48));
        addButton(bottomPanel, "=", new Color(0, 160, 220));

        JPanel calculatorButtons = new JPanel(new BorderLayout(0, 5));
        calculatorButtons.setBackground(new Color(32, 32, 32));

        calculatorButtons.add(buttonPanel, BorderLayout.CENTER);
        calculatorButtons.add(bottomPanel, BorderLayout.SOUTH);

        mainPanel.add(calculatorButtons, BorderLayout.CENTER);

        setContentPane(mainPanel);

        // Keyboard support
        setupKeyboard();

        // Allow window to receive keyboard input
        setFocusable(true);
    }

    // --------------------------------------------------
    // BUTTON CREATION
    // --------------------------------------------------

    private void addButton(JPanel panel, String text, Color background) {

        JButton button = new JButton(text);

        button.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        button.setForeground(Color.WHITE);
        button.setBackground(background);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(
                new Color(75, 75, 75), 1));

        button.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(background.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(background);
            }
        });

        button.addActionListener(e -> processInput(text));

        panel.add(button);
    }

    // --------------------------------------------------
    // INPUT PROCESSING
    // --------------------------------------------------

    private void processInput(String input) {

        // Numbers
        if (input.matches("[0-9]")) {
            enterNumber(input);
            return;
        }

        // Decimal
        if (input.equals(".")) {
            enterDecimal();
            return;
        }

        // Operators
        if (input.equals("+") ||
                input.equals("-") ||
                input.equals("×") ||
                input.equals("÷")) {

            chooseOperator(input);
            return;
        }

        // Equals
        if (input.equals("=")) {
            calculateResult();
            return;
        }

        // Clear
        if (input.equals("C")) {
            clearAll();
            return;
        }

        // Clear Entry
        if (input.equals("CE")) {
            clearEntry();
            return;
        }

        // Backspace
        if (input.equals("⌫")) {
            backspace();
            return;
        }

        // Percentage
        if (input.equals("%")) {
            percentage();
            return;
        }

        // Reciprocal
        if (input.equals("1/x")) {
            reciprocal();
            return;
        }

        // Square
        if (input.equals("x²")) {
            square();
            return;
        }

        // Square Root
        if (input.equals("√x")) {
            squareRoot();
            return;
        }

        // Sign
        if (input.equals("+/-")) {
            changeSign();
            return;
        }

        // Memory
        if (input.equals("MC")) {
            memoryClear();
            return;
        }

        if (input.equals("MR")) {
            memoryRecall();
            return;
        }

        if (input.equals("M+")) {
            memoryAdd();
            return;
        }

        if (input.equals("M-")) {
            memorySubtract();
            return;
        }
    }

    // --------------------------------------------------
    // NUMBER FUNCTIONS
    // --------------------------------------------------

    private void enterNumber(String number) {

        if (startNewNumber || display.getText().equals("0")) {
            display.setText(number);
            startNewNumber = false;
        } else {
            display.setText(display.getText() + number);
        }
    }

    private void enterDecimal() {

        if (startNewNumber) {
            display.setText("0.");
            startNewNumber = false;
        } else if (!display.getText().contains(".")) {
            display.setText(display.getText() + ".");
        }
    }

    // --------------------------------------------------
    // OPERATORS
    // --------------------------------------------------

    private void chooseOperator(String newOperator) {

        double currentNumber = getDisplayValue();

        if (!operator.isEmpty() && !startNewNumber) {
            double result = performCalculation(
                    firstNumber,
                    currentNumber,
                    operator
            );

            displayResult(result);
            firstNumber = result;
        } else {
            firstNumber = currentNumber;
        }

        operator = newOperator;
        startNewNumber = true;

        historyArea.setText(
                formatNumber(firstNumber) + " " + operator
        );
    }

    private void calculateResult() {

        if (operator.isEmpty()) {
            return;
        }

        double secondNumber = getDisplayValue();

        double result;

        try {
            result = performCalculation(
                    firstNumber,
                    secondNumber,
                    operator
            );
        } catch (ArithmeticException e) {
            display.setText("Cannot divide by zero");
            operator = "";
            startNewNumber = true;
            return;
        }

        String expression =
                formatNumber(firstNumber) + " " +
                operator + " " +
                formatNumber(secondNumber) +
                " = " +
                formatNumber(result);

        historyArea.setText(expression);

        displayResult(result);

        firstNumber = result;
        operator = "";
        startNewNumber = true;
    }

    private double performCalculation(
            double a,
            double b,
            String op) {

        switch (op) {

            case "+":
                return a + b;

            case "-":
                return a - b;

            case "×":
                return a * b;

            case "÷":
                if (b == 0) {
                    throw new ArithmeticException();
                }
                return a / b;

            default:
                return b;
        }
    }

    // --------------------------------------------------
    // SPECIAL FUNCTIONS
    // --------------------------------------------------

    private void percentage() {

        double value = getDisplayValue();

        if (!operator.isEmpty()) {
            value = firstNumber * value / 100.0;
        } else {
            value = value / 100.0;
        }

        displayResult(value);
    }

    private void reciprocal() {

        double value = getDisplayValue();

        if (value == 0) {
            display.setText("Cannot divide by zero");
            startNewNumber = true;
            return;
        }

        double result = 1 / value;

        historyArea.setText("1 / " + formatNumber(value));

        displayResult(result);
        startNewNumber = true;
    }

    private void square() {

        double value = getDisplayValue();
        double result = value * value;

        historyArea.setText(
                "(" + formatNumber(value) + ")²"
        );

        displayResult(result);
        startNewNumber = true;
    }

    private void squareRoot() {

        double value = getDisplayValue();

        if (value < 0) {
            display.setText("Invalid input");
            startNewNumber = true;
            return;
        }

        double result = Math.sqrt(value);

        historyArea.setText(
                "√(" + formatNumber(value) + ")"
        );

        displayResult(result);
        startNewNumber = true;
    }

    private void changeSign() {

        double value = getDisplayValue();

        if (value != 0) {
            value = -value;
        }

        displayResult(value);
    }

    // --------------------------------------------------
    // CLEAR / BACKSPACE
    // --------------------------------------------------

    private void clearAll() {

        display.setText("0");
        historyArea.setText("");

        firstNumber = 0;
        operator = "";
        startNewNumber = true;
    }

    private void clearEntry() {

        display.setText("0");
        startNewNumber = true;
    }

    private void backspace() {

        if (startNewNumber) {
            return;
        }

        String current = display.getText();

        if (current.length() <= 1 ||
                (current.length() == 2 && current.startsWith("-"))) {

            display.setText("0");
            startNewNumber = true;

        } else {
            display.setText(
                    current.substring(0, current.length() - 1)
            );
        }
    }

    // --------------------------------------------------
    // MEMORY FUNCTIONS
    // --------------------------------------------------

    private void memoryClear() {

        memory = 0;
        hasMemory = false;
    }

    private void memoryRecall() {

        if (hasMemory) {
            displayResult(memory);
            startNewNumber = true;
        }
    }

    private void memoryAdd() {

        memory += getDisplayValue();
        hasMemory = true;
    }

    private void memorySubtract() {

        memory -= getDisplayValue();
        hasMemory = true;
    }

    // --------------------------------------------------
    // DISPLAY HELPERS
    // --------------------------------------------------

    private double getDisplayValue() {

        try {
            return Double.parseDouble(display.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void displayResult(double value) {

        if (Double.isNaN(value) ||
                Double.isInfinite(value)) {

            display.setText("Error");

        } else {

            display.setText(formatNumber(value));
        }
    }

    private String formatNumber(double value) {

        return formatter.format(value);
    }

    // --------------------------------------------------
    // KEYBOARD SUPPORT
    // --------------------------------------------------

    private void setupKeyboard() {

        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(e -> {

                    if (e.getID() != KeyEvent.KEY_PRESSED) {
                        return false;
                    }

                    int key = e.getKeyCode();

                    // Numbers
                    if (key >= KeyEvent.VK_0 &&
                            key <= KeyEvent.VK_9) {

                        processInput(
                                String.valueOf(
                                        key - KeyEvent.VK_0
                                )
                        );

                        return true;
                    }

                    // Decimal
                    if (key == KeyEvent.VK_DECIMAL ||
                            key == KeyEvent.VK_PERIOD) {

                        processInput(".");
                        return true;
                    }

                    // Plus
                    if (key == KeyEvent.VK_ADD ||
                            key == KeyEvent.VK_EQUALS &&
                            e.isShiftDown()) {

                        processInput("+");
                        return true;
                    }

                    // Minus
                    if (key == KeyEvent.VK_SUBTRACT ||
                            key == KeyEvent.VK_MINUS) {

                        processInput("-");
                        return true;
                    }

                    // Multiply
                    if (key == KeyEvent.VK_MULTIPLY ||
                            key == KeyEvent.VK_X) {

                        processInput("×");
                        return true;
                    }

                    // Divide
                    if (key == KeyEvent.VK_DIVIDE ||
                            key == KeyEvent.VK_SLASH) {

                        processInput("÷");
                        return true;
                    }

                    // Equals / Enter
                    if (key == KeyEvent.VK_ENTER ||
                            key == KeyEvent.VK_EQUALS) {

                        processInput("=");
                        return true;
                    }

                    // Backspace
                    if (key == KeyEvent.VK_BACK_SPACE) {

                        processInput("⌫");
                        return true;
                    }

                    // Escape = Clear
                    if (key == KeyEvent.VK_ESCAPE) {

                        processInput("C");
                        return true;
                    }

                    return false;
                });
    }

    // --------------------------------------------------
    // MAIN METHOD
    // --------------------------------------------------

    public static void main(String[] args) {

        // If Java is running in a headless environment,
        // provide a simple command-line calculator mode.
        if (GraphicsEnvironment.isHeadless()) {
            runCommandLineCalculator();
            return;
        }

        SwingUtilities.invokeLater(() -> {

            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName()
                );
            } catch (Exception ignored) {
            }

            Calculator calculator = new Calculator();
            calculator.setVisible(true);
        });
    }

    // --------------------------------------------------
    // COMMAND LINE FALLBACK
    // --------------------------------------------------

    private static void runCommandLineCalculator() {

        java.util.Scanner scanner =
                new java.util.Scanner(System.in);

        System.out.println("================================");
        System.out.println("       JAVA CALCULATOR");
        System.out.println("================================");
        System.out.println("Command-line mode");
        System.out.println("Enter expressions such as:");
        System.out.println("10 + 20");
        System.out.println("50 * 4");
        System.out.println("100 / 5");
        System.out.println("Type exit to quit.");
        System.out.println();

        while (true) {

            System.out.print(">>> ");

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            try {

                String[] parts = input.split("\\s+");

                if (parts.length != 3) {
                    System.out.println(
                            "Format: number operator number"
                    );
                    continue;
                }

                double a = Double.parseDouble(parts[0]);
                String op = parts[1];
                double b = Double.parseDouble(parts[2]);

                double result;

                switch (op) {

                    case "+":
                        result = a + b;
                        break;

                    case "-":
                        result = a - b;
                        break;

                    case "*":
                    case "x":
                    case "X":
                        result = a * b;
                        break;

                    case "/":
                        if (b == 0) {
                            System.out.println(
                                    "Cannot divide by zero."
                            );
                            continue;
                        }
                        result = a / b;
                        break;

                    default:
                        System.out.println(
                                "Unsupported operator."
                        );
                        continue;
                }

                System.out.println(
                        "Result = " + result
                );

            } catch (Exception e) {

                System.out.println(
                        "Invalid expression."
                );
            }
        }

        scanner.close();
    }
}