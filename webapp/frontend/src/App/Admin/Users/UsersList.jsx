import React from 'react';
import { userService } from '_services';
import { Link } from "react-router-dom";
import { Formik, Form as FormFormik,  Field} from 'formik';
import { Button, Form } from 'react-bootstrap';

class UsersList extends React.Component {
    
    
   
    render() {
        return (
             <div className="form-search">
                <h2>Filtro de usuarios</h2>
                <Formik key="searchUsers"
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
                                    setStatus( {result : usersList });
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
                                    <Button href="/admin/users/edit/0" variant="secondary" >Nuevo</Button>
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
                                      <th scope="col">Email</th>
                                      <th scope="col">Activo</th>
                                      <th scope="col"></th>
                                    </tr>
                                  </thead>
                                  <tbody>
                                    {status.result.map(user =>
                                        <tr key={'tr' +user.id}>
                                          <th scope="row">{user.id}</th>
                                          <td>{user.name}</td>
                                          <td>{user.email}</td>
                                          <td>{user.active? 'Si' : 'No'}</td>
                                          <td><Link to={'/admin/users/edit/' +user.id} >Ver</Link></td>
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

export {UsersList}
