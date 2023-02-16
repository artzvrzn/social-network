import {applyMiddleware, combineReducers, legacy_createStore} from "redux";
import {profilesReducer} from "./reducer/ProfilesReducer";
import {personalProfileReducer} from "./reducer/PersonalProfileReducer";
import {profileReducer} from "./reducer/ProfileReducer";
import thunk from "redux-thunk";

const reducers = combineReducers({
    personalProfilePage: personalProfileReducer,
    profilePage: profileReducer,
    profilesPage: profilesReducer,

});

export const store = legacy_createStore(reducers, applyMiddleware(thunk));

