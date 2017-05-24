GRADLE ?= gradle
GIT    ?= git
PATCH  ?= patch
JAVA   ?= java


test: src/main/jastadd/PrettyPrint.jadd | extendj/build.gradle
	$(GRADLE) cleanTest check

extendj/build.gradle:
	$(GIT) submodule update --init
	$(PATCH) -p0 <patches/java4.patch
	$(PATCH) -p0 <patches/java5.patch


src/main/jastadd/PrettyPrint.jadd: src/main/pretty-print/SemanticsPrettyPrint.tt | aspectgen/aspectgen.jar
	$(JAVA) -jar aspectgen/aspectgen.jar -aspect SemanticsPrettyPrint $< > $@

aspectgen/aspectgen.jar: | aspectgen
	cd aspectgen && $(GRADLE) jar

aspectgen:
	$(GIT) clone --recursive 'https://bitbucket.org/joqvist/aspectgen'


gradle-wrapper: gradle gradlew gradlew.bat

clean-gradle-wrapper:
	rm -rf gradle gradlew gradlew.bat

gradle: extendj/gradle
	cp -r $< $@

gradlew: extendj/gradlew
	cp $< $@

gradlew.bat: extendj/gradlew.bat
	cp $< $@


clean:
	$(GRADLE) clean


.PHONY: test gradle-wrapper clean-gradle-wrapper clean
