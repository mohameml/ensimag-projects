# ================================== Makefile du projetc ACOL : =================================




#  les répertoires pour les fichiers source ,  les fichiers compilés les fichiers JAR externes
SRCDIR := src
BINDIR := bin
LIBDIR := lib



# les noms des fichiers JAR externes

JFREECHART_JAR := jfreechart.jar
JCOMMON_JAR := jcommon.jar
OJDBC_JAR := sqlite-jdbc.jar
JAMA_JAR := jama-1.0.3.jar



# Spécifiez les fichiers source de votre projet
SOURCES := $(wildcard $(SRCDIR)/*.java)

# le nom de notre classe principale
MAINCLASS := App

# chemin du fichier excécutable :

CLASSPATH := $(BINDIR):$(LIBDIR)/$(JFREECHART_JAR):$(LIBDIR)/$(JCOMMON_JAR):$(LIBDIR)/$(OJDBC_JAR)

# CLASSPATH := $(BINDIR):$(LIBDIR)/$(JFREECHART_JAR):$(LIBDIR)/$(JCOMMON_JAR):$(LIBDIR)/$(JAMA_JAR)


# Options de compilation

JAVAC := javac
JAVACFLAGS := -d $(BINDIR) -sourcepath $(SRCDIR) -classpath $(CLASSPATH)


# compilation du projet : avce make 
compile:
	
	$(JAVAC) $(JAVACFLAGS) $(SOURCES)
	


# lancer l'Application : make run 

run: compile
	java -classpath $(CLASSPATH) $(MAINCLASS)


## pour les docs :

doc : 
	find docs/latex/ -name "*.tex" -exec pdflatex -output-directory=docs/latex/out/ {} \;
	cp docs/latex/out/*.pdf docs/


# clean projet 

clean:
	rm -rf $(BINDIR)/*