#! /bin/sh

# Auteur : gl10
# Version initiale : 01/01/2024

# Test de compilation sans options.
# On lance decac sur les tests valides.



cd "$(dirname "$0")"/../../.. || exit 1

# PATH=./src/test/script/launchers:"$PATH"



decac_valide() {
    # $1 = premier argument.
    if ! decac "$1" > /dev/null 2>&1; then
        echo "Echec inattendu de decac sur $1."
        exit 1
    else
      echo "Succes attendu pour decac sur $1."
    fi
}

for cas_de_test in ./src/test/deca/codegen/valid/Complet/*.deca
do
    decac_valide "$cas_de_test"
done



ima_valide () {
    # $1 = premier argument.ima
    if ima "$1" 2>&1 | grep -q "IMA"
    then
        echo "Echec inattendu pour ima sur $1."
        exit 1
    else
        echo "Succès attendu pour ima sur $1."
    fi

}


for cas_de_test in ./src/test/deca/codegen/valid/Complet/*.ass
do
    ima_valide "$cas_de_test"
done

decac_valide_verif() {
    # $1 = premier argument.
    if ! decac -v "$1" > /dev/null 2>&1; then
        echo "Echec inattendu de decac -v sur $1."
        exit 1
    else
      echo "Succes attendu pour decac -v sur $1."
    fi
}
for cas_de_test in ./src/test/deca/codegen/valid/Complet/*.deca
do
    decac_valide_verif "$cas_de_test"
done




exit 0







