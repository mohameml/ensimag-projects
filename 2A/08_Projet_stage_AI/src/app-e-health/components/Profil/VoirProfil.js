import { StyleSheet, Text, View } from "react-native";
import React from "react";
import InputInfo from "../InputInfo/InputInfo";

const VoirProfil = ({ infos }) => {
    const { nom, prenom, email } = infos;
    return (
        <View style={styles.container}>
            <InputInfo label="Nom" info={nom} color="gray" editable={false} />
            <InputInfo
                label="Prenom"
                info={prenom}
                color="gray"
                editable={false}
            />
            <InputInfo
                label="Email"
                info={email}
                color="gray"
                editable={false}
            />
        </View>
    );
};

export default VoirProfil;

const styles = StyleSheet.create({
    container: {
        width: "100%",
    },
});
