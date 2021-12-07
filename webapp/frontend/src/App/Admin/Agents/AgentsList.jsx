import React from 'react';
import { agentService } from '_services';
import { systemsService } from '_services';
import { Link } from "react-router-dom";
import { Formik, Form as FormFormik,  Field} from 'formik';
import { Button, Form, Row, Col, Alert } from 'react-bootstrap';

/** Componente de listado de agentes */
class AgentsList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            error : null,
            systemsList : null,
            agentList : null
        };
        
        this.findAgents = this.findAgents.bind(this);
    }
    
    /** Busqueda de agentes */
    findAgents(name, systemId, activeTypeFilter) {
        agentService.find({name, systemId, activeTypeFilter})
            .then(
                agentsList => {
                    this.setState( {agentList : agentsList });
                },
                error => {
                    this.setState({error : error});
                }
            );
    }
    
    /** Cuando se carga el componenete */
    componentDidMount() {
         
         systemsService.find({activeTypeFilter : "ACTIVE"}).then(
            systemsList => {
                this.setState( { systemsList } );
            },
            error => {
                this.setState({error});
            }
        );
        
        this.findAgents('', '', 'ALL');
    }
    
    render() {
        const {systemsList, agentList, error} = this.state;
        
        return (
             <div className="form-search">
                <h2>Filtro de agentes</h2>
                <Formik key="searchUsers"
                    initialValues={{
                        name: '',
                        systemId: '',
                        activeTypeFilter: 'ALL'
                    }}
                    onSubmit={({ name, systemId, activeTypeFilter}) => {
                        this.findAgents(name, systemId,activeTypeFilter );
                    }}>
                    {() => (
                        <div>
                            <FormFormik >
                                 <Row>
                                    <Col lg="2">
                                        <Form.Label htmlFor="activeTypeFilterACTIVE">Estado</Form.Label>
                                        <Form.Group>
                                            <Field name="activeTypeFilter" id="activeTypeFilterACTIVE" type="radio" className="form-check-input" value="ACTIVE" />
                                            <Form.Label htmlFor="activeTypeFilterACTIVE">Activos</Form.Label>
                                        </Form.Group>
                                        <Form.Group>
                                            <Field name="activeTypeFilter" id="activeTypeFilterINACTIVE" type="radio" className="form-check-input" value="INACTIVE" />
                                            <Form.Label htmlFor="activeTypeFilterINACTIVE">Inactivos</Form.Label>
                                         </Form.Group>
                                         <Form.Group>
                                            <Field name="activeTypeFilter" id="activeTypeFilterALL" type="radio" className="form-check-input" value="ALL" />
                                            <Form.Label htmlFor="activeTypeFilterALL">Todos</Form.Label>
                                        </Form.Group>
                                    </Col>
                                    <Col lg="5">
                                        <Form.Group>
                                            <Form.Label >Nombre:</Form.Label>
                                            <Field name="name" type="text" className="form-control" />
                                        </Form.Group>
                                     </Col>
                                    <Col lg="5">
                                         <Form.Group>
                                            <Form.Label>Sistema</Form.Label>
                                            <Field name="systemId" as="select"  className="form-select" >
                                               <option value=""></option>
                                               {systemsList && systemsList.map(system => <option key={system.id} value={system.id}>{system.name}</option>)}
                                             </Field>
                                         </Form.Group>
                                    </Col>
                                </Row>
                                 <Row>
                                    <Col lg="12" className="text-center">
                                         <Form.Group>
                                            <Button variant="primary" type="submit">Buscar</Button>  &nbsp;
                                            <Link to="/admin/agents/edit/0" className="btn btn-secondary" >Nuevo</Link>
                                        </Form.Group>
                                     </Col>
                                </Row>
                            </FormFormik>
                        </div>
                    )}
                 </Formik>
                 {agentList &&
                       <table className="table table-striped table-hover">
                          <thead>
                            <tr>
                              <th scope="col">#</th>
                              <th scope="col">Nombre</th>
                              <th scope="col">Sistema</th>
                              <th scope="col">Activo</th>
                              <th scope="col"></th>
                            </tr>
                          </thead>
                          <tbody>
                            {agentList.map(agent =>
                                <tr key={'tr' +agent.token}>
                                  <th scope="row">{agent.token}</th>
                                  <td>{agent.name}</td>
                                  <td>{agent.systems && agent.systems.name}</td>
                                  <td>{agent.active && agent.active? 'Si' : 'No'}</td>
                                  <td><Link to={'/admin/agents/edit/' +agent.token} >Ver</Link></td>
                                </tr>
                            )}
                          </tbody>
                       </table>
                    }
                    {error && <Alert key="alertError" variant="danger" >{error}</Alert> }
            </div>
        );
    }
}

export {AgentsList}
