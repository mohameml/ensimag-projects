// // Import the functions you need from the SDKs you need
// import { initializeApp } from "firebase/app";
// import { getFirestore } from "firebase/firestore";
// // TODO: Add SDKs for Firebase products that you want to use
// // https://firebase.google.com/docs/web/setup#available-libraries

// import { initializeAuth, getReactNativePersistence } from "firebase/auth";
// import ReactNativeAsyncStorage from "@react-native-async-storage/async-storage";

// import {
//     API_KEY,
//     AUTH_DOMAIN,
//     PROJECT_ID,
//     STORAGE_BUCKET,
//     MESSAGING_SENDER_ID,
//     APP_ID,
// } from "@env";

// // Your web app's Firebase configuration

// const firebaseConfig = {
//     apiKey: API_KEY,
//     authDomain: AUTH_DOMAIN,
//     projectId: PROJECT_ID,
//     storageBucket: STORAGE_BUCKET,
//     messagingSenderId: MESSAGING_SENDER_ID,
//     appId: APP_ID,
// };

// // Initialize Firebase
// let app;

// app = initializeApp(firebaseConfig);
// // if (firebase.apps.length === 0) {
// // } else {
// //     app = firebase.app();
// // }

// // Initialiser Firebase Auth avec persistance
// const auth = initializeAuth(app, {
//     persistence: getReactNativePersistence(ReactNativeAsyncStorage),
// });

// // Initialize Cloud Firestore and get a reference to the service
// const db = getFirestore(app);

// export { auth, db };
// Import the functions you need from the SDKs you need
import { initializeApp, getApps, getApp } from "firebase/app";
import { getFirestore } from "firebase/firestore";
import {
    initializeAuth,
    getAuth,
    getReactNativePersistence,
} from "firebase/auth";
import ReactNativeAsyncStorage from "@react-native-async-storage/async-storage";

import {
    API_KEY,
    AUTH_DOMAIN,
    PROJECT_ID,
    STORAGE_BUCKET,
    MESSAGING_SENDER_ID,
    APP_ID,
} from "@env";

// Your web app's Firebase configuration
const firebaseConfig = {
    apiKey: API_KEY,
    authDomain: AUTH_DOMAIN,
    projectId: PROJECT_ID,
    storageBucket: STORAGE_BUCKET,
    messagingSenderId: MESSAGING_SENDER_ID,
    appId: APP_ID,
};

// Initialize Firebase App if it hasn't been initialized already
let app;
if (getApps().length === 0) {
    app = initializeApp(firebaseConfig);
} else {
    app = getApp();
}

// Initialize Firebase Auth with persistence if it hasn't been initialized already
let auth;
try {
    auth = getAuth(app);
} catch (e) {
    auth = initializeAuth(app, {
        persistence: getReactNativePersistence(ReactNativeAsyncStorage),
    });
}

// Initialize Cloud Firestore and get a reference to the service
const db = getFirestore(app);

export { auth, db };
