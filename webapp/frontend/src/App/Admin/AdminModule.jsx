import React from 'react';
import { authenticationService } from '_services';
import { Route, Routes, useLocation} from 'react-router-dom'
import { HomePage, UsersPage} from 'App/Admin';
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
                                <Nav.Link href="/admin/home">Home</Nav.Link>
                                <Nav.Link href="/admin/users">Usuarios</Nav.Link>
                             </Nav>
                             <Nav className="justify-content-end">
                                <Navbar.Text>[{currentUser.sub}] ::</Navbar.Text>
                                <Nav.Link href="/" onClick={this.logout}>Salir</Nav.Link>
                             </Nav>
                        </Navbar>
                        
                    }
                </div>
                <div className="admin-body"> 
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
            </div>
        );
    }
}


export {AdminModule}
