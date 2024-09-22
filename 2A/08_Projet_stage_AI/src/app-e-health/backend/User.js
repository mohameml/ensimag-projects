import { db, auth } from "../config/firebase";
import {
    collection,
    query,
    where,
    getDocs,
    addDoc,
    doc,
    updateDoc,
} from "firebase/firestore";

// =========== infos User : ===================
function getEmail() {
    const mailUser = auth.currentUser?.email;

    return mailUser;
}

async function getIdUser() {
    const mailUser = getEmail();
    let idUser = "";
    try {
        const q = query(
            collection(db, "users"),
            where("Email", "==", mailUser)
        );
        const queryResult = await getDocs(q);

        if (!queryResult.empty) {
            idUser = queryResult.docs[0].id;
        } else {
            console.log(`User ${mailUser} doesn't have any id`);
        }
    } catch (e) {
        console.error("Error fetching user data: ", error);
    }

    return idUser;
}

async function getInfoUser() {
    const mailUser = getEmail();
    let res = {};
    try {
        const q = query(
            collection(db, "users"),
            where("Email", "==", mailUser)
        );
        const queryResult = await getDocs(q);

        if (!queryResult.empty) {
            const data = queryResult.docs[0].data();

            if (data !== null) {
                const { Nom, Prenom } = data;
                res = { nom: Nom, prenom: Prenom };
            }
        } else {
            console.log(`User ${mailUser} doesn't have any data`);
        }
    } catch (e) {
        console.error("Error fetching user data: ", e);
        // throw new Error("Error fetching user data: file User.js");
    }

    return res;
}

async function AddNewUser({ nom, prenom, email }) {
    try {
        const docRef = await addDoc(collection(db, "users"), {
            Email: email,
            Nom: nom,
            Prenom: prenom,
        });
        console.log("Document written with ID: ", docRef.id);
    } catch (e) {
        console.error("Error adding document: ", e);
    }
}

async function UpdateInfoUser(email, nom, prenom) {
    let idUser = await getIdUser();
    try {
        const docUserRed = doc(db, "users", idUser);
        await updateDoc(docUserRed, {
            Email: email,
            Nom: nom,
            Prenom: prenom,
        });
        console.log("User profile updated successfully!");
    } catch (e) {
        console.error("Error upadting document: ", e);
    }
}
function DeleteUser() {}

export {
    getEmail,
    getIdUser,
    getInfoUser,
    AddNewUser,
    UpdateInfoUser,
    DeleteUser,
};
