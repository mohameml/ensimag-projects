import { useState, useEffect, useRef } from "react";
import { Text, View, Button, Platform } from "react-native";
import * as Device from "expo-device";
import * as Notifications from "expo-notifications";
import Constants from "expo-constants";

export default function usePushNotif() {
    Notifications.setNotificationHandler({
        handleNotification: async () => ({
            shouldShowAlert: true,
            shouldPlaySound: true,
            shouldSetBadge: false,
        }),
    });

    const [expoPushToken, setExpoPushToken] = useState();
    // const [notification, setNotification] = useState();

    // const notificationListener = useRef();
    // const responseListener = useRef();

    function handleRegistrationError(errorMessage) {
        alert(errorMessage);
        throw new Error(errorMessage);
    }

    async function registerForPushNotification() {
        let token;

        if (Device.isDevice) {
            const { status: existingStatus } =
                await Notifications.getPermissionsAsync();

            let finalStatus = existingStatus;
            if (existingStatus !== "granted") {
                const { status } =
                    await Notifications.requestPermissionsAsync();
                finalStatus = status;
            }
            if (finalStatus !== "granted") {
                handleRegistrationError(
                    "Permission not granted to get push token for push notification!"
                );
                return;
            }

            const projectId =
                Constants?.expoConfig?.extra?.eas?.projectId ??
                Constants?.easConfig?.projectId;
            if (!projectId) {
                handleRegistrationError("Project ID not found");
                return;
            }

            try {
                token = (
                    await Notifications.getExpoPushTokenAsync({
                        projectId,
                    })
                ).data;
                console.log(token);
            } catch (e) {
                handleRegistrationError(`${e}`);
            }

            if (Platform.OS === "android") {
                Notifications.setNotificationChannelAsync("default", {
                    name: "default",
                    importance: Notifications.AndroidImportance.MAX,
                    vibrationPattern: [0, 250, 250, 250],
                    lightColor: "#FF231F7C",
                });
            }

            return token;
        } else {
            handleRegistrationError("Error : Please use a physical device");
        }
    }

    useEffect(() => {
        registerForPushNotification().then((token) => setExpoPushToken(token));
        // notificationListener.current =
        //     Notifications.addNotificationReceivedListener((notification) => {
        //         setNotification(notification);
        //     });

        // responseListener.current =
        //     Notifications.addNotificationResponseReceivedListener(
        //         (response) => {
        //             console.log(response);
        //         }
        //     );

        // return () => {
        //     Notifications.removeNotificationSubscription(
        //         notificationListener.current
        //     );

        //     Notifications.removeNotificationSubscription(
        //         responseListener.current
        //     );
        // };
    }, []);

    return { expoPushToken };
}
