import ProfileAPI from "../../ProfileAPI";
import Profiles from "../../../components/profiles/Profiles";

const SET_PROFILES = "profiles/setProfiles";
const TOGGLE_IS_FETCHING = "profiles/toggleIsFetching";
const SET_PAGE_NUMBER = "profiles/setPageNumber";
const SET_TOTAL_PAGES = "profiles/setTotalPages";
const TOGGLE_IS_FETCHED = "profiles/toggleIsFetched";
const SUBSCRIBE = "profiles/subscribe";
const UNSUBSCRIBE = "profiles/unsubscribe";
const TOGGLE_SUBSCRIPTION_PROGRESS = "profiles/toggleSubscriptionProgress";

const initialState = {
    profiles: null,
    pageNumber: 1,
    pageSize: 3,
    totalPages: 0,
    isFetching: false,
    isFetched: false,
    subscriptionInProgress: []
};

export const profilesReducer = (state = initialState, action) => {
    switch (action.type) {
        case SET_PROFILES:
            return _setProfiles(state, action.profiles);
        case SET_PAGE_NUMBER:
            return _setPageNumber(state, action.pageNumber);
        case SET_TOTAL_PAGES:
            return _setTotalPages(state, action.totalPages);
        case TOGGLE_IS_FETCHING:
            return _toggleIsFetching(state, action.isFetching);
        case TOGGLE_IS_FETCHED:
            return _toggleIsFetched(state, action.isFetched);
        case SUBSCRIBE:
            return _subscribeToProfile(state, action.profileId);
        case UNSUBSCRIBE:
            return _unsubscribeFromProfile(state, action.profileId);
        case TOGGLE_SUBSCRIPTION_PROGRESS:
            return _toggleSubscriptionProgress(state, action.profileId, action.inProgress);
        default:
            return state;
    }
}


export const setProfilesAC = (profiles) => ({ type: SET_PROFILES, profiles });

export const setPageNumberAC = (number) => ({type: SET_PAGE_NUMBER, pageNumber: number});

export const setTotalPagesAC = (totalPages) => ({type: SET_TOTAL_PAGES, totalPages});

export const toggleIsFetchingAC = (isFetching) => ({ type: TOGGLE_IS_FETCHING, isFetching });

export const toggleIsFetchedAC = (isFetched) => ({ type: TOGGLE_IS_FETCHED, isFetched });

export const subscribeToProfileAC = (profileId) => ({type: SUBSCRIBE, profileId});

export const unsubscribeFromProfileAC = (profileId) => ({type: UNSUBSCRIBE, profileId});

export const toggleSubscriptionProgressAC = (profileId, inProgress) => (
    {type : TOGGLE_SUBSCRIPTION_PROGRESS, profileId, inProgress}
);


export const getProfilesThunk = (pageNumber, pageSize) => (dispatch) => {
    dispatch(toggleIsFetchingAC(true));
    ProfileAPI.getProfiles(pageNumber, pageSize)
        .then(data => {
            console.log(data);
            dispatch(setProfilesAC(data.content));
            dispatch(setPageNumberAC(data.number));
            dispatch(setTotalPagesAC(data.totalPages));
            dispatch(toggleIsFetchingAC(false));
            dispatch(toggleIsFetchedAC(true));
        })
        .catch(error => console.log(error));
};

export const followProfileThunk = (profileId) => (dispatch) => {
    dispatch(toggleSubscriptionProgressAC(profileId, true));
    ProfileAPI.followProfile(profileId)
        .then(response => {
            console.log(response);
            if (response.status === 200) {
                dispatch(subscribeToProfileAC(profileId));
            }
            dispatch(toggleSubscriptionProgressAC(profileId, false));
        })
};

export const unfollowProfileThunk = (profileId) => (dispatch) => {
    dispatch(toggleSubscriptionProgressAC(profileId, true));
    ProfileAPI.unfollowProfile(profileId)
        .then(response => {
            console.log(response);
            if (response.status === 200) {
                dispatch(unsubscribeFromProfileAC(profileId));
            }
            dispatch(toggleSubscriptionProgressAC(profileId, false));
        })
};


const _setProfiles = (state, profiles) => ({ ...state, profiles });

const _setPageNumber = (state, number) => ({...state, pageNumber: number});

const _setTotalPages = (state, totalPages) => ({...state, totalPages});

const _toggleIsFetching = (state, isFetching) => ({ ...state, isFetching });

const _toggleIsFetched = (state, isFetched) => ({...state, isFetched});

const _subscribeToProfile = (state, profileId) => _changeSubscriptionStatus(state, profileId, true);

const _unsubscribeFromProfile = (state, profileId) => _changeSubscriptionStatus(state, profileId, false);

const _changeSubscriptionStatus = (state, profileId, status) => ({
    ...state,
    profiles: state.profiles.map(profile =>
        profile.id === profileId ? {...profile, isSubscribed: status} : profile)
});

const _toggleSubscriptionProgress = (state, profileId, inProgress) => {
    if (inProgress) {
        if (state.subscriptionInProgress.includes(profileId)) {
            console.warn("attempt to add same profile id to subscription progress");
            return state;
        }
        return {...state, subscriptionInProgress: [...state.subscriptionInProgress, profileId]};
    } else {
        return {
            ...state,
            subscriptionInProgress: state.subscriptionInProgress.filter(element => element !== profileId)
        };
    }
};
