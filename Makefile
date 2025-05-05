JAVAC = javac
JAVA  = java
SRC   = Main.java
CLASS = Main

.PHONY: all compile run clean

all: compile

# Compile .java → .class
compile: $(SRC)
	$(JAVAC) $(SRC)

# Run the program (will compile first if needed)
run: compile
	$(JAVA) $(CLASS)

# Remove all .class files
clean:
	rm -f *.class
