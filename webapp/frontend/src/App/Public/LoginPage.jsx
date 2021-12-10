import React from 'react';
import { Formik, Field, Form as FormFormik, ErrorMessage } from 'formik';
import { Form, Button, Alert,  Row, Col } from 'react-bootstrap';
import * as Yup from 'yup';
import { useNavigate} from "react-router-dom";
import { authenticationService } from '_services';
import './LoginPage.css';

class LoginPageNoNavigate extends React.Component {
    
    constructor(props) {
        super(props);

        // redirect to home if already logged in
        if (authenticationService.currentUserValue) { 
          
          //this.props.navigate('/home');
          console.log("Redirect en LoginPageNoNavigate");
          this.props.navigate('/admin/home');
        }
    }

    render() {
        return (
            <div className="form-login">
                <h2>Login</h2>
                <Formik
                    initialValues={{
                        username: '',
                        password: ''
                    }}
                    validationSchema={Yup.object().shape({
                        username: Yup.string().required('El email es obligatorio.'),
                        password: Yup.string().required('La clave es obligatoria.')
                    })}
                    onSubmit={({ username, password }, { setStatus, setSubmitting }) => {
                        setStatus();
                        authenticationService.login(username, password)
                            .then(
                                token => {
                                    /*const { from } = this.props.location.state || { from: { pathname: "/" } };
                                    console.log("Aqui: " +from);
                                    this.props.navigate(from);*/
                                    // this.props.navigate('/home');
                                    // Si se recibe token, quiere decir que estamos loggeados
                                    console.log("Redirect  post auth.");
                                    if(authenticationService.currentUserValue.rol === 'ADMINISTRATOR') {
                                        this.props.navigate('/admin/home');
                                    } else if(authenticationService.currentUserValue.rol === 'SUPPORT') {
                                        this.props.navigate('/support/home');
                                    } else {
                                        this.props.navigate('/notFound');
                                    }
                                },
                                error => {
                                    console.log("Error: " +(typeof error) + " " + error);
                                    setSubmitting(false);
                                    if(typeof(error) == "object") {
                                        setStatus(['Cannot connect to server.']);
                                    } else {
                                        setStatus(error);
                                    }
                                }
                            );
                    }}>
                    {({ errors, status, touched, isSubmitting }) => (
                        <FormFormik>
                            <Form.Group>
                                <Form.Label>Email</Form.Label>
                                <Field name="username" type="text" className={'form-control' + (errors.username && touched.username ? ' is-invalid' : '')} />
                                <ErrorMessage name="username" component="div" className="alert alert-error" />
                            </Form.Group>
                            <Form.Group>
                                <Form.Label>Password</Form.Label>
                                <Field name="password" type="password" className={'form-control' + (errors.password && touched.password ? ' is-invalid' : '')} />
                                <ErrorMessage name="password" component="div" className="alert alert-error" />
                            </Form.Group>
                            
                            <Row>
                                <Col lg="12" className="text-center button-login">
                                    <Form.Group>
                                         <Button variant="primary" type="submit" disabled={isSubmitting}>Login</Button>
                                    </Form.Group>
                                 </Col>
                             </Row>
                            <br/>
                            { status && <Alert key="alertInfo1" variant="danger" >{status}</Alert> }
                        </FormFormik>
                    )}
                 </Formik>
                 <Alert key="alertInfo1" variant="info" >
                    <strong>Usuario admin:</strong>
                    <hr/>
                    Username: aperezgua@uoc.edu<br />
                    Password: pw
                </Alert>
                <Alert key="alertInfo2" variant="info" >
                    <strong>Usuario soporte:</strong>
                    <hr/>
                    Username: sam@uoc.edu<br />
                    Password: pw
                </Alert>
            </div>
        )
    }
}

function LoginPage(props) {
    let navigate = useNavigate();
    return <LoginPageNoNavigate {...props} navigate={navigate} />
}
export {LoginPage}

