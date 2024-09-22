import { StyleSheet, Text, View } from "react-native";
import React from "react";
import { useNavigation, useRoute } from "@react-navigation/native";
import { useLayoutEffect, useState, useEffect } from "react";
import VoirProfil from "../components/Profil/VoirProfil";
import EditProfil from "../components/Profil/EditProfil";
import { getInfoUser, getEmail } from "../backend/User";
import { ActivityIndicator } from "react-native";

const Details = () => {
    const navigation = useNavigation();
    const route = useRoute();
    const params = route.params;

    const [infos, setInfos] = useState({});
    const [loading, setLoading] = useState(true);

    useLayoutEffect(() => {
        navigation.setOptions({
            headerStyle: {
                backgroundColor: "#2966e3",
            },

            headerTintColor: "white",
        });
    }, [navigation]);

    useEffect(() => {
        const fetchDataUser = async () => {
            try {
                const { nom, prenom } = await getInfoUser();
                const email = getEmail();
                setInfos({ email: email, nom: nom, prenom: prenom });
            } catch (e) {
                console.error("Error fetching user data: ", e);
            } finally {
                setLoading(false);
            }
        };

        fetchDataUser();
    }, [navigation]);

    if (loading) {
        return (
            <View style={styles.wait}>
                <ActivityIndicator size={"large"} />
            </View>
        );
    }

    if (params.name === "voir") {
        return (
            <View style={styles.container}>
                <VoirProfil infos={infos} />
            </View>
        );
    } else if (params.name === "modifier") {
        return (
            <View style={styles.container}>
                <EditProfil infos={infos} />
            </View>
        );
    }
};

export default Details;

const styles = StyleSheet.create({
    container: {
        width: "100%",
        backgroundColor: "white",
        flex: 1,
        alignItems: "center",
        padding: 20,
        paddingTop: 40,
    },
    wait: {
        flex: 1,
        justifyContent: "center",
        alignItems: "center",
    },
});
