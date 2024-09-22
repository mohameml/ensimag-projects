import { StyleSheet, Text, TouchableOpacity, View } from "react-native";
import React, { useEffect, useState } from "react";
import { useNavigation } from "@react-navigation/native";
import { Button } from "react-native-paper";

import Table from "../components/Table/Table";
import { getHeartRatePerDay, addNewMesure } from "../backend/DataSante";
import { ActivityIndicator } from "react-native";

import { Dimensions } from "react-native";

import {
    extractJourMoisAnneeFormatDDMMAAAA,
    reverseString,
    extractJourMoisAnneeFormatAAAAMMDD,
    isDateBetween,
} from "../utils/utils";

const Historique = () => {
    const navigation = useNavigation();

    const [loading, setLoading] = useState(true);
    const [dataUser, setDataUser] = useState([]);

    const handelFilter = (DateFrom, DateTo) => {
        const dataFilter = dataUser.filter((item) => {
            const { jour, mois, annee } = extractJourMoisAnneeFormatDDMMAAAA(
                item.date
            );
            const dateToCheck = annee + "-" + mois + "-" + jour;

            return isDateBetween(dateToCheck, DateFrom, DateTo);
        });

        setDataUser(dataFilter);
    };

    useEffect(() => {
        const fecthMesureSante = async () => {
            let count = 0;
            try {
                const mesuresUser = await getHeartRatePerDay();

                const mesureUserAvceNAN = mesuresUser.map((item) => {
                    count++;
                    let heartRate = Number(item.heartRate) ?? 0;
                    console.log("heartRate =", heartRate, isNaN(heartRate));

                    if (isNaN(heartRate)) {
                        heartRate = 0;
                        count--;
                    }
                    return {
                        key: count,
                        heartRate: heartRate,
                        date: item.date.slice(0, 10),
                    };
                });

                setDataUser(
                    mesureUserAvceNAN.filter((item) => item.heartRate != 0)
                );
            } catch (e) {
                console.log("Failed to fetch heart rate data", e);
            } finally {
                setLoading(false);
            }
        };
        fecthMesureSante();
    }, []);

    if (loading) {
        return (
            <View>
                <ActivityIndicator size={"large"} />
            </View>
        );
    }

    return (
        <View style={styles.container}>
            <View style={styles.btns}>
                <Button
                    mode="outlined"
                    onPress={() =>
                        navigation.navigate("Visualisation", { data: dataUser })
                    }
                >
                    visualiser
                </Button>

                <Button
                    mode="outlined"
                    onPress={() => {
                        navigation.navigate("filter", {
                            handelFilter: handelFilter,
                        });
                    }}
                >
                    filtrer
                </Button>
            </View>
            <Table items={dataUser} />
        </View>
    );
};

export default Historique;

const styles = StyleSheet.create({
    container: {
        flex: 1,
        // justifyContent: "center",
        alignItems: "center",
        backgroundColor: "white",
    },

    btns: {
        // backgroundColor: "red",
        width: "100%",
        padding: 5,
        flexDirection: "row",
        justifyContent: "flex-end",
        gap: 10,
    },
    moadlConatiner: {
        width: "100%",
        height: Dimensions.get("window"),
        // backgroundColor: "white",
    },

    modalContainer: {
        backgroundColor: "red",
        width: "100%",
    },
});
