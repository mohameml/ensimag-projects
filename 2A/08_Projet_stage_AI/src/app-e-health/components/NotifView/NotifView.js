import { StyleSheet, Text, View } from "react-native";
import React from "react";
import colros from "../../constant/colors";
const NotifView = ({ title, message }) => {
    return (
        <View style={styles.container}>
            <Text style={styles.title}>{title}</Text>
            <Text style={styles.msg}>{message}</Text>
        </View>
    );
};

export default NotifView;

const styles = StyleSheet.create({
    container: {
        width: "100%",
        backgroundColor: "red",
        padding: 10,
        marginBottom: 15,
        borderRadius: 10,
        backgroundColor: colros.primary,
    },
    title: {
        fontSize: 15,
        fontWeight: "bold",
        color: "white",
    },
    msg: {
        marginVertical: 5,
        color: "white",
    },
});
