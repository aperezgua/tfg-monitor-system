import React from 'react';
import { Navigate  } from 'react-router-dom';
import { authenticationService, userService } from '_services';

class HomePage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            currentToken: authenticationService.currentTokenValue,
            users : null
        };
    }
    
    componentDidMount() {
        userService.getAll().then(
            users => this.setState({ users }),
            error => {
                console.log("Error: " +(typeof error) + " " + error);
            }
        );
    }
    
    render() {
        const { currentToken } = this.state;
        return !currentToken ? <Navigate to="/" /> : (<div>LOGGED</div>);
    }
}

export { HomePage };