import React from 'react';
import { userService } from '_services';
import { Formik, Form as FormFormik,  Field} from 'formik';
import { Button, Form } from 'react-bootstrap';
import { useParams } from "react-router-dom";

class UserEditNoParams extends React.Component {
   constructor(props) {
        super(props);
        this.state = {
            user : null,
            error : null
        };
    }
    componentDidMount() {
        if (this.props.params.id) {
            userService.get(this.props.params.id).then(
                user => {
                    this.setState( {user} )
                },
                error => {
                    this.setState({error})
                }
            );
        }
    }
    render() {
        const { error } = this.state;
        return (
             <div className="form-edit">
                <h2>Edición de usuario</h2>
                {!error &&
                <Formik
                    enableReinitialize
                    initialValues={this.state.user}
                    onSubmit={({ name, email, values}, { setStatus, setSubmitting }) => {
                        setStatus();
                        console.log(JSON.stringify(values));
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
                 }
                 {error && 
                        <div className={'alert alert-danger'}>{error}</div>
                 }
             </div>
        );
    }
}



function UserEdit(props) {
    let params = useParams();
    return <UserEditNoParams {...props} params={params} />
}
export {UserEdit}