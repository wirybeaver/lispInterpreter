# Java Makefile
# Reference: http://www.voidcn.com/article/p-hkdryezy-ob.html

# Project Structure
# Makefile
# READMEP1
# /src
#	/*.java
# /target
# 	/*.class

# set the name of your jar package:
JAR_PKG = app.jar

# set the entry of the java application
ENTRY_POINT = App

# set your source file name
SOURCE_FILE = \
part1Scan.APP.java \
part1Scan.Parser.java \
part1Scan.Sexp.java \
part1Scan.TokenHandler.java \
part1Scan.utils.SexpUtil.java \
part1Scan.exception.InvalidSexpException.java \
part1Scan.exception.IncompletenessException.java \
part1Scan.exception.LispException \
part1Scan.enums.PrimitiveEnum.java \
part1Scan.enums.RunStateEnum.java

# set file path
TARGET = ./target
SOURCE = ./src

# set your java compiler:
JAVAC = javac

# set compiler option
ENCODING = -encoding UTF-8
SOURCEPATH = -sourcepath $(SOURCE)
D = -d $(TARGET)
JFLAGS = $D $(SOURCEPATH) $(ENCODING)

vpath %.class $(TARGET)
vpath %.java $(SOURCE)

# show help message by default:
Default:
	@echo "make init: build the target directory."
	@echo "make build: compile the source file."
	@echo "make clean: clear classes generated."
	@echo "make rebuild: rebuild project."
	@echo "make run: run your application at the entry point."

build: $(SOURCE_FILE:.java=.class)
%.class: %.java
	$(JAVAC) $(JFLAGS) $<

rebuild: clean build

.PHONY: init clean run jar

init:
	mkdir -pv $(TARGET)

clean:
	rm -frv $(TARGET)/*.class

run:
	java $(ENTRY_POINT)

jar:
	jar cvfe $(JAR_PKG) $(ENTRY_POINT) -C $(TARGET) .
