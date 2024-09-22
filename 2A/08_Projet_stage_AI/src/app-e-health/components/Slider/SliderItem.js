import {
    StyleSheet,
    Text,
    View,
    Image,
    Dimensions,
    Animated,
    Easing,
} from "react-native";
import React from "react";
import colors from "../../constant/colors";

const { width, height } = Dimensions.get("screen");

const SliderItem = ({ item, full }) => {
    const translateImage = new Animated.Value(20);

    Animated.timing(translateImage, {
        toValue: 0,
        duration: 1000,
        useNativeDriver: true,
        easing: Easing.bounce,
    }).start();

    const stylesContainer =
        full === true ? styles.conatainerFull : styles.container;

    return (
        <View style={stylesContainer}>
            <Animated.Image
                source={item.image}
                style={[
                    styles.img,
                    {
                        transform: [
                            {
                                translateY: translateImage,
                            },
                        ],
                    },
                ]}
            />
            <View style={styles.content}>
                <Text style={styles.title}>{item.title}</Text>
                <Text style={styles.description}>{item.description}</Text>
            </View>
        </View>
    );
};

export default SliderItem;

const styles = StyleSheet.create({
    container: {
        width: width,
        height: 250,
        alignItems: "center",
        backgroundColor: "white",
    },
    conatainerFull: {
        width: width,
        height: height,
        alignItems: "center",
        backgroundColor: "white",
    },
    img: {
        width: "100%",
        flex: 0.6,
        resizeMode: "contain",
        // backgroundColor: "blue",
    },
    content: {
        width: "100%",
        flex: 0.4,
        alignItems: "center",
        // backgroundColor: "red",
    },
    title: {
        fontSize: 24,
        fontWeight: "bold",
        color: "#333",
    },
    description: {
        fontSize: 15,
        textAlign: "center",
        color: colors.secondary,
        marginVertical: 5,
    },
});
