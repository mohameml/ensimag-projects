import { ScrollView, StyleSheet, Text, View } from "react-native";
import React, { useEffect, useState } from "react";
import Courbe from "../components/chart/Courbe";
import HealthLineChart from "../components/chart/chartLine";
import Bar from "../components/chart/Bar";
import { ActivityIndicator } from "react-native";

import { getHeartRatePerDay } from "../backend/DataSante";
import { useRoute } from "@react-navigation/native";

const Visualisation = () => {
    const route = useRoute();
    const dataUserSante = route.params.data;

    return (
        <ScrollView
            contentContainerStyle={{ backgroundColor: "white" }}
            showsVerticalScrollIndicator={false}
        >
            {/* chart 1 :  */}
            <Text style={styles.titleChart}>Mesure Cardiatique </Text>
            <HealthLineChart
                labels={dataUserSante.map((item) => item.date)}
                data={dataUserSante.map((item) => item.heartRate)}
            />
        </ScrollView>
    );
};

export default Visualisation;

const styles = StyleSheet.create({
    container: {
        flex: 1,
        // justifyContent: "center",
        // justifyContent: "flex-start",
        alignItems: "center",
    },
    titleChart: {
        textAlign: "center",
        fontSize: 20,
        fontWeight: "bold",
    },
});
