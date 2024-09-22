import {
    SafeAreaView,
    StyleSheet,
    Text,
    TouchableOpacity,
    View,
} from "react-native";
import useBLE from "../hook/useBLE";
import DeviceModal from "../components/DeviceModal/DeviceModal";

import { useState } from "react";
// import heartRateAnimeted from "../components/Animation/heartRateAnimeted";

export default function Bluetooth() {
    const {
        requestBluetoothPermission,
        scanForPeripherals,
        allDevices,
        connectToDevice,
        connectedDevice,
        heartRate,
        disconnectFromDevice,
    } = useBLE();

    const [isModalVisible, setIsModalVisible] = useState(false);
    const scanForDevices = async () => {
        const isPermissionEnabled = await requestBluetoothPermission();
        if (isPermissionEnabled) {
            scanForPeripherals();
        }
    };

    const hideModal = () => {
        setIsModalVisible(false);
    };

    const openModal = async () => {
        scanForDevices();
        setIsModalVisible(true);
    };

    return (
        <SafeAreaView style={styles.container}>
            <View style={styles.heartRateTitleWrapper}>
                {connectedDevice ? (
                    <>
                        {/* <heartRateAnimeted /> */}
                        <Text style={styles.heartRateTitleText}>
                            Your Heart Rate Is:
                        </Text>
                        <Text style={styles.heartRateText}>
                            {heartRate} bpm
                        </Text>
                    </>
                ) : (
                    <Text style={styles.heartRateTitleText}>
                        Veuillez vous connecter à un appareil médical
                    </Text>
                )}
            </View>
            <TouchableOpacity
                onPress={connectedDevice ? disconnectFromDevice : openModal}
                style={styles.ctaButton}
            >
                <Text style={styles.ctaButtonText}>
                    {connectedDevice ? "Disconnecter" : "Connecter"}
                </Text>
            </TouchableOpacity>
            <DeviceModal
                closeModal={hideModal}
                visible={isModalVisible}
                connectToPeripheral={connectToDevice}
                devices={allDevices}
            />
        </SafeAreaView>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: "#fff",
        alignItems: "center",
        justifyContent: "center",
    },
    heartRateTitleWrapper: {
        flex: 1,
        justifyContent: "center",
        alignItems: "center",
    },
    heartRateTitleText: {
        fontSize: 27,
        fontWeight: "bold",
        textAlign: "center",
        marginHorizontal: 20,
        color: "black",
    },
    heartRateText: {
        fontSize: 25,
        marginTop: 15,
    },
    ctaButton: {
        backgroundColor: "#FF6060",
        justifyContent: "center",
        alignItems: "center",
        height: 50,
        marginHorizontal: 20,
        marginBottom: 20,
        borderRadius: 8,
        width: "80%",
    },
    ctaButtonText: {
        fontSize: 18,
        fontWeight: "bold",
        color: "white",
    },
});
