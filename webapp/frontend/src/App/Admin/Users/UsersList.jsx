import React from 'react';
import { userService } from '_services';
import { Link } from "react-router-dom";
import { Formik, Form as FormFormik,  Field} from 'formik';
import { Button, Form, Row, Col, Alert } from 'react-bootstrap';


/** Componente para listar usuarios. */
class UsersList extends React.Component {
    
    /** Constructor en donde se establecen las propiedades del componente. */
    constructor(props) {
        super(props);
        this.state = {
            usersList : null,
            error : null
        };
        
        this.findUsers = this.findUsers.bind(this);
    }
    
    /** Método encargado de llamar al servicio y setear la lista. */
    findUsers(name, email, activeTypeFilter) {
        userService.find({name, email, activeTypeFilter})
                .then(
                    usersList => {
                        this.setState( {usersList : usersList });
                    },
                    error => {
                        this.setState({error : error});
                    }
                );
    }
    
    /** Cuando se monta el componente se cargan los usuarios. */
    componentDidMount() {
        this.findUsers('', '', 'ALL');
    }
   
    render() {
        let {usersList, error} = this.state;
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
                       this.findUsers(name, email, activeTypeFilter);
                    }}>
                    {() => (
                        <div>
                            <FormFormik >
                                <Row>
                                    <Col lg="2">
                                        <Form.Label htmlFor="activeTypeFilterACTIVE">Estado</Form.Label>
                                        <Form.Group>
                                            <Field name="activeTypeFilter" id="activeTypeFilterACTIVE" type="radio" className="form-check-input" value="ACTIVE" />
                                            <Form.Label htmlFor="activeTypeFilterACTIVE">Activos</Form.Label>
                                        </Form.Group>
                                        <Form.Group>
                                            <Field name="activeTypeFilter" id="activeTypeFilterINACTIVE" type="radio" className="form-check-input" value="INACTIVE" />
                                            <Form.Label htmlFor="activeTypeFilterINACTIVE">Inactivos</Form.Label>
                                         </Form.Group>
                                         <Form.Group>
                                            <Field name="activeTypeFilter" id="activeTypeFilterALL" type="radio" className="form-check-input" value="ALL" />
                                            <Form.Label htmlFor="activeTypeFilterALL">Todos</Form.Label>
                                        </Form.Group>
                                    </Col>
                                    <Col lg="5">
                                        <Form.Group>
                                            <Form.Label >Nombre:</Form.Label>
                                            <Field name="name" type="text" className="form-control" />
                                        </Form.Group>
                                     </Col>
                                     <Col lg="5">
                                        <Form.Group>
                                            <Form.Label>Email</Form.Label>
                                            <Field name="email" type="text" className="form-control" />
                                        </Form.Group>
                                     </Col>
                                </Row>
                                <Row>
                                    <Col lg="12" className="text-center">
                                         <Form.Group>
                                            <Button variant="primary" type="submit">Buscar</Button> &nbsp;
                                            <Link to="/admin/users/edit/0" className="btn btn-secondary" >Nuevo</Link>
                                        </Form.Group>    
                                    </Col>
                                </Row>
                            </FormFormik>
                        </div>
                    )}
                 </Formik>
                  {usersList && 
                   <table className="table table-striped table-hover">
                      <thead>
                        <tr>
                          <th scope="col">#</th>
                          <th scope="col">Nombre</th>
                          <th scope="col">Email</th>
                          <th scope="col">Rol</th>
                          <th scope="col">Activo</th>
                          <th scope="col"></th>
                        </tr>
                      </thead>
                      <tbody>
                        {usersList.map(user =>
                            <tr key={'tr' +user.id}>
                              <th scope="row">{user.id}</th>
                              <td>{user.name}</td>
                              <td>{user.email}</td>
                              <td>{user.rol}</td>
                              <td>{user.active? 'Si' : 'No'}</td>
                              <td><Link to={'/admin/users/edit/' +user.id} >Ver</Link></td>
                            </tr>
                        )}
                      </tbody>
                   </table>
                }
                {error && <Alert key="alertError" variant="danger" >{error}</Alert> }
            </div>
        );
    }
}

export {UsersList}
