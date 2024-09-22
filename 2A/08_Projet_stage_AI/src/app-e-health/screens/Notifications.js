import { FlatList, StyleSheet, Text, View } from "react-native";
import { useNavigation } from "@react-navigation/native";
import React from "react";
import { useLayoutEffect, useState, useEffect, useRef } from "react";
import usePushNotif from "../hook/usePushNotif";
import NotifView from "../components/NotifView/NotifView";
import * as Notification from "expo-notifications";

const Notifications = () => {
    const { expoPushToken } = usePushNotif();

    const [allNotifications, setAllNotifications] = useState([]);
    const notificationListener = useRef();
    console.log(expoPushToken);

    // const data = JSON.stringify(notification, undefined, 2);
    // console.log(data);
    // const messageBody = notification?.request?.content?.body;
    // const messageTitle = notification?.request?.content?.title;

    const navigation = useNavigation();
    useLayoutEffect(() => {
        navigation.setOptions({
            tabBarBadge: allNotifications.length,
        });
    }, [allNotifications]);

    useEffect(() => {
        notificationListener.current =
            Notification.addNotificationReceivedListener((notification) => {
                setAllNotifications((prevAllNotif) => [
                    ...prevAllNotif,
                    { notification: notification },
                ]);
            });

        return () => {
            Notification.removeNotificationSubscription(
                notificationListener.current
            );
        };
    }, []);

    const renderNotifView = ({ item }) => {
        const notif = item.notification;
        const messageBody = notif?.request?.content?.body;
        const messageTitle = notif?.request?.content?.title;

        return <NotifView title={messageTitle} message={messageBody} />;
    };

    return (
        <View>
            <FlatList
                data={allNotifications}
                renderItem={renderNotifView}
                showsHorizontalScrollIndicator={false}
            />
        </View>
    );
};

export default Notifications;

const styles = StyleSheet.create({});
