import React from 'react';
import { authenticationService } from '_services';
import { Route, Routes,  Link } from 'react-router-dom'
import { HomePage, UsersPage, SystemsPage, AgentsPage} from 'App/Admin';
import { NotFound } from 'App/NotFound';
import { Navbar, Nav } from 'react-bootstrap';
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
            <div className="admin-page">
                <div className="admin-header">
                    {currentUser && 
                        <Navbar bg="light" expand="lg">
                            <Nav className="me-auto">
                                <Link to="/admin/home" className="nav-link">Home</Link>
                                <Link to="/admin/users/list"  className="nav-link">Usuarios</Link>
                                <Link to="/admin/systems/list" className="nav-link">Sistemas</Link>
                                <Link to="/admin/agents/list" className="nav-link">Agentes</Link>
                             </Nav>
                             <Nav className="justify-content-end">
                                <Navbar.Text>[{currentUser.sub}] ::</Navbar.Text>
                                <Link to="/" onClick={this.logout}  className="nav-link">Salir</Link>
                             </Nav>
                        </Navbar>
                        
                    }
                </div>
                <div className="admin-body"> 
                    {currentUser &&
                       <div> 
                         <Routes>
                            <Route path="/home" element={<HomePage/>} />
                            <Route path="/users/*" element={<UsersPage/>} />
                            <Route path="/systems/*" element={<SystemsPage/>} />
                            <Route path="/agents/*" element={<AgentsPage/>} />
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
            </div>
        );
    }
}


export {AdminModule}
