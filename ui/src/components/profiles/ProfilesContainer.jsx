import React from "react";
import {connect} from "react-redux";
import Profiles from "./Profiles";
import {getProfilesThunk} from "../../service/store/reducer/ProfilesReducer";
import {withPreloaderHOC} from "../../hoc/Hocs";
import {compose} from "redux";

class ProfilesContainer extends React.Component {

    render() {
        if (this.props.profiles === null) {
            return <></>
        }
        return (
            <Profiles
                profiles={this.props.profiles}
                pageNumber={this.props.pageNumber}
                totalPages={this.props.totalPages}
                onPageChanged={this.onPageChanged}
                onUserClicked={this.onUserClicked}
            />
        );
    }

    componentDidMount() {
        if (!this.props.isFetched) {
            this.props.getProfiles(this.props.pageNumber, this.props.pageSize);
        }
    }

    onPageChanged = (pageNumber) => {
        this.props.getProfiles(pageNumber, this.props.pageSize);
    }
}

const mapStateToProps = (state) => {
    return {
        profiles: state.profilesPage.profiles,
        pageNumber: state.profilesPage.pageNumber,
        pageSize: state.profilesPage.pageSize,
        totalPages: state.profilesPage.totalPages,
        isFetching: state.profilesPage.isFetching,
        isFetched: state.profilesPage.isFetched
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        getProfiles: (pageNumber, pageSize) => dispatch(getProfilesThunk(pageNumber, pageSize))
    }
}

export default compose(
    connect(mapStateToProps, mapDispatchToProps),
    withPreloaderHOC
)(ProfilesContainer);

// export default connect(mapStateToProps, mapDispatchToProps)(withPreloaderHOC(ProfilesContainer));