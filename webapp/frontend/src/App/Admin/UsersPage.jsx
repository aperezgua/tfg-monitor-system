import React from 'react';
import { userService } from '_services';
import { Formik, Form as FormFormik } from 'formik';
import { Button, Form, Row } from 'react-bootstrap';
class UsersPage extends React.Component {
    
    render() {
        return (
             <div className="form-search">
                <h2>Filtro de usuarios</h2>
                <Formik
                    initialValues={{
                        name: '',
                        email: '',
                        activeTypeFilter: 'ALL'
                    }}
                    onSubmit={({ name, email, activeTypeFilter}, { setStatus }) => {
                        setStatus();
                        
                        console.log("BLABAL + " +name);
                        
                        userService.find({name, email, activeTypeFilter})
                            .then(
                                users => this.setState({ users }),
                                error => {
                                    if(typeof(error) == "object") {
                                        setStatus(['Cannot connect to server.']);
                                    } else {
                                        setStatus(error);
                                    }
                                }
                            );
                    }}>
                    {({values, status, errors,
                          touched,
                          handleChange,
                          handleBlur,
                          handleSubmit,
                          isSubmitting }) => (
                        <Form onSubmit={handleSubmit}>
                            <Form.Group>
                                <Form.Label >Nombre:</Form.Label>
                                <Form.Control  name="name" defaultValue={values.name} type="text"/> 
                            </Form.Group>
                            <Form.Group>
                                <Form.Label>Email</Form.Label>
                                <Form.Control name="email" type="text"/>
                            </Form.Group>
                            <Form.Group>
                                <Form.Check type="radio" name="activeTypeFilter" defaultChecked={'ACTIVE' === values.activeTypeFilter} id="activeTypeFilterACTIVE" label="Activos"/>
                                <Form.Check type="radio" name="activeTypeFilter" defaultChecked={'INACTIVE' === values.activeTypeFilter} id="activeTypeFilterINACTIVE" label="Inactivos"/>
                                <Form.Check type="radio" name="activeTypeFilter" defaultChecked={'ALL' === values.activeTypeFilter} id="activeTypeFilterALL" label="Todos"/>
                            </Form.Group>
                            <Form.Group>
                                <Button variant="primary" type="submit">Buscar</Button>
                            </Form.Group>
                            {status &&                                
                                <div className={'alert alert-danger'}>{status}</div>
                            }
                        </Form>
                    )}
                </Formik>
            </div>
        );
    }
}

export {UsersPage}
