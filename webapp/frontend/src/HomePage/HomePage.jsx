import React from 'react';
import {  Navigate  } from 'react-router-dom';
import { authenticationService } from '_services';

class HomePage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            currentUser: authenticationService.currentUserValue
        };
    }
    render() {
        const { currentUser } = this.state;
        return !currentUser ? <Navigate to="/login" /> : (<div>LOGGED</div>);
    }
}

export { HomePage };