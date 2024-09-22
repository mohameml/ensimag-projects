import { View, Image, Text, Pressable, StyleSheet } from "react-native";
import colors from "./../constant/colors";
function LandigPage({ navigation }) {
    return (
        <View style={styles.container}>
            <Image
                source={require("./../assets/images/cardiogram.png")}
                style={styles.logo}
            />
            <Text style={styles.description}>
                Commancer à suivre votre Sante
            </Text>
            <Text style={styles.sousDescription}>
                Cette application vous permet de suivre votre santé ,et bien
                plus encore
            </Text>
            <Pressable
                style={styles.btn}
                onPress={() => navigation.replace("Boarding")}
            >
                <Text style={styles.txtBtn}>Commancer</Text>
            </Pressable>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: "#e8eef7",
        alignItems: "center",
        justifyContent: "center",
        width: "100%",
    },
    logo: {
        width: 300,
        height: 100,
        resizeMode: "contain",
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
        color: "#b8bcc2",
        marginBottom: 50,
    },
    btn: {
        width: "80%",
        backgroundColor: colors.primary,
        padding: 10,
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        borderRadius: 20,
    },
    txtBtn: {
        color: "white",
        fontSize: 18,
    },
});

export default LandigPage;
