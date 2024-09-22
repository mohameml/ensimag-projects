# Projet : **couverture de produits dérivés**

## 1. **Description:**

-   Ce projet a pour but de développer un outil de valorisation et de couverture de produits financiers en C++ en mettant en application d'une part les principes de conception et de programmation étudiés dans les cours de 2A et d'autre part la théorie de Black Scholes présentée dans le cours Introduction aux Produits Dérivés.

## 2. **compilation:**

-   **Installation du `PNL`:**

[lien_vers_pnl](https://github.com/pnlnum/pnl/releases)

```bash
mkdir lib
cd lib
cp /relative/path/to/pnl ./
mkdir build
cd  build
cmake ..
chmod +x ../split_linker_command.sh
make
make install
```

-   **Installation du `nlohmann-json`:**

```bash
sudo apt update
sudo apt install nlohmann-json3-dev
```

-   **compilation:**

```bash
mkdir build
cd build
cmake -DCMAKE_PREFIX_PATH=relative/path/to/lib/pnl/build/ ..
make
```

-   **Exécution:**

```bash
./price0 data_input.json
./hedge market_file.txt data_input.json
```
