import ProfileAPI from "../../ProfileAPI";

const SET_PROFILE = "personalProfile/setProfile";
const TOGGLE_IS_FETCHING = "personalProfile/toggleIsFetching";
const TOGGLE_IS_FETCHED = "personalProfile/toggleIsFetched";

const initialState = {
    profile: null,
    isFetching: false,
    isFetched: false
};

export const personalProfileReducer = (state = initialState, action) => {
    switch (action.type) {
        case SET_PROFILE:
            return _setProfile(state, action.profile);
        case TOGGLE_IS_FETCHING:
            return _toggleIsFetching(state, action.isFetching);
        case TOGGLE_IS_FETCHED:
            return _toggleIsFetched(state, action.isFetched);
        default:
            return state;
    }
}

export const setProfileAC = (profile) => ({type: SET_PROFILE, profile})
export const toggleIsFetchingAC = (isFetching) => ({type: TOGGLE_IS_FETCHING, isFetching});
export const toggleIsFetchedAC = (isFetched) => ({type: TOGGLE_IS_FETCHED, isFetched});

export const getPersonalProfileThunk = () => (dispatch) => {
    dispatch(toggleIsFetchingAC(true));
    ProfileAPI.getMyProfile()
        .then(data => {
            dispatch(setProfileAC(data));
            dispatch(toggleIsFetchingAC(false));
            dispatch(toggleIsFetchedAC(true));
        });
};

const _setProfile = (state, profile) => ({...state, profile});
const _toggleIsFetching = (state, isFetching) => ({...state, isFetching});
const _toggleIsFetched = (state, isFetched) => ({...state, isFetched});

