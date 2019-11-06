# SAT-interface
JavaFX application that helps run SAT solvers (participants of 2017 and 2018 SAT Competition). GUI allows users to compare performance of exact solvers and to find solutions of considered SAT problems

PL:

SAT Competition
 Zawody solverów SAT Competition dzielą się na różne kategorie:
Main Track - główna kategoria, w której solvery rozwiązują od trzystu do sześciuset problemów, a limit czasowy wynosi 5 tys. sekund. Aby wziąć w niej udział trzeba posiadać certyfikaty SAT i UNSAT.
Random SAT Track - to kategoria, w której najlepiej sprawdzają się solvery dobre w rozwiązywaniu losowo wygenerowanych i spełnialnych problemów.
Agile Track -  w tej kategorii najlepiej wypadają solvery, które sprawnie rozwiązują duże ilości niezbyt skomplikowanych problemów.
Parallel Track - jest to kategoria przeznaczona dla solverów działających na wielu procesorach/rdzeniach.
No-Limits Track - jest to kategoria dowolna - aby w niej uczestniczyć nie trzeba posiadać specjalnych certyfikatów ani udostępniać kodu źródłowego solvera.
Incremental Library Track - ta kategoria przeznaczona jest dla przyrostowych solverów.

Wybrane SAT Solvery
   1. AbcdSAT (Parallel)
AbcdSAT bazuje na solverze Glucose w wersji 2.3. Istnieje kilka jego wersji dostosowanych do różnych kategorii SAT Competition: agile, main, no-limit, incremental i parallel track. Bazuje on na heurystyce VSIDS, oraz podobnie jak Lingeling posiada kilka uproszczeń, tj. nie używa preprocesorów takich jak: lifting, probing, distillation, elimination, hyper binary resolution, equivalent literal search, unhiding redundancy, XOR Gaussian elimination.


Repozytorium projektu: https://github.com/jingchaochen/abcdSAT_Rat
Kod źródłowy: https://baldur.iti.kit.edu/sat-competition-2017/solvers/parallel/abcdsat_parallel.zip
2. CaDiCaL
Głównym założeniem przy tworzeniu CaDiCaLa było stworzenie solvera opartego o algorytm CDCL, który jest łatwy do zrozumienia i podatny na zmiany, a jednocześnie nie jest dużo wolniejszy od innych solverów opartych o CDCL. Początkowo chciano drastycznie uprościć wewnętrzne struktury danych, jednakże cel został osiągnięty tylko częściowo w porównaniu na przykład do Lingeling’a. Z drugiej strony jednak CaDiCaL jest szybszy niż Lingeling, chociaż brakuje mu kilku preprocesorów, których obecność ma duże znaczenie w rozwiązywaniu niektórych problemów. Jego API przyrostowe jest niekompletne.
Repozytorium projektu: https://github.com/arminbiere/cadical

3. Glucose
Solver Glucose implementuje algorytm CDCL. Opiera się na Minisat, szczególny nacisk kładąc na usuwanie nieużywanych klauzul tak szybko jak jest to możliwe oraz na oryginalnym schemacie restartu. Glucose należy do nowoczesnych mechanizmów uczenia maszynowego. Mocno opiera swoje działanie o koncept Literal Block Distance, czyli mierze opisującej jakość uczonych klauzul. Dzięki temu właśnie jest w stanie nie tylko identyfikować dobre klauzule, ale również usuwać te niepoprawne, przez co zwiększa niezupełność algorytmu CDCL, na którym opiera swoje działanie. Bardzo dobrze znajduje najkrótsze i najłatwiejsze rozwiązanie.  
Kod źródłowy: http://www.labri.fr/perso/lsimon/downloads/softwares/glucose-syrup-4.1.tgz
4. Riss
Solver Riss implementuje algorytm CDCL. Łączy on w sobie cechy Minisat z Glucose 2.2. Poza wieloma oferowanymi przez Riss rozszerzeniami algorytmu przeszukiwania, Riss jest również wyposażony w preprocesor Coprocesor, który wywołany na początku działania upraszcza formuły i wspomaga uczenie się klauzul. Uproszczenie opiera się o trzy zadania:
unit propagation, czyli rozwiązywanie klauzul od dwuelementowych do coraz dłuższych
eliminacja równoważności
detekcja “czystych” literałów.
Riss Solver wspiera wyliczanie więcej niż jednego modelu oraz rozwiązania przyrostowe. 

