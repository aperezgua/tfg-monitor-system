import React from 'react';
import { authenticationService, systemsService, eventLogService } from '_services';
import { Route, Routes} from 'react-router-dom'
import { Navbar, Nav, Form } from 'react-bootstrap';
import Select from 'react-select'

import { SupportHomePage, RuleEventsPage} from 'App/Support';
import { NotFound } from 'App/NotFound';

import './SupportModule.css';

/** Modulo principal de soporte */
class SupportModule extends React.Component {
    constructor(props) {
        super(props);
        
        this.state = {
            currentUser: authenticationService.currentUserValue,
            eventList : [],
            eventFilter : eventLogService.getEventFilter()
        };
        
    }
    
    logout() {
        authenticationService.logout();
    }
    
    /** Cuando es creado el componente se llama al servicio para cargar los sistemas y si se especifica token, los datos
        del agente */
    componentDidMount() {
        systemsService.find({}).then(
            systemsList => {
                let data = [];
                for(var i=0; i < systemsList.length; i++) {
                    data.push({value : systemsList[i].id, label : systemsList[i].name});
                }
                                
                this.setState( { systemsList : data } );
            },
            error => {
                this.setState({error});
            }
        ) ;
    }
    
    render() {
        const { currentUser, eventFilter} = this.state;
        
        return (
            <div className="support-page">
                <div className="support-header">
                    {currentUser && 
                        <Navbar bg="light" expand="lg">
                            <Nav className="me-auto">
                                <Nav.Link href="/support/home">Home</Nav.Link>
                             </Nav>
                             <Nav  className="justify-content-end">
                                <Nav.Item>
                                    <Select options={this.state.systemsList} 
                                            isMulti
                                            value={eventFilter.systems}
                                            onChange={e => {
                                                let eventFilter = this.state.eventFilter;
                                                eventFilter.systems = e;
                                                eventLogService.saveEventFilter(eventFilter);
                                                this.setState({eventFilter});
                                              }}
                                        />
                                </Nav.Item>
                                <Nav.Item>
                                    <Form.Control
                                          as="select"
                                          value={eventFilter.lastTimeInSeconds}
                                          onChange={e => {
                                            
                                            let eventFilter = this.state.eventFilter;
                                            eventFilter.lastTimeInSeconds = e.target.value;
                                            eventLogService.saveEventFilter(eventFilter);
                                            this.setState({eventFilter});
                                          }}
                                        >
                                      <option></option>
                                      <option value="60">&Uacute;ltimo minuto</option>
                                      <option value="300">&Uacute;ltimos 5 minutos</option>
                                      <option value="600">&Uacute;ltimos 10 minutos</option>
                                    </Form.Control>
                                </Nav.Item>
                             </Nav>                             
                             <Nav className="justify-content-end">
                                <Navbar.Text>[{currentUser.sub}] ::</Navbar.Text>
                                <Nav.Link href="/" onClick={this.logout}>Salir</Nav.Link>
                             </Nav>
                        </Navbar>
                        
                    }
                </div>
                <div className="support-body"> 
                    {currentUser &&
                         <Routes>
                            <Route path="home" element={<SupportHomePage/>} />
                            <Route path="view/:token/:rule" element={<RuleEventsPage/>} />
                            <Route path="/*" element={NotFound}/>
                        </Routes>
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
