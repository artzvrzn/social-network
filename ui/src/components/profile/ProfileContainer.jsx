import React from "react";
import {connect} from "react-redux";
import {getProfileThunk} from "../../service/store/reducer/ProfileReducer";
import Profile from "./Profile";
import {withParamsHOC, withPreloaderHOC} from "../../hoc/Hocs";
import {compose} from "redux";

class ProfileContainer extends React.Component {

    render() {
        return <Profile profile={!this.props.isFetched ? {} : this.props.profile} />;
    }

    componentDidMount() {
        if (!this.props.isFetched) {
            let {id} = this.props.params;
            this.props.getProfile(id);
        }
    }
}

const mapStateToProps = (state) => {
    return {
        profile: state.profilePage.profile,
        isFetching: state.profilePage.isFetching,
        isFetched: state.profilePage.isFetched
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        getProfile: (id) => dispatch(getProfileThunk(id))
    }
}

export default compose(
    connect(mapStateToProps, mapDispatchToProps),
    withPreloaderHOC,
    withParamsHOC
)(ProfileContainer);

//same as
// export default connect(mapStateToProps, mapDispatchToProps)(withPreloaderHOC(withParamsHOC(ProfileContainer)));
//(order matters)