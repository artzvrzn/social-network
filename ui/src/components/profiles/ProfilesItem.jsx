import React from "react";
import {Link, NavLink} from "react-router-dom";

const ProfilesItem = (props) => {
    // const imgRef = React.createRef();
    return (
        <div>
            <NavLink to={`/profile/${props.user.id}`}>
                <div>
                    <img src={props.user.imageSmall ?? "https://img1.ak.crunchyroll.com/i/spire1/75188c9e255b8acb03fabe5ec1da4f1d1627702728_large.png"}/>
                </div>
            </NavLink>
            <div>
                <p>id {props.user.id} {props.user.username} </p>
                <p>{`${props.user.name} ${props.user.familyName}`}</p>
            </div>
        </div>
    );
}

export default ProfilesItem;