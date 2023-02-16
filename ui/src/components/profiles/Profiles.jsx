import React from "react";
import ProfilesItem from "./ProfilesItem";
import Pagination from "../pagination/Pagination";

const Profiles = (props) => {
    return (
        <div>
            <Pagination pageNumber={props.pageNumber} totalPages={props.totalPages} onPageChanged={props.onPageChanged} />
            <div>{props.profiles.map(profile => <ProfilesItem user={profile} onUserClicked={props.onUserClicked}/>)}</div>
        </div>
    );
}

export default Profiles;