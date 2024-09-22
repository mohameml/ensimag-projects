import { StyleSheet, Text, Image, Pressable, View } from "react-native";
import React from "react";
import { useNavigation } from "@react-navigation/native";

const FoncView = ({ title, image, desc, color }) => {
    function handelPress() {
        console.log("Press ", title);
        navigation.navigate(title);
    }

    const navigation = useNavigation();

    return (
        <Pressable
            style={[styles.container, { backgroundColor: color }]}
            onPress={handelPress}
        >
            <View style={styles.content}>
                <Text style={styles.title}>{title}</Text>
                <Text style={styles.desc}>{desc}</Text>
            </View>
            <Image source={image} style={styles.img} />
        </Pressable>
    );
};

export default FoncView;

const styles = StyleSheet.create({
    container: {
        width: "45%",
        height: 190,
        borderRadius: 15,
        backgroundColor: "#deeefe",
        justifyContent: "center",
        alignItems: "center",
        padding: 5,
    },
    content: {
        width: "100%",
        felx: 0.4,
        justifyContent: "center",
        alignItems: "center",
    },
    img: {
        width: "100%",
        flex: 0.6,
        resizeMode: "contain",
        marginVertical: 10,
    },
    title: {
        fontSize: 22,
        fontWeight: "500",
        marginBottom: 10,
    },
    desc: {
        fontSize: 12,
        color: "#7e899b",
        textAlign: "center",
    },
});
