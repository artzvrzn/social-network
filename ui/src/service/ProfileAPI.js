import axios from "axios";
import AuthService from "./AuthService";

const _axios = axios.create({
    baseURL: 'http://localhost:8080',
});

const getProfiles = (page, size) => {
    return _getRequest(`/profile/user?page=${page}&size=${size}`);
};

const getMyProfile = () => {
    return _getRequest("/profile/user/me");
};

const getProfile = (id) => {
    return _getRequest(`/profile/user/${id}`);
};

const followProfile = (id) => {
    return _axios.post(
        `/profile/subscription/follow/${id}`,
        {},
        { headers: {
            Authorization: `Bearer ${AuthService.getToken()}`
        }}
    ).catch(error => console.log(error))
}

const unfollowProfile = (id) => {
    return _axios.delete(
        `/profile/subscription/unfollow/${id}`,
        { headers: {
            Authorization: `Bearer ${AuthService.getToken()}`
        }}
    ).catch(error => console.log(error))
}


const _getRequest = (path) => {
    return _axios.get(path, { headers: { Authorization: `Bearer ${AuthService.getToken()}` } })
        .then(response => response.data)
        .catch(error => console.log(error));
}

const ProfileAPI = {
    getMyProfile,
    getProfile,
    getProfiles,
    followProfile,
    unfollowProfile
}

export default ProfileAPI;