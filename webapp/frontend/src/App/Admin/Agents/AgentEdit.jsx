import React from 'react';
import { agentService, logService} from '_services';
import { systemsService } from '_services';
import { Formik, Form as FormFormik,  Field, ErrorMessage} from 'formik';
import { Button, Form, Alert, Row, Col } from 'react-bootstrap';
import { useParams } from "react-router-dom";
import { AgentToken, AgentRules } from 'App/Admin/Agents';
import * as Yup from 'yup';

/** Componente para editar un agente */
class AgentEditNoParams extends React.Component {
    
   /** Constructor dónde son inicializados los datos del state */
   constructor(props) {
        super(props);
        this.state = {
            agent : {
                token : '',
                name : '',
                systems : {},
                active : true,
                rules : []
            },
            error : null,
            systemsList : null
        };
    }
    
    /** Cuando es creado el componente se llama al servicio para cargar los sistemas y si se especifica token, los datos
        del agente */
    componentDidMount() {
         
         systemsService.find({activeTypeFilter : "ACTIVE"}).then(
            systemsList => {
                this.setState( { systemsList } );
            },
            error => {
                this.setState({error});
            }
        );
        
        if (this.props.params.token && this.props.params.token !== '0') {
            agentService.get(this.props.params.token).then(
                agent => {
                    
                    if(!agent.rules) {
                        agent.rules = [];
                    }
                    
                    this.setState( {agent} );
                },
                error => {
                    this.setState({error});
                }
            );
        } 
    }
    
        
    render() {
        const { error, agent, systemsList } = this.state;
        
        return (
             <div className="form-edit">
                <h2>Edición de agente</h2>
                {!error &&
                <Formik key="editAgentFormik"
					enableReinitialize
                    initialValues={agent}
                    validationSchema={Yup.object().shape({
                        token: Yup.string().required('El token es obligatorio.'),
                        name: Yup.string().required('El nombre es obligatorio.')
                    })}
                    onSubmit={( values, { setStatus, setSubmitting }) => {
                        setSubmitting(true)
                        setStatus();
                        
                        agentService.put(values)
                            .then(
                                
                                updatedAgent => {    
                                    
                                    
                                    logService.updateAgentEvents(values.token).then(
                                        ok => {
                                            console.log("OK" +ok);
                                            setStatus( {result : 'updated' });
                                            setSubmitting(false);    
                                        },
                                        errorLog => {  setSubmitting(false);
                                            setStatus({error : errorLog});
                                        }
                                    );
                                    
                                },
                                error => {
                                    setSubmitting(false);
                                    setStatus({error : error});
                                }
                            );
                        
                    }}>
                    {({values, errors, status, isSubmitting, touched, setFieldValue}) => (
                        <FormFormik key="editAgentForm">
                            <Row>
                               <Col lg="12">
                                    <Form.Group>
                                        <AgentToken name="token" value={values && values.token} setFieldValue={setFieldValue}/>
                                        <ErrorMessage name="token" component="div" className="alert alert-error" />
                                    </Form.Group>
                               </Col>
                            </Row>
                            <Row>
                                <Col lg="12">
                                    <Form.Group>
                                        <Form.Label >Nombre:</Form.Label>
                                        <Field name="name" type="text" className={'form-control' + (errors.name && touched.name ? ' is-invalid' : '')} />
                                        <ErrorMessage name="name" component="div" className="alert alert-error" />
                                    </Form.Group>
                                </Col>
                            </Row>
                            <Row>
                                <Col lg="6">
                                    <Form.Group>
                                        <Form.Label>Sistema</Form.Label>
                                        <Field name="systems.id" as="select"  className="form-select" >
                                           <option value=""></option>
                                           {systemsList && systemsList.map(system => <option key={system.id} value={system.id}>{system.name}</option>)}
                                         </Field>
                                    </Form.Group>
                                </Col>
                                <Col lg="6">
                                    <Form.Group>
                                        <Form.Label>Activo:</Form.Label>
                                        <Field name="active" as="select"  className="form-select" >
                                           <option value=""></option>
                                           <option value="true">Activo</option>
                                           <option value="false">Inactivo</option>
                                         </Field>
                                    </Form.Group>
                                 </Col>
                            </Row>
                            <Row>
                                <Col lg="12">
                                    <AgentRules value={values && values.rules} agentTokenId={values && values.token}  name="rules" setFieldValue={setFieldValue} />
                                 </Col>
                            </Row>
                            <Row>
                                <Col lg="12"  className="text-center">
                                    <br/>
                                    {status && status.error && <Alert key="alertError1" variant="danger" >{status.error}</Alert> }
                                    {status && status.result && <Alert key="alertOk" variant="success" >Se ha guardado correctamente</Alert> }
                                    <Form.Group>
                                        <Button variant="primary" type="submit" disabled={isSubmitting}>Guardar</Button> 
                                        {isSubmitting && <Alert key="submitting" variant="primary" >Se están actualizando datos, espere</Alert>}
                                    </Form.Group>
                                </Col>
                            </Row>
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