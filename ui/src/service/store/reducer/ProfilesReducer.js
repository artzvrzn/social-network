import ProfileAPI from "../../ProfileAPI";

const SET_PROFILES = "profiles/setProfiles";
const TOGGLE_IS_FETCHING = "profiles/toggleIsFetching";
const SET_PAGE_NUMBER = "profiles/setPageNumber";
const SET_TOTAL_PAGES = "profiles/setTotalPages";
const TOGGLE_IS_FETCHED = "profiles/toggleIsFetched";

const initialState = {
    profiles: null,
    pageNumber: 1,
    pageSize: 3,
    totalPages: 0,
    isFetching: false,
    isFetched: false
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
        default:
            return state;
    }
}

export const setProfilesAC = (profiles) => ({ type: SET_PROFILES, profiles });
export const setPageNumberAC = (number) => ({type: SET_PAGE_NUMBER, pageNumber: number});
export const setTotalPagesAC = (totalPages) => ({type: SET_TOTAL_PAGES, totalPages: totalPages});
export const toggleIsFetchingAC = (isFetching) => ({ type: TOGGLE_IS_FETCHING, isFetching });
export const toggleIsFetchedAC = (isFetched) => ({ type: TOGGLE_IS_FETCHED, isFetched });

export const getProfilesThunk = (pageNumber, pageSize) => (dispatch) => {
    dispatch(toggleIsFetchingAC(true));
    ProfileAPI.getProfiles(pageNumber, pageSize)
        .then(data => {
            dispatch(setProfilesAC(data.content));
            dispatch(setPageNumberAC(data.number));
            dispatch(setTotalPagesAC(data.totalPages));
            dispatch(toggleIsFetchingAC(false));
            dispatch(toggleIsFetchedAC(true));
        })
        .catch(error => console.log(error));
};

const _setProfiles = (state, profiles) => ({ ...state, profiles });
const _setPageNumber = (state, number) => ({...state, pageNumber: number});
const _setTotalPages = (state, totalPages) => ({...state, totalPages});
const _toggleIsFetching = (state, isFetching) => ({ ...state, isFetching });
const _toggleIsFetched = (state, isFetched) => ({...state, isFetched});
