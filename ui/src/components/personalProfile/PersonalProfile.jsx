import React from "react";

const PersonalProfile = (props) => {
    return <div>
        <p>{JSON.stringify(props.profile)}</p>
    </div>;
}

export default PersonalProfile;