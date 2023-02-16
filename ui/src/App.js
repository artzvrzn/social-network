import './App.css';
import HeaderContainer from "./components/header/HeaderContainer";
import Home from "./components/home/Home";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import ProfilesContainer from "./components/profiles/ProfilesContainer";
import {Provider} from "react-redux";
import {store} from "./service/store/StoreService";
import PersonalProfileContainer from "./components/personalProfile/PersonalProfileContainer";
import ProfileContainer from "./components/profile/ProfileContainer";

function App() {
    window.state = store.getState();
    return (
        <div>
            <Provider store={store}>
                <HeaderContainer/>
                <BrowserRouter>
                    <Routes>
                        <Route exact path="/" element={<Home/>}/>
                        <Route path="/users" element={<ProfilesContainer />}/>
                        <Route path="/profile">
                            <Route index element={<PersonalProfileContainer />}/>
                            <Route path=":id" element={<ProfileContainer />}/>
                        </Route>
                    </Routes>
                </BrowserRouter>
            </Provider>
        </div>
    );
}

export default App;
