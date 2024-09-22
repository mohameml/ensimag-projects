import { StyleSheet, TextInput, View } from "react-native";
import React from "react";

const Input = (props) => {
    const { title, value, setValue, color } = props;
    return (
        <View>
            <TextInput
                placeholder={title}
                value={value}
                onChangeText={(text) => setValue(text)}
                style={[styles.input, { borderColor: color }]}
                {...props}
            />
        </View>
    );
};

export default Input;

const styles = StyleSheet.create({
    input: {
        backgroundColor: "white",
        color: "black",
        paddingHorizontal: 15,
        paddingVertical: 10,
        borderRadius: 10,
        marginBottom: 10,
        borderWidth: 2,
        borderColor: "black",
    },
});
