import React from 'react';
import { userService } from '_services';
import { Formik, Form as FormFormik,  Field} from 'formik';
import { Button, Form } from 'react-bootstrap';

class UsersPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            usersList : null, 
            usersStr : null
        };
    }
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
                    onSubmit={({ name, email, activeTypeFilter}, { setStatus, setSubmitting }) => {
                        setStatus();
                        userService.find({name, email, activeTypeFilter})
                            .then(
                                usersList => {
                                    console.log("LALALA: " + JSON.stringify(usersList));    
                                    this.setState({ usersList })
                                    this.setState({usersStr : "asasa" })
                                    setSubmitting(false);
                                },
                                error => {
                                    setSubmitting(false);
                                    if(typeof(error) == "object") {
                                        setStatus(['Cannot connect to server.']);
                                    } else {
                                        setStatus(error);
                                    }
                                }
                            );
                    }}
                    render={({usersList, usersStr, status}) => (
                        <FormFormik>
                            <Form.Group>
                                <Form.Label >Nombre:</Form.Label>
                                <Field name="name" type="text" className="form-control" />
                            </Form.Group>
                            <Form.Group>
                                <Form.Label>Email</Form.Label>
                                <Field name="email" type="text" className="form-control" />
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
                            </Form.Group>
                             {usersStr && <div>dd</div>}
                             {usersList &&
                                <ul>
                                    {usersList.map(user =>
                                        <li key={user.id}>{user.name} {user.email}</li>
                                    )}
                                </ul>
                             }
                            {status &&
                                <div className={'alert alert-danger'}>{status}</div>
                            }
                        </FormFormik>
                    )}/>
            </div>
        );
    }
}

export {UsersPage}
