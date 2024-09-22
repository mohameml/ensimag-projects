#! /bin/sh

# Auteur : gl10
# historique : 11/01/2024


# cd "$(dirname "$0")"/../../.. || exit 1

# PATH=./src/test/script/launchers:"$PATH"


echo "============================= invalide : test-context sans objet  =============================="

test_context_invalide() {
    
    if test_context "$1" 2>&1  | grep  -q -e "$1:[0-9][0-9]*:[0-9][0-9]*"
    then
        echo "Echec attendu pour test_context sur $1"
    else
        echo "Succes inattendu de test_context  $1"
        exit 1
    fi
}


for cas_de_test in ./src/test/deca/context/invalid/sans_objet/*.deca
do
    test_context_invalide "$cas_de_test"
done



