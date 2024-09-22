import React, { useCallback } from "react";
import { Text, StyleSheet, TouchableOpacity } from "react-native";

const DeviceModalListItem = (props) => {
    const { item, connectToPeripheral, closeModal } = props;

    const connectAndCloseModal = useCallback(() => {
        connectToPeripheral(item.item);
        closeModal();
    }, [closeModal, connectToPeripheral, item.item]);
    const name = item.item.name === null ? "Unnamed" : item.item.name;

    return (
        <TouchableOpacity
            onPress={connectAndCloseModal}
            style={modalStyle.ctaButton}
        >
            <Text style={modalStyle.ctaButtonText}>{name}</Text>
        </TouchableOpacity>
    );
};

const modalStyle = StyleSheet.create({
    ctaButton: {
        backgroundColor: "#FF6060",
        justifyContent: "center",
        alignItems: "center",
        height: 50,
        marginHorizontal: 20,
        marginBottom: 5,
        borderRadius: 8,
    },
    ctaButtonText: {
        fontSize: 18,
        fontWeight: "bold",
        color: "white",
    },
});

export default DeviceModalListItem;
