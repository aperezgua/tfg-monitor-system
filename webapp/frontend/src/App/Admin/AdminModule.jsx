import React from 'react';
import { Link } from "react-router-dom";
import { authenticationService } from '_services';
import { Route, Routes, useNavigate} from 'react-router-dom'
import { HomePage, UsersPage} from 'App/Admin';
import { NotFound } from 'App/NotFound';

class AdminModule extends React.Component {
    constructor(props) {
        super(props);       
        this.state = {
            currentUser: authenticationService.currentUserValue,
            users : null
        };
        
    }
    
    logout() {
        authenticationService.logout();
    }    
    render() {
        const { currentUser} = this.state;
        return (
            <div>
                {currentUser && <nav className="navbar navbar-expand navbar-dark bg-dark">
                        <div className="navbar-nav">
                            <Link to="/admin/home" className="nav-item nav-link">Home</Link>
                            <Link to="/admin/users" className="nav-item nav-link">Users</Link>
                            <Link to="/" onClick={this.logout} className="nav-item nav-link">Logout</Link>
                        </div>
                   </nav>}
                {currentUser && <h1>Hi {currentUser.sub}!</h1>}
                 {currentUser && 
                 <Routes>
                    <Route path="/home" element={<HomePage/>} />
                    <Route path="/users" element={<UsersPage/>} />
                    <Route path="/*" element={NotFound}/>
                </Routes>
                }
                 {!currentUser
                 && <div className="alert alert-error">
                        No autorizado.
                    </div> 
                 }
            </div>
        );
    }
}


export {AdminModule}
