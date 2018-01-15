# The Unnamed Language

Programming language for the UVic CSC 435 Compiler Construction class.

## Compiling

The language is built with `make`.

- `make grammar` runs Antlr to generate lexer and parser classes
- `make compiler` compiles the compiler
- `make clean` removes compile and build files

To do a full clean and build run,

```sh
make clean; make
```

## Running

The compiler can be run against language files to determine whether or not the files are in the ul language.

```sh
java Compiler path/to/file.ul
```

If the file is in the language then there will be no output. Otherwise, a lexer or parser error will be displayed.

## Testing

For the first assignment there are a bunch of .ul language files that can be tested against the parser. Files in tests/accept should all pass and be in the language. Files in tests/reject should all fail and not be in the language. Use the script test.sh to run on the tests.

```sh
# Run the tests
./test.sh
```

## TODO

- [x] Lexer
- [ ] Parser and AST generation
- [ ] Syntax analysis
- [ ] Type checking
- [ ] Intermediate code generation
- [ ] Register allocation
- [ ] Machine code generation
- [ ] Assembly and linking
