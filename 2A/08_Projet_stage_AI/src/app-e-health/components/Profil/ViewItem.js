import { StyleSheet, Text, TouchableOpacity, View } from "react-native";
import React from "react";
import { MaterialIcons } from "@expo/vector-icons";
import { AntDesign } from "@expo/vector-icons";
import colors from "../../constant/colors";
import { useNavigation } from "@react-navigation/native";

const ViewItem = ({ nameIcon, title, handelPress }) => {
    return (
        <TouchableOpacity style={styles.container} onPress={handelPress}>
            <View style={styles.viewTitle}>
                {/* <Icon /> */}
                <AntDesign name={nameIcon} size={24} color="black" />
                <Text style={styles.textTitle}>{title}</Text>
            </View>
            <View>
                <MaterialIcons name="navigate-next" size={24} color="black" />
            </View>
        </TouchableOpacity>
    );
};

export default ViewItem;

const styles = StyleSheet.create({
    container: {
        // backgroundColor: "red",
        flexDirection: "row",
        justifyContent: "space-between",
        padding: 5,
        marginVertical: 10,
        // backgroundColor: colors.background,
    },
    viewTitle: {
        flexDirection: "row",
        paddingLeft: 20,
    },
    textTitle: {
        fontSize: 15,
        fontWeight: "500",
        paddingLeft: 10,
    },
});
