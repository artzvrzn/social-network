import React from "react";
import {useParams} from "react-router-dom";
import Preloader from "../components/preloader/Preloader";

// function withParamsHOC(Component) {
//     return props => {
//         return <Component {...props} params={useParams()} />;
//     }
// }

const withParamsHOC = (Component) => (props) => {
    return <Component {...props} params={useParams()} />;
}

const withPreloaderHOC = (Component) => (props) => {
    return props.isFetching ? <Preloader /> : <Component {...props}/>;
};

export {withParamsHOC, withPreloaderHOC};