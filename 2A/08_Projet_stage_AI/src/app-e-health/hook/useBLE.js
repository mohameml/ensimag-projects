import { useMemo, useState } from "react";
import { BleManager, Device } from "react-native-ble-plx";

import { Platform, PermissionsAndroid } from "react-native";
import base64 from "react-native-base64";

import { addNewMesure } from "../backend/DataSante";

function useBLE() {
    const bleManager = useMemo(() => new BleManager(), []);

    const [allDevices, setAllDevices] = useState([]);
    const [connectedDevice, setConnectedDevice] = useState(null);
    const [heartRate, setHeartRate] = useState(-1);

    const HEART_RATE_UUID = "0000180d-0000-1000-8000-00805f9b34fb";
    const HEART_RATE_CHARACTERISTIC = "00002a37-0000-1000-8000-00805f9b34fb";

    const service_UUID = "180D";
    const charac_UUID = "2A37";

    const requestBluetoothPermission = async () => {
        if (Platform.OS === "ios") {
            return true;
        }
        if (
            Platform.OS === "android" &&
            PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION
        ) {
            const apiLevel = parseInt(Platform.Version.toString(), 10);

            if (apiLevel < 31) {
                const granted = await PermissionsAndroid.request(
                    PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION
                );
                return granted === PermissionsAndroid.RESULTS.GRANTED;
            }
            if (
                PermissionsAndroid.PERMISSIONS.BLUETOOTH_SCAN &&
                PermissionsAndroid.PERMISSIONS.BLUETOOTH_CONNECT
            ) {
                const result = await PermissionsAndroid.requestMultiple([
                    PermissionsAndroid.PERMISSIONS.BLUETOOTH_SCAN,
                    PermissionsAndroid.PERMISSIONS.BLUETOOTH_CONNECT,
                    PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION,
                ]);

                return (
                    result["android.permission.BLUETOOTH_CONNECT"] ===
                        PermissionsAndroid.RESULTS.GRANTED &&
                    result["android.permission.BLUETOOTH_SCAN"] ===
                        PermissionsAndroid.RESULTS.GRANTED &&
                    result["android.permission.ACCESS_FINE_LOCATION"] ===
                        PermissionsAndroid.RESULTS.GRANTED
                );
            }
        }

        alert("Permission have not been granted");

        return false;
    };

    const isDuplicteDevice = (devices, nextDevice) =>
        devices.findIndex((device) => nextDevice.id === device.id) > -1;

    const scanForPeripherals = () => {
        console.log("=========== start scanning ================");
        bleManager.startDeviceScan(null, null, (error, device) => {
            if (error) {
                console.log(error);
            }

            if (device) {
                setAllDevices((prevState) => {
                    if (!isDuplicteDevice(prevState, device)) {
                        return [...prevState, device];
                    }
                    return prevState;
                });
            }
        });
    };

    const connectToDevice = async (device) => {
        try {
            console.log("======= Conncection to devaice ============");
            // console.log(device);
            const deviceConnection = await bleManager.connectToDevice(
                device.id
            );
            setConnectedDevice(deviceConnection);
            await deviceConnection.discoverAllServicesAndCharacteristics();
            bleManager.stopDeviceScan();
            await startStreamingData(deviceConnection);
        } catch (e) {
            console.log("FAILED TO CONNECT , ERROR : ", e);
        }
    };

    const onHeartRateUpdate = (error, characteristic) => {
        if (error) {
            console.log(error);
            return -1;
        } else if (!characteristic?.value) {
            console.log("No Data was recieved");
            return -1;
        }

        const rawData = base64.decode(characteristic.value);
        console.log("==== read data : ", characteristic.value);
        let innerHeartRate = -1;

        const firstBitValue = Number(rawData) & 0x01;

        if (firstBitValue === 0) {
            innerHeartRate = rawData[1].charCodeAt(0);
        } else {
            innerHeartRate =
                Number(rawData[1].charCodeAt(0) << 8) +
                Number(rawData[2].charCodeAt(2));
        }

        setHeartRate(innerHeartRate);
    };

    const startStreamingData = async (device) => {
        console.log("============ start read info ===============");
        if (device) {
            const characteristic = await device.readCharacteristicForService(
                service_UUID,
                charac_UUID
            );
            // console.log("value =", characteristic);
            const value = base64.decode(characteristic.value);
            setHeartRate(value);
            addNewMesure(value);
            console.log("value =", characteristic.value);

            if (device.name?.includes("CorSense")) {
                device.monitorCharacteristicForService(
                    HEART_RATE_UUID,
                    HEART_RATE_CHARACTERISTIC,
                    onHeartRateUpdate
                );
            }
        } else {
            console.log("No Device Connected");
        }
    };

    const disconnectFromDevice = () => {
        if (connectedDevice) {
            bleManager.cancelDeviceConnection(connectedDevice.id);
            setConnectedDevice(null);
            setHeartRate(0);
        }
    };

    return {
        requestBluetoothPermission,
        scanForPeripherals,
        allDevices,
        connectToDevice,
        connectedDevice,
        heartRate,
        disconnectFromDevice,
    };
}

export default useBLE;
