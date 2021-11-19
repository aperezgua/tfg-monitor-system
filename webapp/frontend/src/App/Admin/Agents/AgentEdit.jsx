import React from 'react';
import { agentService } from '_services';
import { systemsService } from '_services';
import { Formik, Form as FormFormik,  Field, ErrorMessage} from 'formik';
import { Button, Form, Alert } from 'react-bootstrap';
import { useParams } from "react-router-dom";
import { AgentToken} from 'App/Admin/Agents';
import * as Yup from 'yup';


class AgentEditNoParams extends React.Component {
   constructor(props) {
        super(props);
        this.state = {
            agent : {
                token : '',
                name : '',
                systems : {},
                active : true
            },
            error : null,
            systemsList : null
        };
        this.onChangeTokenHandler = this.onChangeTokenHandler.bind(this);
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
        
        if (this.props.params.token && this.props.params.token != '0') {
            agentService.get(this.props.params.token).then(
                agent => {
                    this.setState( { agent , disabledToken : true} );
                    
                },
                error => {
                    this.setState({error});
                }
            );
        } 
    }
    
    onChangeTokenHandler(newToken) {
        this.setState({
          agent: {token : newToken}
        })
    }
    
    render() {
        const { error, agent, systemsList } = this.state;
        
        return (
             <div className="form-edit">
                <h2>Edición de agent</h2>
                {!error &&
                <Formik key="editUserFormik"
					enableReinitialize
                    initialValues={agent}
                    validationSchema={Yup.object().shape({
                        token: Yup.string().required('El token es obligatorio.'),
                        name: Yup.string().required('El nombre es obligatorio.')
                    })}
                    onSubmit={( values, { setStatus, setSubmitting }) => {
                        setStatus();
                        agentService.put(values)
                            .then(
                                updatedAgent => {    
                                    setStatus( {result : 'updated' });
                                    setSubmitting(false);
                                },
                                error => {
                                    setSubmitting(false);
                                    setStatus({error : error});
                                }
                            );
                    }}>
                    {({errors, status, touched}) => (
                        <FormFormik key="editAgentForm">
                            <Form.Group>
                                <AgentToken name="token" value={agent && agent.token} onChangeTokenHandler={this.onChangeTokenHandler}/>
                                 <ErrorMessage name="token" component="div" className="alert alert-error" />
                            </Form.Group>
                            <Form.Group>
                                <Form.Label >Nombre:</Form.Label>
                                <Field name="name" type="text" className={'form-control' + (errors.name && touched.name ? ' is-invalid' : '')} />
                                <ErrorMessage name="name" component="div" className="alert alert-error" />
                            </Form.Group>
                            <Form.Group>
                                <Form.Label>Sistema</Form.Label>
                                <Field name="systems.id" as="select"  className="form-select" >
                                   <option value=""></option>
                                   {systemsList && systemsList.map(system => <option key={system.id} value={system.id}>{system.name}</option>)}
                                 </Field>
                            </Form.Group>
                            <Form.Group>
                                <Form.Label>Activo:</Form.Label>
                                <Field name="active" as="select"  className="form-select" >
                                   <option value=""></option>
                                   <option value="true">Activo</option>
                                   <option value="false">Inactivo</option>
                                 </Field>
                            </Form.Group>
                            {status && status.error && <Alert key="alertError1" variant="danger" >{status.error}</Alert> }
                            {status && status.result && <Alert key="alertOk" variant="success" >{status.result}</Alert> }
                            <Form.Group>
                                <Button variant="primary" type="submit">Guardar</Button>
                            </Form.Group>
                        </FormFormik>
                    )}
                 </Formik>
                 }
                 {error && <Alert key="alertError2" variant="danger" >{error}</Alert>}
             </div>
        );
    }
}



function AgentEdit(props) {
    let params = useParams();
    return <AgentEditNoParams {...props} params={params} />
}
export {AgentEdit}