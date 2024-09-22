#! /bin/sh

# Auteur : gl10
# historique : 11/01/2024


# cd "$(dirname "$0")"/../../.. || exit 1

# PATH=./src/test/script/launchers:"$PATH"


echo "============================= valide  : test-context sans objet =============================="

test_context_valide() {
    
    if test_context "$1" 2>&1  | grep  -q -e "$1:[0-9][0-9]*:[0-9][0-9]*"
    then
        echo "Echec inattendu pour test_context sur $1"
        exit 1
    else
        echo "Succes attendu de test_context  $1"
        
    fi
}


for cas_de_test in ./src/test/deca/context/valid/sans_objet/*.deca
do
    test_context_valide "$cas_de_test"
done



