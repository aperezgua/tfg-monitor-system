import React from 'react';
import { userService } from '_services';

class HomePage extends React.Component {
    constructor(props) {
        super(props);
        console.log("Entro en HomePage");
        this.state = {
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
        const {  users} = this.state;
        return (             
            <div>              
             <h3>Users from secure api end point:</h3>
                {users &&
                    <ul>
                        {users.map(user =>
                            <li key={user.id}>{user.name} {user.email}</li>
                        )}
                    </ul>
                }
             </div>
        );
    }
}
export {HomePage}
