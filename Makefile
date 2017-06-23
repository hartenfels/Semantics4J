GRADLE ?= gradle
GIT    ?= git
PATCH  ?= patch
JAVA   ?= java

SOURCE_FILES = $(shell find src/main -type f)


all: src/main/jastadd/PrettyPrint.jadd | extendj/build.gradle
	$(GRADLE) check >gradle.log
	@echo
	@echo
	@echo -e "\e[32mjastics.jar built successfully!\e[0m"
	@echo
	@echo -e 'You can try running some examples now:'
	@$(MAKE) -s list-examples
	@echo

list-examples:
	@echo
	@for file in examples/*.java; do \
		name="$$(basename $$file | sed s/\.java//)"; \
		echo -e "\e[36m - make $$name.example\e[0m"; \
	done
	@echo


test: src/main/jastadd/PrettyPrint.jadd | extendj/build.gradle
	$(GRADLE) cleanTest check

jastics.jar: src/main/jastadd/PrettyPrint.jadd $(SOURCE_FILES) build.gradle | extendj/build.gradle
	$(GRADLE) jar

extendj/build.gradle:
	$(GIT) submodule update --init
	$(PATCH) -p0 <patches/java4.patch
	$(PATCH) -p0 <patches/java5.patch


%.example: examples/%.class jastics.jar
	$(JAVA) -cp examples:jastics.jar $*

examples/%.class: examples/%.java jastics.jar
	$(JAVA) -jar jastics.jar $< -cp jastics.jar


src/main/jastadd/PrettyPrint.jadd: src/main/pretty-print/SemanticsPrettyPrint.tt | aspectgen/aspectgen.jar
	$(JAVA) -jar aspectgen/aspectgen.jar -aspect SemanticsPrettyPrint $< > $@

aspectgen/aspectgen.jar: | aspectgen
	cd aspectgen && $(GRADLE) jar

aspectgen:
	$(GIT) clone --recursive 'https://bitbucket.org/joqvist/aspectgen'


clean:
	$(GRADLE) clean
	rm -f examples/*.class src/test/resources/integration/*.class gradle.log

realclean: clean
	rm -f jastics.jar


.PHONY: all list-examples test clean realclean
.PRECIOUS: examples/%.class
