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
mkdir -p ./src/test/deca/syntax/valid/HelloWorld/lex
mkdir -p ./src/test/deca/syntax/valid/HelloWorld/lex/expected
mkdir -p ./src/test/deca/syntax/valid/HelloWorld/lex/sortie

# Stockage manuelle des résultats attendu des tests
echo "OBRACE: [@0,128:128='{',<3>,10:0]
PRINTLN: [@1,134:140='println',<11>,11:4]
OPARENT: [@2,141:141='(',<7>,11:11]
STRING: [@3,142:148='\"Hello\"',<40>,11:12]
CPARENT: [@4,149:149=')',<8>,11:19]
SEMI: [@5,150:150=';',<5>,11:20]
PRINT: [@6,156:160='print',<9>,12:4]
OPARENT: [@7,161:161='(',<7>,12:9]
STRING: [@8,162:186='\"hello World hello again\"',<40>,12:10]
CPARENT: [@9,187:187=')',<8>,12:35]
SEMI: [@10,188:188=';',<5>,12:36]
PRINTX: [@11,194:199='printx',<10>,13:4]
OPARENT: [@12,200:200='(',<7>,13:10]
STRING: [@13,201:213='\"how are you\"',<40>,13:11]
CPARENT: [@14,214:214=')',<8>,13:24]
SEMI: [@15,215:215=';',<5>,13:25]
PRINTX: [@16,221:226='printx',<10>,14:4]
OPARENT: [@17,227:227='(',<7>,14:10]
STRING: [@18,228:242='\"fine and you?\"',<40>,14:11]
CPARENT: [@19,243:243=')',<8>,14:26]
SEMI: [@20,244:244=';',<5>,14:27]
PRINT: [@21,250:254='print',<9>,15:4]
OPARENT: [@22,255:255='(',<7>,15:9]
STRING: [@23,256:262='\"World\"',<40>,15:10]
CPARENT: [@24,263:263=')',<8>,15:17]
SEMI: [@25,264:264=';',<5>,15:18]
PRINTX: [@26,270:275='printx',<10>,16:4]
OPARENT: [@27,276:276='(',<7>,16:10]
STRING: [@28,277:279='\"!\"',<40>,16:11]
CPARENT: [@29,280:280=')',<8>,16:14]
SEMI: [@30,281:281=';',<5>,16:15]
PRINTLNX: [@31,287:294='printlnx',<12>,17:4]
OPARENT: [@32,295:295='(',<7>,17:12]
STRING: [@33,296:298='\"!\"',<40>,17:13]
CPARENT: [@34,299:299=')',<8>,17:16]
SEMI: [@35,300:300=';',<5>,17:17]
CBRACE: [@36,302:302='}',<4>,18:0]" > ./src/test/deca/syntax/valid/HelloWorld/lex/expected/affichage_string.lis

echo "OBRACE: [@0,152:152='{',<3>,10:0]
PRINT: [@1,158:162='print',<9>,11:4]
OPARENT: [@2,163:163='(',<7>,11:9]
STRING: [@3,164:190='\"helloo world! \\\"je suis !\"',<40>,11:10]
CPARENT: [@4,191:191=')',<8>,11:37]
SEMI: [@5,192:192=';',<5>,11:38]
CBRACE: [@6,194:194='}',<4>,12:0]" > ./src/test/deca/syntax/valid/HelloWorld/lex/expected/guillemet_double_protected.lis

echo "OBRACE: [@0,120:120='{',<3>,10:0]
CBRACE: [@1,127:127='}',<4>,12:0]" > ./src/test/deca/syntax/valid/HelloWorld/lex/expected/main_vide.lis

echo "OBRACE: [@0,129:129='{',<3>,10:0]
PRINTLN: [@1,135:141='println',<11>,11:4]
OPARENT: [@2,142:142='(',<7>,11:11]
STRING: [@3,143:152='\"Un print\"',<40>,11:12]
CPARENT: [@4,153:153=')',<8>,11:22]
SEMI: [@5,154:154=';',<5>,11:23]
CBRACE: [@6,156:156='}',<4>,12:0]" > ./src/test/deca/syntax/valid/HelloWorld/lex/expected/println.lis

echo "OBRACE: [@0,146:146='{',<3>,10:0]
PRINT: [@1,152:156='print',<9>,11:4]
OPARENT: [@2,157:157='(',<7>,11:9]
CPARENT: [@3,158:158=')',<8>,11:10]
SEMI: [@4,159:159=';',<5>,11:11]
CBRACE: [@5,161:161='}',<4>,12:0]" > ./src/test/deca/syntax/valid/HelloWorld/lex/expected/print_vide.lis

echo -n > ./src/test/deca/syntax/valid/HelloWorld/lex/expected/vide.lis

cd ./src/test/deca/syntax/valid/HelloWorld/


# Test de non regression

for f in *.deca
do
  test_lex "$f" > "./lex/sortie/${f%.deca}.lis" 2>&1
  if diff "./lex/sortie/${f%.deca}.lis" "./lex/expected/${f%.deca}.lis" >/dev/null
  then
      :
  else
      echo "Echec inattendu pour $f"
      exit 1
  fi
done



# A faire les tests invalides




exit 0
