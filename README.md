# SAT-interface
JavaFX application that helps run SAT solvers (participants of 2017 and 2018 SAT Competition). GUI allows users to compare performance of exact solvers and to find solutions of considered SAT problems.

## Application interface:
![app-view](https://github.com/monikanana/SAT-interface/blob/master/readme-images/app-view.png "App view")

On the left there are four buttons and one combo list with solvers:
1. Load CNF file
2. Combo list with solvers
3. Output file - select file where you would like to save the output. Button becomes active when you select solver with such option available.
4. Use Satelite - if this button is green problem will be minimized before solving.
5. Solve from file - This button is available if CNF file passed validation.

![solver-list](https://github.com/monikanana/SAT-interface/blob/master/readme-images/solver-list.png "Solver list")

Every file loaded by user is checked by DimacsReader from sat4j library. There are couple of rules for `.cnf` files:

 * Lines starting with letter `c` are treated as comments
 * First line (starting with letter `p` is a cnf format declaration and contains number of variables and clauses, e.g. `p cnf 20 534`
 * Each following line contains one clause and has zero at the end, e.g. `16 -30 95 0`. Clause denoted by this line is `( x(16) OR ( NOT x(30) )  OR x(95))`
 * Variables are natural numbers.
 * `-` means `NOT`

![file-problem-satelite](https://github.com/monikanana/SAT-interface/blob/master/readme-images/file-problem-satelite.png "File Problem Satelite")
If the file is valid its name is green, otherwise - it's red. After selecting and running solver the result is presented in the window on the right. The result is a block of text, each line starts with one of three letters:
 * c - line with this letter contains some description, comment. For instance it informs about solver running time or memory used.
 * s - line starting with s informs wheather problem can be solved (is satisfiable)
 * v - line starting with v contains solution. For example `1 -2 -3 4 5 6` means, that one of the problem's solutions is to set first, fourth, fifth and sixth variable value to `true`, and the second and third should be `false`.

![input-problem](https://github.com/monikanana/SAT-interface/blob/master/readme-images/input-problem.png "Problem from input")
SAT Solver Interface allows user to input his problem in a more convinient way than loading `.cnf`file. In the top-right corner of UI there is a textfield where you can type your problem - variable names can be also letters or words separated by space (`OR`), between each line there is logical `AND`, `-` means `NOT`. After selecting solver and pressing Solve Input button user gets result in the window below - same way as for .cnf files.

 * SAT Competition website: http://www.satcompetition.org 
 * SAT Competition 2018: http://sat2018.forsyte.tuwien.ac.at/index.php?cat=result
 * Solvers descriptions (2017): https://helda.helsinki.fi/bitstream/handle/10138/224324/sc2017-proceedings.pdf?sequence=4&isAllowed=y
 * Solver descriptions (2018): https://helda.helsinki.fi/bitstream/handle/10138/237063/sc2018_proceedings.pdf?sequence=6&isAllowed=y
 * Solvers source code (2017):
https://baldur.iti.kit.edu/sat-competition-2017/solvers/
 * About .cnf format:
https://people.sc.fsu.edu/~jburkardt/data/cnf/cnf.html
 * SAT Competition 2018:
http://sat2018.forsyte.tuwien.ac.at/downloads/satcomp18slides.pdf
