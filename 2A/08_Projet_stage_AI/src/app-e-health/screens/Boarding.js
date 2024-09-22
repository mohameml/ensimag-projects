import { StyleSheet, Text, View } from "react-native";
import React from "react";
import Slider from "../components/Slider/Slider";
import dataBoarding from "../data/dataBoarding";
import { Button } from "react-native-elements";

const Boarding = () => {
    return (
        <View>
            <Slider data={dataBoarding} full={true} />
        </View>
    );
};

export default Boarding;

const styles = StyleSheet.create({});
