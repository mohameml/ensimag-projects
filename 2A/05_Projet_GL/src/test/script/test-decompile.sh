#! /bin/sh
echo "============================= decompilation et comparaison ============================="


for cas_de_test in src/test/deca/syntax/valid/Objet/*.deca
do
    decac -p "$cas_de_test" > test.deca
    decac -p test.deca > test2.deca

    if diff test.deca test2.deca >/dev/null; then
        
        echo "Test case $cas_de_test: Comparaison PASSED"
    else
        echo "Test case $cas_de_test: comparaison FAILED"
    fi
done
rm test.deca
rm test2.deca
