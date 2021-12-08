import React from 'react';
import { systemsService } from '_services';
import { Link } from "react-router-dom";
import { Formik, Form as FormFormik,  Field} from 'formik';
import { Button, Form, Row, Col, Alert } from 'react-bootstrap';

/**Componente que gestiona el listado de sistemas */
class SystemsList extends React.Component {
     constructor(props) {
        super(props);
        this.state = {
            countriesList : null,
            error : null, 
            systemsList : null
        };
        
        this.findSystems = this.findSystems.bind(this);
     }
     
    /** Busca sistemas a partir del servicio */
    findSystems(name, countryId, activeTypeFilter) {
         systemsService.find({name, countryId, activeTypeFilter})
                .then(
                    systemsList => {
                        this.setState( {systemsList : systemsList });
                    },
                    error => {
                        this.setState({error : error});
                    }
                );
    }
     
     /** cuando se inicia el componente se carngan los países y los sistemas creados */
    componentDidMount() {
         systemsService.getCountries().then(
            countriesList => {
                this.setState( { countriesList } );
            },
            error => {
                this.setState({error});
            }
        );
        this.findSystems('', '', 'ALL');
    }
   
    render() {
        const {systemsList, error, countriesList} = this.state;        
        
        return (
             <div className="form-search">
                <h2>Filtro de sistemas</h2>
                <Formik key="searchUsers"
                    initialValues={{
                        name: '',
                        countryId: '',
                        activeTypeFilter: 'ALL'
                    }}
                    onSubmit={({ name, countryId, activeTypeFilter}) => {
                        this.findSystems(name, countryId, activeTypeFilter);
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
                                            <Form.Label>País</Form.Label>
                                            <Field name="countryId" as="select"  className="form-select" >
                                               <option value=""></option>
                                               {countriesList && countriesList.map(country => <option key={country.id} value={country.id}>{country.name}</option>)}
                                             </Field>
                                        </Form.Group>                                     
                                     </Col>
                                </Row>
                                <Row>
                                    <Col lg="12" className="text-center">
                                         <Form.Group>
                                            <Button variant="primary" type="submit">Buscar</Button>  &nbsp;
                                            <Link to="/admin/systems/edit/0" className="btn btn-secondary" >Nuevo</Link>
                                        </Form.Group>
                                     </Col>
                                </Row>
                            </FormFormik>
                        </div>
                    )}
                 </Formik>
                 {systemsList &&
                   <table className="table table-striped table-hover">
                      <thead>
                        <tr>
                          <th scope="col">#</th>
                          <th scope="col">Nombre</th>
                          <th scope="col">País</th>
                          <th scope="col">Activo</th>
                          <th scope="col"></th>                                      
                        </tr>
                      </thead>
                      <tbody>
                        {systemsList.map(system =>
                            <tr key={'tr' +system.id}>
                              <th scope="row">{system.id}</th>
                              <td>{system.name}</td>
                              <td>{system.country.name}</td>
                              <td>{system.active? 'Si' : 'No'}</td>
                              <td><Link to={'/admin/systems/edit/' +system.id} >Ver</Link></td>
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

export {SystemsList}
