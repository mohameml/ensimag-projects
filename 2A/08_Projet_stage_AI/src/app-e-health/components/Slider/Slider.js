import { StyleSheet, View, FlatList, Animated, Dimensions } from "react-native";
import SliderItem from "./SliderItem";
import Pagination from "./Pagination";
import { useRef, useState } from "react";
import { useNavigation } from "@react-navigation/native";
// import { Button } from "react-native-elements";
import Button from "../Button/Button";

const Slider = ({ data, full }) => {
    const [index, setIndex] = useState(0);
    const [endSlider, setEndSlider] = useState(false);
    const navigation = useNavigation();
    const { width } = Dimensions.get("window");

    const scrollX = useRef(new Animated.Value(0)).current;

    const handelScroll = (event) => {
        Animated.event(
            [
                {
                    nativeEvent: {
                        contentOffset: {
                            x: scrollX,
                        },
                    },
                },
            ],
            {
                useNativeDriver: false,
            }
        )(event);

        if (full) {
            setEndSlider(false);
            const offsetX = event.nativeEvent.contentOffset.x;
            const index = Math.round(offsetX / width);
            if (index === data.length - 1) {
                // setTimeout(() => {
                //     navigation.navigate("Login");
                // }, 500);
                setEndSlider(true);
            }
        }
    };

    const handelChangeItems = ({ viewableItems }) => {
        // console.log(viewableItems);
        setIndex(viewableItems[0].index);
    };

    return (
        <View style={styles.container}>
            <FlatList
                data={data}
                renderItem={({ item }) => (
                    <SliderItem item={item} full={full} />
                )}
                // contentContainerStyle={styles.container}
                horizontal
                pagingEnabled
                showsHorizontalScrollIndicator={false}
                snapToAlignment="center"
                onScroll={handelScroll}
                onViewableItemsChanged={handelChangeItems}
            />
            <Pagination data={data} scrollX={scrollX} index={index} />
            {full && endSlider && (
                <View style={styles.btnNext}>
                    {/* <Button title="next" /> */}
                    <Button
                        title="next"
                        handelOnPress={() => navigation.navigate("Login")}
                    />
                </View>
            )}
        </View>
    );
};

export default Slider;

const styles = StyleSheet.create({
    container: {
        width: "100%",
    },
    btnNext: {
        // backgroundColor: "red",
        position: "absolute",
        bottom: 50,
        width: "100%",
        // marginHorizontal: "auto",
        // justifyContent: "center",
        // alignItems: "center",
    },
});
