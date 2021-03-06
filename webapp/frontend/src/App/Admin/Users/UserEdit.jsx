import React from 'react';
import { userService } from '_services';
import { Formik, Form as FormFormik,  Field, ErrorMessage} from 'formik';
import { Button, Form, Alert, Row, Col } from 'react-bootstrap';
import { useParams } from "react-router-dom";
import * as Yup from 'yup';

class UserEditNoParams extends React.Component {
   constructor(props) {
        super(props);
        this.state = {
            user : {
                name : '',
                email : '',
                password : '',
                active : true,
                rol : 'ADMINISTRATOR'
            },
            error : null
        };
        
    }
    componentDidMount() {
        if (this.props.params.id && this.props.params.id > 0) {
            userService.get(this.props.params.id).then(
                user => {
                    this.setState( { user } );
                },
                error => {
                    this.setState({error});
                }
            );
        } else {
            this.setState( { user : {
                name : '',
                email : '',
                password : '',
                active : true,
                rol : 'ADMINISTRATOR'
            } } );
        }
    }
    render() {
        let { error, user } = this.state;
        return (
             <div className="form-edit">
                <h2>Edición de usuario</h2>
                {!error &&
                <Formik key="editUserFormik"
					enableReinitialize
                    initialValues={user}
                    validationSchema={Yup.object().shape({
                        name: Yup.string().required('El nombre es obligatorio.'),
                        email: Yup.string().required('El email es obligatorio.'),
                        password: Yup.string().required('La clave es obligatoria.'),
                        rol: Yup.string().required('El rol es obligatorio.'),
                        active: Yup.string().required('Debe especificar si está activo o no.')
                    })}
                    onSubmit={( values, { setStatus, setSubmitting }) => {
                        setStatus();
                        userService.put(values)
                            .then(
                                updatedUser => {
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
                        <FormFormik key="editUserForm">
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
                                        <label htmlFor="email">Email:</label>
                                        <Field name="email" type="text" className={'form-control' + (errors.email && touched.email ? ' is-invalid' : '')} />
                                        <ErrorMessage name="email" component="div" className="alert alert-error" />
                                    </Form.Group>
                               </Col>
                               <Col lg="6">
                                    <Form.Group>
                                        <label htmlFor="password">Password:</label>
                                        <Field name="password" type="password" className={'form-control' + (errors.password && touched.password ? ' is-invalid' : '')} />
                                        <ErrorMessage name="password" component="div" className="alert alert-error" />
                                    </Form.Group>
                               </Col>
                             </Row>
                             <Row>
                                <Col lg="6">
                                    <Form.Group>
                                        <Form.Label>Rol:</Form.Label>
                                        <Field name="rol" as="select"  className={'form-select' + (errors.rol && touched.rol ? ' is-invalid' : '')} >
                                           <option value=""></option>
                                           <option value="ADMINISTRATOR">Administrador</option>
                                           <option value="SUPPORT">Soporte</option>
                                         </Field>
                                         <ErrorMessage name="rol" component="div" className="alert alert-error" />
                                    </Form.Group>
                                </Col>
                                <Col lg="6">
                                    <Form.Group>
                                        <Form.Label>Activo:</Form.Label>
                                        <Field name="active" as="select" className={'form-select' + (errors.active && touched.active ? ' is-invalid' : '')}>
                                           <option value=""></option>
                                           <option value="true">Activo</option>
                                           <option value="false">Inactivo</option>
                                         </Field>
                                         <ErrorMessage name="active" component="div" className="alert alert-error" />
                                    </Form.Group>
                                </Col>
                             </Row>
                              <Row>
                                <Col lg="12" className="text-center">
                                    <br/>
                                     {status && status.error && <Alert key="alertError1" variant="danger" >{status.error}</Alert> }
                                     {status && status.result && <Alert key="alertOk" variant="success" >Se han guardado los datos correctamente</Alert> }
                                    <Form.Group >                                        
                                        <Button variant="primary" type="submit">Guardar</Button>
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



function UserEdit(props) {
    let params = useParams();
    return <UserEditNoParams {...props} params={params} />
}
export {UserEdit}