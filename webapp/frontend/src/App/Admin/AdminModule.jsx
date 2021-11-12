import React from 'react';
import { Link } from "react-router-dom";
import { authenticationService } from '_services';
import { Route, Routes, useNavigate} from 'react-router-dom'
import { HomePage, UsersPage} from 'App/Admin';
import { NotFound } from 'App/NotFound';
import './AdminModule.css';

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
            <div className="admin-background">
                {currentUser && 
                    <nav className="navbar navbar-default">
                        <div className="container-fluid">
                         
                            <ul className="nav navbar-nav">
                                <li className="active"><Link to="/admin/home" >Home</Link></li>
                                <li><Link to="/admin/users" >Users</Link></li>
                                
                            </ul>
                            <ul className="nav navbar-nav navbar-right">
                                <li><Link to="/" onClick={this.logout} >Logout</Link></li>
                            </ul>
                            
                            <p className="navbar-text navbar-right">Signed in as <a href="#" className="navbar-link">{currentUser.sub}</a></p>
                            </div>
                    
                    </nav>
                    
                }   
                {currentUser &&
                   <div> 
                     <Routes>
                        <Route path="/home" element={<HomePage/>} />
                        <Route path="/users" element={<UsersPage/>} />
                        <Route path="/*" element={NotFound}/>
                    </Routes>
                   </div>
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
