import { StyleSheet, Text, View, TouchableOpacity } from "react-native";
import React from "react";

const Button = (props) => {
    const { title, handelOnPress } = props;
    return (
        <View style={styles.btnContainer}>
            <TouchableOpacity style={styles.btn} onPress={handelOnPress}>
                <Text style={styles.btnText}>{title}</Text>
            </TouchableOpacity>
        </View>
    );
};

export default Button;

const styles = StyleSheet.create({
    btnContainer: {
        justifyContent: "center",
        alignItems: "center",
        width: "100%",
        marginTop: 30,
    },
    btn: {
        backgroundColor: "#0782F9",
        padding: 10,
        borderRadius: 15,
        width: "80%",
    },
    btnText: {
        color: "white",
        fontSize: 20,
        textAlign: "center",
    },
});
