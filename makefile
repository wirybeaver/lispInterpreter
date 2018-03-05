# Java Makefile
# Reference: http://www.voidcn.com/article/p-hkdryezy-ob.html

# Project Structure
# Makefile
# READMEP1
# /src
#	/*.java
# /target
# 	/*.class


# set the entry of the java application
ENTRY_POINT = part1Scan/App

# set your source file name
SOURCE_FILES = \
part1Scan/exception/LispException.java \
part1Scan/exception/InvalidSexpException.java \
part1Scan/exception/IncompletenessException.java \
part1Scan/enums/PrimitiveEnum.java \
part1Scan/enums/RunStateEnum.java \
part1Scan/enums/SexpTypeEnum.java \
part1Scan/TokenHandler.java \
part1Scan/Sexp.java \
part1Scan/utils/SexpUtil.java \
part1Scan/Parser.java \
part1Scan/App.java


# set file path related to the makefie location
TARGET = target
SOURCE = src

# set your java compiler:
JAVAC = javac

# set compiler option
ENCODING = -encoding UTF-8
JFLAGS = $(ENCODING)

vpath %.class $(TARGET)
vpath %.java $(SOURCE)

# show help message by default:
Default:
	@echo "make init: build the target directory."
	@echo "make build: compile the source file."
	@echo "make clean: clear classes generated."
	@echo "make rebuild: rebuild project."
	@echo "make run: run the application at the entry point."

build: $(SOURCE_FILES:.java=.class)

%.class: %.java
	$(JAVAC) -classpath $(TARGET) -d $(TARGET) $<

rebuild: clean build

.PHONY: init clean run

init:
	mkdir -pv $(TARGET)

clean:
	rm -frv $(TARGET)/*

run:
	java -classpath $(TARGET) $(ENTRY_POINT)
