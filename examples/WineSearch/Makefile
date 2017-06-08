GRADLE ?= gradle
JAVA   ?= java


run: WineSearch.jar
	$(JAVA) -jar $< || echo

WineSearch.jar: WineSearch.java build.gradle ../../jastics.jar
	$(GRADLE) jar


clean:
	$(GRADLE) clean

realclean: clean
	rm -f WineSearch.jar


.PHONY: run clean realclean
