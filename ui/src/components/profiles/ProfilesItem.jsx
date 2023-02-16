import React from "react";
import {Link, NavLink} from "react-router-dom";

const ProfilesItem = (props) => {
    // const imgRef = React.createRef();
    const profile = props.profile;
    return (
        <div>
            <NavLink to={`/profile/${profile.id}`}>
                <div>
                    <img src={profile.imageSmall ?? "https://img1.ak.crunchyroll.com/i/spire1/75188c9e255b8acb03fabe5ec1da4f1d1627702728_large.png"}/>
                </div>
            </NavLink>
            <div>
                <p>id {profile.id} {profile.username} </p>
                <p>{`${profile.name} ${profile.familyName}`}</p>
            </div>
            <div>
                {profile.isSubscribed ?
                    <button disabled={props.subscriptionInProgress.some(id => id === profile.id)}
                            onClick={() => props.onUnfollowClicked(profile.id)}>Unfollow</button> :
                    <button disabled={props.subscriptionInProgress.some(id => id === profile.id)}
                            onClick={() => props.onFollowClicked(profile.id)}>Follow</button>}
            </div>
        </div>
    );
}

export default ProfilesItem;