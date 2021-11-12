import React from 'react';
import { useNavigate, Link } from "react-router-dom";
import { authenticationService, userService } from '_services';

class HomePageNoNavigate extends React.Component {
    constructor(props) {
        super(props);
        if (!authenticationService.currentTokenValue) { 
            this.props.navigate('/');
        } else {
            this.state = {
                currentUser: authenticationService.currentUserValue,
                users : null
            };
        }
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
        const { currentUser , users} = this.state;
                
        return (             
            <div>
              {currentUser &&
                <nav className="navbar navbar-expand navbar-dark bg-dark">
                    <div className="navbar-nav">
                        <Link to="/home" className="nav-item nav-link">Home</Link>
                        <Link to="/" onClick={this.logout} className="nav-item nav-link">Logout</Link>
                    </div>
                </nav>
            }
              <h1>Hi {currentUser.sub}!</h1>
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


function HomePage(props) {
    let navigate = useNavigate();
    return <HomePageNoNavigate {...props} navigate={navigate} />
}
export {HomePage}
