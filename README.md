# Lisp Interpreter #
##  Part 1 ##
**Goal**: convert a mixed s-expression to a pure dot notation<br>
**Run Instruction**:
>make init

build the target directory

>make build

compile the source file

>make rebuild

clear classes generated and rebuild the project again

>make run

run the application

>make clean

clean *.class files

**Input Manual**
1. Atom only consists of uppercase letters and integers
2. Enter a s-expression followed by a in a single $ or $$
3. It is allowed to appending a single or $ or $$ to any empty expression(only spaces)
4. After input a single $$, please press any key to exit
5. When it catches an error, the interpreter would ignore any input until meeting a single $ or $$

[Sample test cases](http://web.cse.ohio-state.edu/~soundarajan.1/courses/6341/l1input.txt)

**Sketch**
0. input process
As disccussed in the piazza forum, we have two ways to report an error while considering the case of potential multiple line input. One is to immediately report an error even if the user doesn't enter a single $. Another way is to wait an user enter a single $. I adopt the former way. Although it is a bit more difficult than the latter, the former way, an interactive one, is much more friendly to users. Because if the starting few lines has some errors, it is meaningless to let user continuesly input other expression. The RunStateEnum is used to identify the input checking state: reset, suceed, wait, error. Wait state is used to implement the design idea mentioned above. Specifically, if the user enter "(2 3", the interpreter will regard the current  input as an illegal s-expression. But it is possible that the user input ")" in the future, we could not report an error immediately. In other words, the wait state is indicating that the current input is illegal but it will become legal by inputing some other input.
1. Sexp.java
Same design given in handouts.
	class SExp{
		int type; /* 1: integer atom; 2: symbolic atom; 3: non-atom */
		int val; /* if type is 1 */
		string name; /* if type is 2 */
		SExp* left; SExp* right; /* if type is 3 */
	...}
2. TokenHandler.java
TokenHandler is a util class, following the rule in [handouts](http://web.cse.ohio-state.edu/~soundarajan.1/courses/6341/lispInput1.txt). 
It has two function: ckNextToken, skipToken. ckNextToken repeated return the same next token unless you call the skipToken.
The TokenHandler utilize the java.util.StringTokenizer.
3. utils/SexpUtil.java
Regard sexp as a binary tree, the pure dot notation output could be implemented using inorder tree traverse.
4. exceptions
The package exception has 3 exception class. InvalidSexpException and IncompletenessException are both inherited from LispExcetpion. The reason why use two different is to implete the interactive programming design stated in the part 0. IncompletenessExcetion is corresponding to the wait state.
5. Parser
Parser is the core content in project 1, it has two recursive function: input and input2, follow the idea in handouts. Extend the process for token "(" in the input[] to deal with the list notation.

	input[] = [ eq[ckNextToken[],4] --> getId[];
		  | eq[ckNextToken[],1] --> [NoToken --> error!;
									eq[ckNextToken[], 2] --> NIL;
					     			eq[ckNextToken[], 3] --> cons[input[], input[]];
					     			T --> cons[input[], input2[]];
									];
		  | T --> error!
		  ]
	input2[] = [NoToken --> error!;
				eq[ckNextToken[],2] --> NIL;
		   		| T --> cons[input[], input2[]]; ]


