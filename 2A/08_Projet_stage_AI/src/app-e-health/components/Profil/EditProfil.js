import { StyleSheet, Text, View } from "react-native";
import React, { useState } from "react";
import InputInfo from "../InputInfo/InputInfo";
import Button from "../Button/Button";
import { UpdateInfoUser } from "../../backend/User";
import { useNavigation } from "@react-navigation/native";

const EditProfil = ({ infos }) => {
    const navigation = useNavigation();
    const [nom, setNom] = useState(infos.nom);
    const [prenom, setPreNom] = useState(infos.prenom);
    const [email, setEmail] = useState(infos.email);

    async function handelOnPressValider() {
        await UpdateInfoUser(email, nom, prenom);
        navigation.navigate("Profile");
    }

    function handelOnPressAnnuler() {
        navigation.navigate("Profile");
    }

    return (
        <View style={styles.container}>
            <InputInfo label="Nom" info={nom} color="black" setInfo={setNom} />
            <InputInfo
                label="Prenom"
                info={prenom}
                color="black"
                setInfo={setPreNom}
            />
            <InputInfo
                label="Email"
                info={email}
                color="black"
                setInfo={setEmail}
            />
            <View>
                <Button title="Valider" handelOnPress={handelOnPressValider} />
                <Button title="Annuler" handelOnPress={handelOnPressAnnuler} />
            </View>
        </View>
    );
};

export default EditProfil;

const styles = StyleSheet.create({
    container: {
        width: "100%",
    },
});
