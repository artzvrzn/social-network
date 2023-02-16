import Keycloak from "keycloak-js";

const _kc = new Keycloak({
    url: "http://localhost:8181/",
    realm: "spring-boot",
    clientId: "react-ui",
    "enable-cors" : true
});

const initKeycloak = (onAuthenticatedCallback) => {
    _kc.init({onLoad: "login-required"})
        .then((authenticated) => {
            if (authenticated) {
                // alert(JSON.stringify(_kc.token))
                onAuthenticatedCallback();
            } else {
                console.warn("not authenticated");
                doLogin();
            }
        });
}

const doLogin = () => _kc.login();

const doLogout = () => _kc.logout();

const getToken = () => _kc.token;

const isAuthenticated = () => !!_kc.token;

const getUsername = () => _kc.tokenParsed.preferred_username;

const updateToken = (successCallback) => {
    _kc.updateToken(5)
        .then(successCallback)
        .catch(doLogin);
}

const AuthService = {
    initKeycloak,
    doLogin,
    doLogout,
    getToken,
    isAuthenticated,
    updateToken,
    getUsername
}

export default AuthService;