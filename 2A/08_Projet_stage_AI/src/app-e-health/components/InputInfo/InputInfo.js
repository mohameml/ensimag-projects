import { StyleSheet, Text, View } from "react-native";
import React from "react";
import Input from "../Input/Input";

const InputInfo = (props) => {
    const { label, info, setInfo, color } = props;
    return (
        <View style={styles.container}>
            <Text style={{ fontSize: 15, fontWeight: "500", padding: 5 }}>
                {label}
            </Text>
            <Input
                title={label}
                value={info}
                setValue={setInfo}
                color={color}
                {...props}
            />
        </View>
    );
};

export default InputInfo;

const styles = StyleSheet.create({
    container: {
        width: "90%",
    },
});
