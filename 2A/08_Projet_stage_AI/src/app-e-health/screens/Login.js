// ========================== Core Components des react native ================================
import { StyleSheet, Text, View, Image, Pressable } from "react-native";

// =============================== hook des react JS ========================
import React, { useEffect, useState } from "react";

// ========================== composante personnalisée =================================
import InputPassword from "../components/InputPassword/InputPassword";
import Button from "../components/Button/Button";
import Input from "../components/Input/Input";

// ============================= fonctions du firebse pour le backend ======================
import { auth } from "../config/firebase";
import { signInWithEmailAndPassword, onAuthStateChanged } from "firebase/auth";

// ========================== Screen  Login  ==============================
const Login = ({ navigation }) => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    useEffect(() => {
        const unsubscribe = onAuthStateChanged(auth, (user) => {
            if (user) {
                navigation.replace("HomeTab");
            }
        });

        return unsubscribe;
    }, []);

    const handelLogin = () => {
        signInWithEmailAndPassword(auth, email, password)
            .then((userCredential) => {
                const user = userCredential.user;
                console.log("Connexion avce : ", user.email);
                navigation.replace("HomeTab");
            })
            .catch((error) => {
                alert(error.message);
            });
    };

    return (
        <View style={styles.container}>
            <View style={styles.infos}>
                <Text style={styles.title}>Welcome Back</Text>
                <Image
                    source={require("../assets/images/login.jpg")}
                    style={styles.image}
                />
            </View>
            <View style={styles.inputContainer}>
                <Input title="Email" value={email} setValue={setEmail} />

                <InputPassword password={password} setPassword={setPassword} />
                <Button title="valider" handelOnPress={handelLogin} />
                <View style={styles.signupSwitch}>
                    <Text style={{ fontWeight: "bold" }}>
                        Don't have a an account ?{" "}
                    </Text>
                    <Pressable onPress={() => navigation.navigate("SingUp")}>
                        <Text style={{ fontWeight: "bold", color: "#0782F9" }}>
                            Sign Up
                        </Text>
                    </Pressable>
                </View>
            </View>
        </View>
    );
};

export default Login;

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
    },
    signupSwitch: {
        marginTop: 30,
        justifyContent: "center",
        alignItems: "center",
    },
});
