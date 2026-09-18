# Java GUI Calculator
A simple graphical calculator application developed in Java using **Java Swing**. The project is implemented in a **single Java source file** and can be compiled and executed directly from the command line without requiring an IDE.

## Features

* Graphical calculator interface using Java Swing
* Basic arithmetic operations:

  * Addition
  * Subtraction
  * Multiplication
  * Division
* Decimal calculations
* Percentage calculation
* Reciprocal (`1/x`)
* Square (`x²`)
* Square root (`√x`)
* Positive/negative toggle (`+/-`)
* Clear (`C`)
* Clear Entry (`CE`)
* Backspace
* Memory functions:

  * `MC` – Memory Clear
  * `MR` – Memory Recall
  * `M+` – Memory Add
  * `M-` – Memory Subtract
* Keyboard input support
* Calculation history display
* Command-line execution support
* No external libraries or dependencies

## Requirements

Before running the project, make sure Java Development Kit (JDK) is installed on your system.

### Required

* JDK 21 or later
* Command Prompt / Terminal

The project uses only Java's standard libraries, so no additional dependencies need to be installed.

## Checking Java Installation

Open a terminal or Command Prompt and run:

```bash
java -version
```

Then check the Java compiler:

```bash
javac -version
```

Both commands should return the installed Java version.

## Project Structure

The project contains a single Java source file:

```text
JavaCalculator/
└── Calculator.java
```

## Compilation

Open a terminal inside the project directory.

For example, on Windows:

```cmd
cd Desktop\JavaCalculator
```

Compile the source code using:

```cmd
javac Calculator.java
```

If the compilation is successful, Java will generate the required `.class` files automatically.

## Running the Application

After compilation, run:

```cmd
java Calculator
```

The graphical calculator window will open.

## Command-Line Execution

The project is designed to be compiled and launched entirely from the command line.

The complete process is:

```cmd
javac Calculator.java
java Calculator
```

No IDE such as IntelliJ IDEA, Eclipse, or NetBeans is required.

## Usage

After launching the application, calculations can be performed using the graphical buttons.

Examples:

```text
7 + 8 = 15
10 × 5 = 50
100 ÷ 4 = 25
5 x² = 25
25 √x = 5
1 ÷ 4 = 0.25
```

The calculator also supports keyboard input for common operations.

## Error Handling

The application handles common invalid operations, including:

* Division by zero
* Square root of a negative number
* Invalid numerical input

Appropriate error messages are displayed when these operations are attempted.

## Dependencies

There are **no external dependencies**.

The application uses Java standard library components, primarily:

* `javax.swing`
* `java.awt`
* `java.awt.event`
* `java.text`

These libraries are included with the JDK.

## Configuration

No additional configuration, environment files, database setup, or third-party packages are required.

Once the JDK is installed, the project can be compiled and executed directly.
