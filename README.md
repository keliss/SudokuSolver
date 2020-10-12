# Sudoku game solver

### Architecture solutions
Since there are many different approaches to solving this problem, the generic Solver interface was introduced.
Same goes for InputValidator interface. This allows for easier extension of code and increases testability.
For the sake of simplicity and readability, current implementation considers as valid only 9x9 Sudoku,
although there's absolutely no problem in adding support for other field sizes.
The implemented solver expects as input a 9x9 matrix with null values in cells to be filled.
The output is a completely filled 9x9 matrix (see tests).

### Algorithm
The algorithm is based on the mathematical model described in this article:
https://www.researchgate.net/publication/228615106_An_integer_programming_model_for_the_sudoku_problem
In short, the idea is to represent the Sudoku game as a Linear Integer Programming problem
by assigning possible number flags to each cell, describing row, column, region and cell constraints
and then solving it like any other LP problem. The flags being in the "ON" state after the calculation will point
at the corresponding solutions for each cell.

### Issues encountered
The initial idea was to solve the problem as a system of linear equations having the complement of 45 (sum)
on the right hand side and then filtering out the incorrect solutions. This seemed to me like a cumbersome and non-optimal
approach (as well as a random-based solution), so I started to search for a proven-to-work one to avoid reinventing the wheel.
Finding the simplest (in terms of code complexity) solution and understanding the idea behind it was the hardest part.