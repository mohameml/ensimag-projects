import { StyleSheet, Text, View } from "react-native";
import React from "react";
import { Calendar } from "react-native-calendars";
import { Button } from "react-native-paper";
import { useState } from "react";
import { useNavigation, useRoute } from "@react-navigation/native";
import InputInfo from "../InputInfo/InputInfo";
let count = 0;

const FilterView = () => {
    const navigtion = useNavigation();
    const [selected, setSelected] = useState("");
    const [DateFrom, setDateFrom] = useState(null);
    const [DateTo, setDateTo] = useState(null);

    const route = useRoute();
    const handelFilter = route.params.handelFilter;

    const handelDatePress = (day) => {
        setSelected(day.dateString);
        console.log("selected day", day.dateString);
        if (count == 0) {
            setDateFrom(day.dateString);
            count++;
        } else if (count == 1) {
            setDateTo(day.dateString);
            count--;
        }
    };

    const handelValideFilter = () => {
        navigtion.navigate("Historique");
        handelFilter(DateFrom, DateTo);
    };

    return (
        <View style={styles.container}>
            <View style={styles.inputs}>
                <InputInfo
                    label="Date Debut"
                    info={DateFrom}
                    setInfo={setDateFrom}
                    color={"#e8e1ed"}
                />
                <InputInfo
                    label="Date Fin"
                    info={DateTo}
                    setInfo={setDateTo}
                    color={"#e8e1ed"}
                />
            </View>
            <View style={styles.calendar}>
                <Calendar
                    onDayPress={handelDatePress}
                    markedDates={{
                        [selected]: {
                            selected: true,
                            disableTouchEvent: true,
                            selectedDotColor: "orange",
                        },
                    }}

                    // markingType={"period"}
                    // markedDates={{
                    //     "2024-08-20": {
                    //         startingDay: true,
                    //         color: "green",
                    //         textColor: "white",
                    //     },
                    //     "2024-08-21": {
                    //         color: "green",
                    //         textColor: "white",
                    //     },
                    //     "2024-08-22": {
                    //         color: "green",
                    //         textColor: "white",
                    //     },

                    //     "2024-08-23": {
                    //         selected: true,
                    //         endingDay: true,
                    //         color: "green",
                    //         textColor: "white",
                    //     },
                    // }}
                />
            </View>
            <View style={styles.viewbtn}>
                <Button
                    mode="outlined"
                    onPress={handelValideFilter}
                    style={styles.btn}
                >
                    valider
                </Button>
            </View>
        </View>
    );
};

export default FilterView;

const styles = StyleSheet.create({
    container: {
        width: "100%",
        justifyContent: "center",
        backgroundColor: "white",
        flex: 1,
        // backgroundColor: "red",
    },
    calendar: {
        marginTop: 50,
        // justifyContent: "center",
        // alignItems: "center",
    },
    btn: {
        width: "80%",
    },
    inputs: {
        alignItems: "center",
    },
    viewbtn: {
        width: "100%",
        // backgroundColor: "red",
        justifyContent: "center",
        alignItems: "center",
        position: "absolute",
        bottom: 50,
    },
});
