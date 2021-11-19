import React from 'react';
import { agentService } from '_services';
import { systemsService } from '_services';
import { Link } from "react-router-dom";
import { Formik, Form as FormFormik,  Field} from 'formik';
import { Button, Form } from 'react-bootstrap';

class AgentsList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            error : null,
            systemsList : null
        };
    }
    componentDidMount() {
         
         systemsService.find({activeTypeFilter : "ACTIVE"}).then(
            systemsList => {
                this.setState( { systemsList } );
            },
            error => {
                this.setState({error});
            }
        );
        
    }
    
    render() {
        const {systemsList} = this.state;
        
        return (
             <div className="form-search">
                <h2>Filtro de agentes</h2>
                <Formik key="searchUsers"
                    initialValues={{
                        name: '',
                        systemId: '',
                        activeTypeFilter: 'ALL'
                    }}
                    onSubmit={({ name, systemId, activeTypeFilter}, { setStatus, setSubmitting }) => {
                        setStatus();
                        agentService.find({name, systemId, activeTypeFilter})
                            .then(
                                agentsList => {
                                    setStatus( {result : agentsList });
                                    setSubmitting(false);
                                },
                                error => {
                                    setSubmitting(false);
                                    setStatus({error : error});
                                }
                            );
                    }}>
                    {({ status }) => (
                        <div>
                            <FormFormik >
                                <Form.Group>
                                    <Form.Label >Nombre:</Form.Label>
                                    <Field name="name" type="text" className="form-control" />
                                </Form.Group>
                                 <Form.Group>
                                    <Form.Label>Sistema</Form.Label>
                                    <Field name="systemId" as="select"  className="form-select" >
                                       <option value=""></option>
                                       {systemsList && systemsList.map(system => <option key={system.id} value={system.id}>{system.name}</option>)}
                                     </Field>
                                 </Form.Group>
                                <Form.Group>
                                    <Field name="activeTypeFilter" id="activeTypeFilterACTIVE" type="radio" className="form-check-input" value="ACTIVE" />
                                    <Form.Label htmlFor="activeTypeFilterACTIVE">Activos</Form.Label>
                                    <Field name="activeTypeFilter" id="activeTypeFilterINACTIVE" type="radio" className="form-check-input" value="INACTIVE" />
                                    <Form.Label htmlFor="activeTypeFilterINACTIVE">Inactivos</Form.Label>
                                    <Field name="activeTypeFilter" id="activeTypeFilterALL" type="radio" className="form-check-input" value="ALL" />
                                    <Form.Label htmlFor="activeTypeFilterALL">Todos</Form.Label>
                                </Form.Group>
                                <Form.Group>
                                    <Button variant="primary" type="submit">Buscar</Button>
                                    <Button href="/admin/agents/edit/0" variant="secondary" >Nuevo</Button>
                                </Form.Group>                            
                                {status && status.error &&
                                    <div className={'alert alert-danger'}>{status.error}</div>
                                }
                            </FormFormik>
                            {status && status.result &&
                               <table className="table table-striped table-hover">
                                  <thead>
                                    <tr>
                                      <th scope="col">#</th>
                                      <th scope="col">Nombre</th>
                                      <th scope="col">Sistema</th>
                                      <th scope="col">Activo</th>
                                    </tr>
                                  </thead>
                                  <tbody>
                                    {status.result.map(agent =>
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
                        </div>
                    )}
                 </Formik>
            </div>
        );
    }
}

export {AgentsList}
