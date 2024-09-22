import LandigPage from "./screens/LandingPage";
import { NavigationContainer } from "@react-navigation/native";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import Login from "./screens/Login";
import { SafeAreaProvider } from "react-native-safe-area-context";
import SingUp from "./screens/SingUp";
import HomeTab from "./screens/HomeTab";
import Profile from "./screens/Profile";
import Bluetooth from "./screens/Bluetooth";
import Visualisation from "./screens/Visualisation";
import Historique from "./screens/Historique";
import Details from "./screens/Details";
import Boarding from "./screens/Boarding";
import { PaperProvider } from "react-native-paper";
import FilterView from "./components/Filter/FilterView";

const Stack = createNativeStackNavigator();

export default function App() {
    return (
        <PaperProvider>
            <SafeAreaProvider>
                <NavigationContainer>
                    <Stack.Navigator screenOptions={{ headerShown: false }}>
                        <Stack.Screen name="Landing" component={LandigPage} />
                        <Stack.Screen name="Login" component={Login} />
                        <Stack.Screen name="Boarding" component={Boarding} />
                        <Stack.Screen name="SingUp" component={SingUp} />
                        <Stack.Screen name="HomeTab" component={HomeTab} />
                        <Stack.Screen
                            name="Profil"
                            component={Profile}
                            options={{ headerShown: true }}
                        />
                        <Stack.Screen
                            name="Bluetooth"
                            component={Bluetooth}
                            options={{ headerShown: true }}
                        />
                        <Stack.Screen
                            name="Details"
                            component={Details}
                            options={{ headerShown: true }}
                        />
                        <Stack.Screen
                            name="Visualisation"
                            component={Visualisation}
                            options={{ headerShown: true }}
                        />
                        <Stack.Screen
                            name="Historique"
                            component={Historique}
                            options={{ headerShown: true }}
                        />
                        <Stack.Screen
                            name="filter"
                            component={FilterView}
                            options={{ headerShown: false }}
                        />
                    </Stack.Navigator>
                </NavigationContainer>
            </SafeAreaProvider>
        </PaperProvider>
    );
}
