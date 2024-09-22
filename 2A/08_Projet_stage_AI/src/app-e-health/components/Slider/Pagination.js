import { StyleSheet, Text, View, Animated, Dimensions } from "react-native";
import React from "react";

const { width } = Dimensions.get("screen");

const Pagination = ({ data, scrollX, index }) => {
    return (
        <View style={styles.container}>
            {/* <View style={styles.cureent} /> */}
            {data.map((_, idx) => {
                const inputRange = [
                    (idx - 1) * width,
                    idx * width,
                    (idx + 1) * width,
                ];
                const doWidth = scrollX.interpolate({
                    inputRange,
                    outputRange: [12, 30, 12],
                    extrapolate: "clamp",
                });

                const backgroundColor = scrollX.interpolate({
                    inputRange,
                    outputRange: ["#ccc", "#000", "#ccc"],
                    extrapolate: "clamp",
                });

                return (
                    <Animated.View
                        key={idx.toString()}
                        style={[
                            styles.cercle,
                            {
                                width: doWidth,
                                backgroundColor: backgroundColor,
                            },
                            // idx === index && styles.dotActive,
                        ]}
                    />
                );
            })}
        </View>
    );
};

export default Pagination;

const styles = StyleSheet.create({
    container: {
        width: "100%",
        position: "absolute",
        backgroundColor: "white",
        bottom: 20,
        flexDirection: "row",
        justifyContent: "center",
        gap: 5,
    },
    cercle: {
        width: 12,
        height: 12,
        borderRadius: 6,
        backgroundColor: "#ccc",
    },
    cureent: {
        width: 20,
        height: 12,
        borderRadius: 6,
        backgroundColor: "black",
    },
    dotActive: {
        backgroundColor: "black",
    },
});
