import React from 'react';
import { systemsService } from '_services';
import { Link } from "react-router-dom";
import { Formik, Form as FormFormik,  Field} from 'formik';
import { Button, Form } from 'react-bootstrap';
import { handleMapData } from '_helpers';
class SystemsList extends React.Component {
     constructor(props) {
        super(props);
        this.state = {
            countriesList : null,
            error : null
        };
     }
     componentDidMount() {
         systemsService.getCountries().then(
            countriesList => {
                this.setState( { countriesList } );
            },
            error => {
                this.setState({error});
            }
        );
    }
   
    render() {
        const {countriesList} = this.state;        
        
        return (
             <div className="form-search">
                <h2>Filtro de sistemas</h2>
                <Formik key="searchUsers"
                    initialValues={{
                        name: '',
                        countryId: '',
                        activeTypeFilter: 'ALL'
                    }}
                    onSubmit={({ name, countryId, activeTypeFilter}, { setStatus, setSubmitting }) => {
                        setStatus();
                        systemsService.find({name, countryId, activeTypeFilter})
                            .then(
                                systemsList => {
                                    setStatus( {result : systemsList });
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
                                    <Form.Label>País</Form.Label>
                                    <Field name="countryId" as="select"  className="form-select" >
                                       <option value=""></option>
                                       {countriesList && countriesList.map(country => <option value={country.id}>{country.name}</option>)}
                                     </Field>
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
                                    <Button href="/admin/systems/edit/0" variant="secondary" >Nuevo</Button>
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
                                      <th scope="col">País</th>
                                      <th scope="col">Activo</th>
                                    </tr>
                                  </thead>
                                  <tbody>
                                    {status.result.map(system =>
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
                        </div>
                    )}
                 </Formik>
            </div>
        );
    }
}

export {SystemsList}
