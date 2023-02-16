import React from "react";
import {connect} from "react-redux";
import {getPersonalProfileThunk} from "../../service/store/reducer/PersonalProfileReducer";
import PersonalProfile from "./PersonalProfile";
import {withPreloaderHOC} from "../../hoc/Hocs";
import {compose} from "redux";

class PersonalProfileContainer extends React.Component {

    render() {
        return <PersonalProfile profile={!this.props.isFetched ? {} : this.props.profile} />;
    }

    componentDidMount() {
        if (!this.props.isFetched) {
            this.props.getProfile();
        }
    }
}

const mapStateToProps = (state) => {
    return {
        profile: state.personalProfilePage.profile,
        isFetching: state.personalProfilePage.isFetching,
        isFetched: state.personalProfilePage.isFetched
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        getProfile: () => dispatch(getPersonalProfileThunk())
    }
}

export default compose(
    connect(mapStateToProps, mapDispatchToProps),
    withPreloaderHOC
)(PersonalProfileContainer);
