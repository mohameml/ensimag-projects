// ========================== Core Components des react native ================================
import { StyleSheet, Text, View, Pressable } from "react-native";

// =============================== hook des react JS ========================
import React, { useState } from "react";

// ========================== composante personnalisée =================================
import InputPassword from "../components/InputPassword/InputPassword";
import Button from "../components/Button/Button";
import Input from "../components/Input/Input";

// ============================= fonctions du firebse pour le backend ======================
import { auth, db } from "../config/firebase";
import { createUserWithEmailAndPassword } from "firebase/auth";
import { collection, addDoc } from "firebase/firestore";

// ========================== Screen  Login  ==============================
const SingUp = ({ navigation }) => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [nom, setNom] = useState("");
    const [prenom, setPreNom] = useState("");

    async function handleSignUp() {
        // creation d'un compte user :
        createUserWithEmailAndPassword(auth, email, password)
            .then((userCredential) => {
                const user = userCredential.user;
                console.log("inscription avce : ", user.email);
            })
            .catch((error) => {
                alert(error.message);
            });
        // ajouter les informations à la base de donnes :
        try {
            const docRef = await addDoc(collection(db, "users"), {
                Email: email,
                Nom: nom,
                Prenom: prenom,
            });
            console.log("Document written with ID: ", docRef.id);
            // navigation to login :
            navigation.replace("Login");
        } catch (e) {
            console.error("Error adding document: ", e);
        }
    }

    return (
        <View style={styles.container}>
            <View style={styles.infos}>
                <Text style={styles.title}>Let's get started!</Text>
            </View>
            <View style={styles.inputContainer}>
                <Input title="Email" value={email} setValue={setEmail} />
                <Input title="Prenome" value={prenom} setValue={setPreNom} />
                <Input title="Nom" value={nom} setValue={setNom} />
                <InputPassword password={password} setPassword={setPassword} />

                <Button title="valider" handelOnPress={handleSignUp} />
                <View style={styles.signupSwitch}>
                    <Text style={{ fontWeight: "bold" }}>
                        if you alredy have an account ?{" "}
                    </Text>
                    <Pressable onPress={() => navigation.navigate("Login")}>
                        <Text style={{ fontWeight: "bold", color: "#0782F9" }}>
                            Login
                        </Text>
                    </Pressable>
                </View>
            </View>
        </View>
    );
};

export default SingUp;

const styles = StyleSheet.create({
    container: {
        flex: 1,
        width: "100%",
        backgroundColor: "white",
        alignItems: "center",
    },
    inputContainer: {
        width: "80%",
    },

    image: {
        width: 200,
        height: 200,
        resizeMode: "contain",
    },
    title: {
        fontSize: 20,
        fontWeight: "bold",
    },
    infos: {
        marginTop: 80,
        width: "100%",
        alignItems: "center",
        marginBottom: 30,
    },
    signupSwitch: {
        marginTop: 30,
        justifyContent: "center",
        alignItems: "center",
    },
});
