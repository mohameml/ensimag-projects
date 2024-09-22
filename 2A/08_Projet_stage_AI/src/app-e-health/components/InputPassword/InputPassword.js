import { StyleSheet, Text, View, Pressable, TextInput } from "react-native";
import { Entypo } from "@expo/vector-icons";
import { useState } from "react";

const InputPassword = ({ password, setPassword }) => {
    const [show, setShow] = useState(true);
    const [nameIcon, setNameIcon] = useState("eye-with-line");

    function handelIconPress() {
        setShow(!show);
        if (!show) {
            setNameIcon("eye-with-line");
        } else {
            setNameIcon("eye");
        }
    }

    return (
        <View style={styles.container}>
            <TextInput
                placeholder="mot de passe"
                value={password}
                onChangeText={(text) => setPassword(text)}
                style={styles.input}
                secureTextEntry={show}
            />
            <Pressable style={styles.icon} onPress={handelIconPress}>
                <Entypo name={nameIcon} size={20} color="black" />
            </Pressable>
        </View>
    );
};

export default InputPassword;

const styles = StyleSheet.create({
    container: {
        // width: "100%",
    },
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
    icon: {
        position: "absolute",
        left: 280,
        top: "25%",
    },
});
