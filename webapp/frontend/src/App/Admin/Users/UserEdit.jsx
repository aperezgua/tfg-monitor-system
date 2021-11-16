import React from 'react';
import { userService } from '_services';
import { Formik, Form as FormFormik,  Field} from 'formik';
import { Button, Form } from 'react-bootstrap';
import { useParams } from "react-router-dom";

class UserEditNoParams extends React.Component {
   constructor(props) {
        super(props);
        this.state = {
            user : null
        };
    }
    componentDidMount() {
        userService.get(this.props.params.id).then(
            user => {
                console.log("LALALA: " + JSON.stringify({user}));
                this.setState( {user} )
            },
            error => {
                console.log("Error: " +(typeof error) + " " + error);
            }
        );
    }
    render() {
        
        return (
             <div className="form-search">
                <h2>Edición de usuario</h2>
                <Formik
                    enableReinitialize
                    initialValues={this.state.user}
                    onSubmit={({ name, email}, { setStatus, setSubmitting }) => {
                        setStatus();
                        userService.put({name, email})
                            .then(
                                updatedUser => {
                                    console.log("LALALA: " + JSON.stringify(updatedUser));    
                                    setStatus( {result : 'updated' });
                                    setSubmitting(false);
                                },
                                error => {
                                    setSubmitting(false);
                                    if(typeof(error) == "object") {
                                        setStatus({error : 'Cannot connect to server.'});
                                    } else {
                                        setStatus({error : error});
                                    }
                                }
                            );
                    }}>
                    {({ status}) => (
                        <FormFormik>
                            <Form.Group>
                                <Form.Label >Nombre:</Form.Label>
                                <Field name="name" type="text" className="form-control" />
                            </Form.Group>
                            <Form.Group>
                                <Form.Label>Email</Form.Label>
                                <Field name="email" type="text" className="form-control" />
                            </Form.Group>
                            {status && status.error &&
                                <div className={'alert alert-danger'}>{status.error}</div>
                            }
                            {status && status.result &&
                                <div className={'alert'}>{status.result}</div>
                            }
                            <Form.Group>
                                <Button variant="primary" type="submit">Guardar</Button>
                            </Form.Group>
                        </FormFormik>
                    )}
                 </Formik>
             </div>
        );
    }
}



function UserEdit(props) {
    let params = useParams();
    return <UserEditNoParams {...props} params={params} />
}
export {UserEdit}