Riss 7 z 2017 roku bierze udział we wszystkich kategoriach konkursu, poza Random SAT Track.

Kod źródłowy: https://github.com/nmanthey/riss-solver.

Interfejs i działanie programu
Po załadowaniu programu ukazuje nam się ekran startowy.
Po lewej stronie znajdują się cztery przyciski oraz rozwijana lista solverów służące kolejno do:
Load CNF file - wczytanie pliku w formacie CNF do programu.
Lista wyboru solvera
Output file - wybranie pliku, do którego zostanie zapisany rezultat. Przycisk jest dostępny tylko po wybraniu solvera umożliwiającego taką operację.
Use Satelite - czerwony kolor oznacza, że problem nie będzie minimalizowany przed rozwiązaniem go przez solver, zielony - przeciwnie
Solve from file - Uruchamia rozwiązywanie problemu z pliku CNF przez wybrany solver. Przycisk jest odblokowany, jeśli plik z problemem CNF pomyślnie przeszedł walidację.

Każdy plik z problemem wczytany przez użytkownika zostaje sprawdzony pod kątem poprawności za pomocą parsera DimacsReader, który jest częścią biblioteki sat4j. Format pliku .cnf ma określone reguły:

Linie zaczynające się od c traktowane są jako komentarz
Pierwsza linia zaczynająca się literą p jest deklaracją formatu cnf oraz określa ilość zmiennych i klauzul.  Przykład takiej linii: p cnf 20 534
Każda kolejna linia zawiera jedną klauzulę i jest zakończona zerem. Przykład takiej linii: 16 -30 95 0. Klauzula odpowiadająca takiej linii to: ( x(16) OR ( NOT x(30) )  OR x(95)) .
Zmienne oznaczone są liczbami naturalnymi.
Negację zmiennej otrzymujemy za pomocą dopisania minusa przed liczbą.

Jeśli plik pomyślnie przejdzie walidację, jego nazwa zostaje pokolorowana na zielono, w przeciwnym razie - na czerwono. Po wybraniu solvera i uruchomieniu go rezultat pojawia się w oknie po prawej stronie. Wynikiem działania solvera są linie tekstu oznaczone przez jedną z trzech liter:
c - linia oznaczona tą literą pełni rolę dodatkowego opisu, komentarza. Przykładowo, informuje o czasie działania solvera czy użytej przez niego pamięci.
s - linia oznaczona przez s informuje o spełnialności danego problemu
v - linia oznaczona w ten sposób zawiera rozwiązanie. Przykładowo, linia
1 -2 -3 4 5 6 oznacza, że jednym z rozwiązań problemów jest ustawienie pierwszej, czwartej, piątej oraz szóstej zmiennej na wartość logiczną true, natomiast zmienna o numerze drugim oraz trzecim powinna być ustawiona na false.

SAT Solver Interface umożliwia również rozwiązywanie problemu wpisanego przez użytkownika w nieco bardziej przystępnej formie, niż za pomocą pliku w formacie .cnf. W prawym górnym rogu aplikacji znajduje się pole, w którym można wprowadzić swój własny problem. Nazwy zmiennych mogą być dowolne, znak “-” oznacza negację, spacja - alternatywę, znak nowej linii - koniunkcję. Po wybraniu solvera i wciśnięciu przycisku Solve Input otrzymamy wynik w oknie poniżej, podobnie jak w przypadku rozwiązywaniu problemu z pliku.


Źródła
Źródła użyte przy tworzeniu projektu i dokumentacji:
Strona www SAT Competition: http://www.satcompetition.org 
Strona www SAT Competition 2018: http://sat2018.forsyte.tuwien.ac.at/index.php?cat=result
Opisy solverów 2017: https://helda.helsinki.fi/bitstream/handle/10138/224324/sc2017-proceedings.pdf?sequence=4&isAllowed=y
Opisy solverów 2018: https://helda.helsinki.fi/bitstream/handle/10138/237063/sc2018_proceedings.pdf?sequence=6&isAllowed=y
Kody źródłowe solverów z roku 2017:
https://baldur.iti.kit.edu/sat-competition-2017/solvers/
Opis formatu .cnf:
https://people.sc.fsu.edu/~jburkardt/data/cnf/cnf.html
Relacja z SAT Competition 2018:
http://sat2018.forsyte.tuwien.ac.at/downloads/satcomp18slides.pdf


