import {
    FlatList,
    Modal,
    SafeAreaView,
    Text,
    StyleSheet,
    TouchableOpacity,
    View,
} from "react-native";
import DeviceModalListItem from "./DeviceModalListItem";
import colors from "../../constant/colors";

const DeviceModal = (props) => {
    const { devices, visible, connectToPeripheral, closeModal } = props;

    const renderDeviceModalListItem = (item) => {
        return (
            <DeviceModalListItem
                item={item}
                connectToPeripheral={connectToPeripheral}
                closeModal={closeModal}
            />
        );
    };

    return (
        <Modal
            style={modalStyle.modalContainer}
            animationType="slide"
            transparent={false}
            visible={visible}
        >
            <SafeAreaView style={modalStyle.modalTitle}>
                <Text style={modalStyle.modalTitleText}>
                    Appuyez sur un appareil pour vous connecter
                </Text>
                <Text style={modalStyle.warningTitle}>
                    Assurez-vous que l'appareil auquel vous souhaitez vous
                    connecter est en mode appairage
                </Text>
                <FlatList
                    contentContainerStyle={modalStyle.modalFlatlistContiner}
                    data={devices}
                    renderItem={renderDeviceModalListItem}
                />
                <View style={modalStyle.annullerContainer}>
                    <TouchableOpacity
                        onPress={closeModal}
                        style={modalStyle.ctaButton}
                    >
                        <Text style={modalStyle.ctaButtonText}>Annuller</Text>
                    </TouchableOpacity>
                </View>
            </SafeAreaView>
        </Modal>
    );
};

const modalStyle = StyleSheet.create({
    modalContainer: {
        flex: 1,
        backgroundColor: "#f2f2f2",
    },
    modalFlatlistContiner: {
        flex: 1,
        justifyContent: "center",
        // alignItems: "center",
    },
    modalCellOutline: {
        borderWidth: 1,
        borderColor: "black",
        alignItems: "center",
        marginHorizontal: 20,
        paddingVertical: 15,
        borderRadius: 8,
    },
    modalTitle: {
        flex: 1,
        backgroundColor: "#f2f2f2",
    },
    modalTitleText: {
        marginTop: 40,
        fontSize: 22,
        fontWeight: "bold",
        marginHorizontal: 20,
        textAlign: "center",
    },
    warningTitle: {
        marginHorizontal: 20,
        textAlign: "center",
        color: colors.secondary,
        fontSize: 15,
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
    annullerContainer: {
        // backgroundColor: "red",
        width: "100%",
        justifyContent: "center",
        alignItems: "center",
    },
});

export default DeviceModal;
