import { StyleSheet } from "react-native";
import colors from "./colors";

const gloablStyles = StyleSheet.create({
    full: {
        flex: 1,
        width: "100%",
    },
    flexCenter: {
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
    },
    container: {
        width: "100%",
        flex: 1,
        backgroundColor: colors.background,
        alignItems: "center",
        justifyContent: "center",
    },
    description: {
        fontSize: 30,
        fontWeight: "bold",
        textAlign: "center",
        marginBottom: 20,
        marginTop: 20,
    },
    sousDescription: {
        fontSize: 16,
        textAlign: "center",
        color: colors.secondary,
        marginBottom: 50,
    },
});

export default gloablStyles;
