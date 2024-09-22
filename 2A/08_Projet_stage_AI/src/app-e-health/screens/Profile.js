import { StyleSheet, Text, View } from "react-native";
import React, { useEffect, useLayoutEffect, useState } from "react";
import { useNavigation } from "@react-navigation/native";
import ViewItem from "../components/Profil/ViewItem";

const Profile = () => {
    const navigation = useNavigation();

    useLayoutEffect(() => {
        navigation.setOptions({
            headerStyle: {
                backgroundColor: "#2966e3",
            },
            headerTintColor: "white",
        });
    }, [navigation]);

    return (
        <View style={styles.container}>
            <View style={styles.label}>
                <Text style={styles.txtLabel}>Profile</Text>
            </View>
            <View>
                <ViewItem
                    title="Voir le Profile"
                    nameIcon={"eyeo"}
                    handelPress={() =>
                        navigation.navigate("Details", {
                            name: "voir",
                        })
                    }
                />
                <ViewItem
                    title="modifier le Profile"
                    nameIcon={"edit"}
                    handelPress={() =>
                        navigation.navigate("Details", {
                            name: "modifier",
                        })
                    }
                />
            </View>
            <View style={styles.label}>
                <Text style={styles.txtLabel}>Configuration</Text>
            </View>
            <View>
                <ViewItem title="configurer" nameIcon={"setting"} />
            </View>
        </View>
    );
};

export default Profile;

const styles = StyleSheet.create({
    container: {
        width: "100%",
        paddingTop: 30,
        flex: 1,
        backgroundColor: "white",
    },
    label: {
        padding: 5,
        width: "100%",
        backgroundColor: "#f6f6f6",
        paddingLeft: 20,
    },
    txtLabel: {
        fontWeight: "bold",
        marginLeft: 20,
    },
});
