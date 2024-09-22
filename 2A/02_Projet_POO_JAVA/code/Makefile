# Example de makefile pour compiler le squelette de code distribué
# Vous pouvez compléter ce makefile, mais étant donnée la taille du projet, 
# il est FORTEMENT recommandé d'utiliser un IDE!

# Organisation:
#  1) Les sources (*.java) se trouvent dans le répertoire src
#     Les classes d'un package toto sont dans src/toto
#     Les classes du package par defaut sont dans src
#
#  2) Les bytecodes (*.class) sont générés dans le répertoire bin
#     La hiérarchie des sources (par package) est conservée.
#
#  3) Une librairie gui.jar est distribuée pour l'interface grapique. 
#     Elle se trouve dans le sous-répertoire lib.
#
# Compilation:
#  Options de javac:
#   -d : répertoire dans lequel sont générés les .class compilés
#   -sourcepath : répertoire dans lequel sont cherchés les .java
#   -classpath : répertoire dans lequel sont cherchées les classes compilées (.class et .jar)



# ----------------- test Invader ---------------------- 

compileTestInvader:
	javac -d bin -classpath lib/gui.jar -sourcepath src/ src/TestInvader.java

runTestInvader: compileTestInvader
	java -classpath bin:lib/gui.jar TestInvader




# -------------------- Test Balles -----------------------------


compileTestBalles :
	javac -d ./bin  -cp ./lib/gui.jar -sourcepath src/ src/TestBalles.java

runTestBalles: compileTestBalles 
	java -classpath bin:lib/gui.jar TestBalles



# ------------------------------ test GUI -------------------------------- 

compileTestGUI:
	javac -d bin -classpath lib/gui.jar src/TestGUISimulator.java

runTestGUI: compileTestGUI
	java -classpath bin:lib/gui.jar  TestGUISimulator


# ---------------------------- Test Balles Simulation ------------------------------- 

compileTestBallesSimulator :
		javac -d bin -classpath lib/gui.jar  -sourcepath src/ src/TestBallesSimulator.java



runTestBallesSimulator : compileTestBallesSimulator

	java -classpath bin:lib/gui.jar TestBallesSimulator 



# ------------------------------ test jeu conway ------------------------------- 
compileTestJeuConWay :
	javac -d bin -classpath lib/gui.jar  -sourcepath src/ src/TestJeuConWay.java


runTestJeuConWay: compileTestJeuConWay
	java -classpath bin:lib/gui.jar TestJeuConWay 



# --------------------------- Test de jeu de l'immigration ---------------------- 
compileTestJeuImmigration :
	javac -d bin -classpath lib/gui.jar  -sourcepath src/ src/TestJeuImmigration.java



runTestJeuImmigration: compileTestJeuImmigration
	java -classpath bin:lib/gui.jar TestJeuImmigration


# ------------------------------- Test du  modèle de Schelling ------------------------- 

compileTestSchelling :

	javac -d ./bin/ -classpath lib/gui.jar -sourcepath src/ src/TestSchellingSimulator.java


runTestSchelling : compileTestSchelling

	java -cp bin:lib/gui.jar TestSchellingSimulator



# ------------------------------- Test  de Boids : ------------------------------------- 

compileTestBoids :
		javac -d ./bin/ -classpath lib/gui.jar -sourcepath src/ src/TestBoids.java

runTestBoids : compileTestBoids 
	java -cp bin:lib/gui.jar TestBoids 


# --------------------------------------- Test de Vceteur : ------------------------------ 


compileTestVecteur :
	javac -d ./bin -sourcepath src/ src/TestVecteur.java

runTestVecteur : compileTestVecteur
	java -cp ./bin/ TestVecteur 



# ------------------------------------  Test de event : ------------------------------ 

compileTestEvent :
	javac -d ./bin/ -sourcepath ./src/ ./src/TestEventManager.java

runTestEvent : compileTestEvent
	java -cp ./bin TestEventManager 

clean:
	rm -r bin/*


