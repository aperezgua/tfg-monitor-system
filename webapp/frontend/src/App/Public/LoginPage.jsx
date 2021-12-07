import React from 'react';
import { Formik, Field, Form, ErrorMessage } from 'formik';
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
                <div className="alert alert-info">
                    Username: aperezgua@uoc.edu<br />
                    Password: pw
                </div>
                <div className="alert alert-info">
                    Username: sam@uoc.edu<br />
                    Password: pw
                </div>
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
                                    if(authenticationService.currentUserValue.rol == 'ADMINISTRATOR') {
                                        this.props.navigate('/admin/home');
                                    } else if(authenticationService.currentUserValue.rol == 'SUPPORT') {
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
                    }}
                    render={({ errors, status, touched, isSubmitting }) => (
                        <Form>
                            <div className="form-group">
                                <label htmlFor="username">Email</label>
                                <Field name="username" type="text" className={'form-control' + (errors.username && touched.username ? ' is-invalid' : '')} />
                                <ErrorMessage name="username" component="div" className="alert alert-error" />
                            </div>
                            <div className="form-group">
                                <label htmlFor="password">Password</label>
                                <Field name="password" type="password" className={'form-control' + (errors.password && touched.password ? ' is-invalid' : '')} />
                                <ErrorMessage name="password" component="div" className="alert alert-error" />
                            </div>
                            <div className="form-group">
                                <button type="submit" className="btn btn-lg btn-primary btn-block" disabled={isSubmitting}>Login</button>
                            </div>
                            <br/>
                            {status &&                                
                                <div className={'alert alert-danger'}>{status}</div>
                            }
                        </Form>
                    )}
                />
            </div>
        )
    }
}

function LoginPage(props) {
    let navigate = useNavigate();
    return <LoginPageNoNavigate {...props} navigate={navigate} />
}
export {LoginPage}

