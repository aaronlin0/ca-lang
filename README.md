# C@ Programming Language

C@ is a simplified version of the C programming language, designed to eliminate complex features such as pointers, making it more accessible for newer developers. C@ programs are transpiled into Java, allowing programmers with previous knowledge in Java to learn C more easily.

## Key Features

- **Simplified Syntax**: Retains the core structure of C while removing pointers and other advanced features.
- **Java Integration**: Transpiles C@ code into Java, leveraging the robustness and portability of the Java platform.

## Prerequisites

- [Java Development Kit](https://www.oracle.com/java/technologies/downloads/)

## Getting Started

1. Write your C@ code in a `.ca` file.
2. Use the C@ transpiler to convert the code to Java. Check [Build](#build) for compilation instructions.

## Example

```c
// main.ca

#define PI 3.14

void main() {
    const double E = PI;
    if (E == 3) {
        printf("E is equal to 3.\n");
    } else if (E == 3.14) {
        printf("E is equal to 3.14.\n");
    } else {
        printf("E is not equal to 3 or 3.14\n");
    }
}
```

## Build

1. Compile `Transpiler.java`.
2. Run `Transpiler` with your preferred flags.

```powershell
javac Transpiler.java
java Transpiler -o output main.ca
```
