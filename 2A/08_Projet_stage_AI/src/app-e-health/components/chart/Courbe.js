import React from "react";
import { View, Dimensions, StyleSheet } from "react-native";
import { LineChart } from "react-native-chart-kit";

const screenWidth = Dimensions.get("window").width;

const data = {
    labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun"],
    datasets: [
        {
            data: [20, 45, 28, 80, 99, 43],
        },
    ],
    legend: ["Health Data"],
};

const chartConfig = {
    backgroundColor: "#e26a00",
    backgroundGradientFrom: "#fb8c00",
    backgroundGradientTo: "#ffa726",
    color: (opacity = 1) => `rgba(255, 255, 255, ${opacity})`,
    labelColor: (opacity = 1) => `rgba(255, 255, 255, ${opacity})`,
};
const Courbe = () => {
    return (
        <View style={styles.container}>
            <LineChart
                data={data}
                width={screenWidth - 16}
                height={200}
                chartConfig={chartConfig}
                style={styles.chart}
                bezier
            />
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        // flex: 1,
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: "#fff",
        padding: 8,
    },
    chart: {
        marginVertical: 8,
        borderRadius: 16,
    },
});

export default Courbe;
