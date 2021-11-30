import React from 'react';
import { authenticationService } from '_services';
import { Route, Routes} from 'react-router-dom'
import { SupportHomePage} from 'App/Support';
import { NotFound } from 'App/NotFound';
import { Navbar, Nav } from 'react-bootstrap';
import './SupportModule.css';

/** Modulo principal de soporte */
class SupportModule extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            currentUser: authenticationService.currentUserValue
        };
        
    }
    
    logout() {
        authenticationService.logout();
    }    
    
    render() {
        const { currentUser} = this.state;
        
        return (
            <div className="support-page">
                <div className="admin-header">
                    {currentUser && 
                        <Navbar bg="light" expand="lg">
                            <Nav className="me-auto">
                                <Nav.Link href="/support/home">Home</Nav.Link>
                             </Nav>
                             <Nav className="justify-content-end">
                                <Navbar.Text>[{currentUser.sub}] ::</Navbar.Text>
                                <Nav.Link href="/" onClick={this.logout}>Salir</Nav.Link>
                             </Nav>
                        </Navbar>
                        
                    }
                </div>
                <div className="supoort-body"> 
                    {currentUser &&
                       <div> 
                         <Routes>
                            <Route path="/home" element={<SupportHomePage/>} />
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


export {SupportModule}
