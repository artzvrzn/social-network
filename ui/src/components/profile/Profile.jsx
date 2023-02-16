import React from "react";

const Profile = (props) => {
    return (
        <div>
            {/*PROFILE ${props.profile.id}*/}
            <div>
                <img src={props.profile.imageSmall ?? "https://img1.ak.crunchyroll.com/i/spire1/75188c9e255b8acb03fabe5ec1da4f1d1627702728_large.png"}/>
            </div>
            <div>
                <p>id {props.profile.id} {props.profile.username} </p>
                <p>{`${props.profile.name} ${props.profile.familyName}`}</p>
            </div>
        </div>
    );
}

export default Profile;