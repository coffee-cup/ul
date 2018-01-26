# The Unnamed Language

Programming language for the UVic CSC 435 Compiler Construction class.

_View this repo on [Github](https://github.com/coffee-cup/unnamed-language)._

## Compiling

The compiler is built with `make`.

- `make grammar` runs Antlr to generate lexer and parser classes
- `make compiler` compiles the compiler
- `make clean` removes compile and build files

To do a full clean and build run,

```sh
make clean; make
```

## Running

The compiler can be run against language files to produce a pretty printed version of the file. Options can be specified to change the default behaviour.

The built compiler is located in the `bin/` directory.

```sh
cd bin/
java Compiler path/to/file.ul
```

### Options

- `-o outfile` Specify a file to output the pretty printed language.
- `-s 1|0` Silent mode. If `1` then no output will be produced. Use this no just compile a file and check for errors.
- `-d 1|0` Dot mode. If `1` then the output is in the [DOT language](https://www.graphviz.org/doc/info/lang.html).

## Example

This following code finds the factorial of 8.

```c
int factorial (int n) {
  if (n == 1) {
    return 1;
  } else {
    return n*factorial(n-1);
  }
}

void main () {
  print "The factorial of 8 is ";
  println factorial(8);
}
```

The following AST is produced.

![image](https://user-images.githubusercontent.com/3044853/35426106-5b002308-0215-11e8-8ae8-3edc4e5a54c5.png)

## Dot Graphs

[Dot language](https://www.graphviz.org/doc/info/lang.html) programs can be produced with the `-d 1` option to the compiler.

For example, if you have this language

```c
// hello.ul
void main() {
    println "hello world";
}
```

You can compile it with

```bash
java Compiler -d 1 -o hello.dot hello.ul
```

You can then use the dot program to create an png image file and open it

```bash
dot -Tpng hello.dot -O && open hello.dot.png
```

The output should be

![image](https://user-images.githubusercontent.com/3044853/35428729-80cd5df2-0225-11e8-839d-5340dd7983af.png)

## Testing

There are a bunch of .ul language files that can be tested against the compiler. Throughout the course I will update the test script to check whether the latest requirements are met. Use the script `test.sh` to run all tests.

```sh
# Run the tests
./test.sh
```

## Licenses

All third party code is referenced in the LICENSES file.

## TODO

- [x] Lexer
- [x] Parser and AST generation
- [x] Pretty printing
- [x] Dot output
- [ ] Syntax analysis
- [ ] Type checking
- [ ] Intermediate code generation
- [ ] Register allocation
- [ ] Machine code generation
- [ ] Assembly and linking
