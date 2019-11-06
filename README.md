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

Every file loaded by user is checked by DimacsReader from sat4j library. There are couple of rules for `.cnf` files:

 * Lines starting with letter `c` are treated as comments
 * Pierwsza linia zaczynająca się literą p jest deklaracją formatu cnf oraz określa ilość zmiennych i klauzul.  Przykład takiej linii: `p cnf 20 534`
 * Każda kolejna linia zawiera jedną klauzulę i jest zakończona zerem. Przykład takiej linii: `16 -30 95 0`. Klauzula odpowiadająca takiej linii to: `( x(16) OR ( NOT x(30) )  OR x(95))`.
 * Zmienne oznaczone są liczbami naturalnymi.
 * Negację zmiennej otrzymujemy za pomocą dopisania minusa przed liczbą.

Jeśli plik pomyślnie przejdzie walidację, jego nazwa zostaje pokolorowana na zielono, w przeciwnym razie - na czerwono. Po wybraniu solvera i uruchomieniu go rezultat pojawia się w oknie po prawej stronie. Wynikiem działania solvera są linie tekstu oznaczone przez jedną z trzech liter:
 * c - linia oznaczona tą literą pełni rolę dodatkowego opisu, komentarza. Przykładowo, informuje o czasie działania solvera czy użytej przez niego pamięci.
 * s - linia oznaczona przez s informuje o spełnialności danego problemu
 * v - linia oznaczona w ten sposób zawiera rozwiązanie. Przykładowo, linia `1 -2 -3 4 5 6` oznacza, że jednym z rozwiązań problemów jest ustawienie pierwszej, czwartej, piątej oraz szóstej zmiennej na wartość logiczną true, natomiast zmienna o numerze drugim oraz trzecim powinna być ustawiona na false.

SAT Solver Interface umożliwia również rozwiązywanie problemu wpisanego przez użytkownika w nieco bardziej przystępnej formie, niż za pomocą pliku w formacie `.cnf`. W prawym górnym rogu aplikacji znajduje się pole, w którym można wprowadzić swój własny problem. Nazwy zmiennych mogą być dowolne, znak “-” oznacza negację, spacja - alternatywę, znak nowej linii - koniunkcję. Po wybraniu solvera i wciśnięciu przycisku Solve Input otrzymamy wynik w oknie poniżej, podobnie jak w przypadku rozwiązywaniu problemu z pliku.


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
