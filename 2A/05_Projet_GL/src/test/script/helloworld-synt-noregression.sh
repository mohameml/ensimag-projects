#! /bin/sh

# Auteur : gl10
# Version initiale : 04/01/2024

# Base pour un script de test de la lexicographie.
# On teste tous les fichiers valides et invalides de deca/syntax/valid/HelloWorld/ et deca/syntax/invalid/HelloWorld/  .


# On se place dans le répertoire du projet (quel que soit le
# répertoire d'où est lancé le script) :
cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers:"$PATH"



# TESTS VALIDES
mkdir -p ./src/test/deca/syntax/valid/HelloWorld/synt
mkdir -p ./src/test/deca/syntax/valid/HelloWorld/synt/expected
mkdir -p ./src/test/deca/syntax/valid/HelloWorld/synt/sortie

# Stockage manuelle des résultats attendu des tests
echo "\`> [10, 0] Program
   +> ListDeclClass [List with 0 elements]
   \`> [10, 0] Main
      +> ListDeclVar [List with 0 elements]
      \`> ListInst [List with 7 elements]
         []> [11, 4] Println
         ||  \`> ListExpr [List with 1 elements]
         ||     []> [11, 12] StringLiteral (Hello)
         []> [12, 4] Print
         ||  \`> ListExpr [List with 1 elements]
         ||     []> [12, 10] StringLiteral (hello World hello again)
         []> [13, 4] Print
         ||  \`> ListExpr [List with 1 elements]
         ||     []> [13, 11] StringLiteral (how are you)
         []> [14, 4] Print
         ||  \`> ListExpr [List with 1 elements]
         ||     []> [14, 11] StringLiteral (fine and you?)
         []> [15, 4] Print
         ||  \`> ListExpr [List with 1 elements]
         ||     []> [15, 10] StringLiteral (World)
         []> [16, 4] Print
         ||  \`> ListExpr [List with 1 elements]
         ||     []> [16, 11] StringLiteral (!)
         []> [17, 4] Println
             \`> ListExpr [List with 1 elements]
                []> [17, 13] StringLiteral (!)" > ./src/test/deca/syntax/valid/HelloWorld/synt/expected/affichage_string.lis

echo "\`> [10, 0] Program
   +> ListDeclClass [List with 0 elements]
   \`> [10, 0] Main
      +> ListDeclVar [List with 0 elements]
      \`> ListInst [List with 1 elements]
         []> [11, 4] Print
             \`> ListExpr [List with 1 elements]
                []> [11, 10] StringLiteral (helloo world! \\\"je suis !)" > ./src/test/deca/syntax/valid/HelloWorld/synt/expected/guillemet_double_protected.lis

echo "\`> [10, 0] Program
   +> ListDeclClass [List with 0 elements]
   \`> [10, 0] Main
      +> ListDeclVar [List with 0 elements]
      \`> ListInst [List with 0 elements]" > ./src/test/deca/syntax/valid/HelloWorld/synt/expected/main_vide.lis

echo "\`> [10, 0] Program
   +> ListDeclClass [List with 0 elements]
   \`> [10, 0] Main
      +> ListDeclVar [List with 0 elements]
      \`> ListInst [List with 1 elements]
         []> [11, 4] Println
             \`> ListExpr [List with 1 elements]
                []> [11, 12] StringLiteral (Un print)" > ./src/test/deca/syntax/valid/HelloWorld/synt/expected/println.lis

echo "\`> [10, 0] Program
   +> ListDeclClass [List with 0 elements]
   \`> [10, 0] Main
      +> ListDeclVar [List with 0 elements]
      \`> ListInst [List with 1 elements]
         []> [11, 4] Print
             \`> ListExpr [List with 0 elements]" > ./src/test/deca/syntax/valid/HelloWorld/synt/expected/print_vide.lis

echo "\`> [9, 0] Program
   +> ListDeclClass [List with 0 elements]
   \`> EmptyMain" > ./src/test/deca/syntax/valid/HelloWorld/synt/expected/vide.lis

cd ./src/test/deca/syntax/valid/HelloWorld/


# Test de non regression

for f in *.deca
do
  test_synt "$f" > "./synt/sortie/${f%.deca}.lis" 2>&1
  if diff "./synt/sortie/${f%.deca}.lis" "./synt/expected/${f%.deca}.lis" >/dev/null
  then
      :
  else
      echo "Echec inattendu pour $f"
      exit 1
  fi
done



# A faire les tests invalides




exit 0
