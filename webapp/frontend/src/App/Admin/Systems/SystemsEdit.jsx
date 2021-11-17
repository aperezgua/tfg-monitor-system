import React from 'react';
import { systemsService } from '_services';
import { Formik, Form as FormFormik,  Field, ErrorMessage} from 'formik';
import { Button, Form, Alert } from 'react-bootstrap';
import { useParams } from "react-router-dom";
import * as Yup from 'yup';

class SystemsEditNoParams extends React.Component {
   constructor(props) {
        super(props);
        this.state = {
            system : null,
            error : null,
            countriesList : null
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
        
        if (this.props.params.id && this.props.params.id > 0) {
            systemsService.get(this.props.params.id).then(
                system => {
                    this.setState( { system } );
                },
                error => {
                    this.setState({error});
                }
            );
        } else {
            this.setState( { 
                system : {
                    name : '',
                    country : '',
                    active : true
                } 
             });
        }
    }
    render() {
        const { error, system, countriesList } = this.state;
        return (
             <div className="form-edit">
                <h2>Edición de sistema</h2>
                {!error &&
                <Formik key="editUserFormik"
					enableReinitialize
                    initialValues={system}
                     validationSchema={Yup.object().shape({
                        name: Yup.string().required('El nombre es obligatorio.')
                    })}
                    onSubmit={( values, { setStatus, setSubmitting }) => {
                        setStatus();
                        systemsService.put(values)
                            .then(
                                updatedSystem => {    
                                    setStatus( {result : 'updated' });
                                    setSubmitting(false);
                                },
                                error => {
                                    setSubmitting(false);
                                    setStatus({error : error});
                                }
                            );
                    }}>
                    {({errors, status, touched}) => (
                        <FormFormik key="editUserForm">
                            <Form.Group>
                                <Form.Label >Nombre:</Form.Label>
                                <Field name="name" type="text" className={'form-control' + (errors.name && touched.name ? ' is-invalid' : '')} />
                                <ErrorMessage name="name" component="div" className="alert alert-error" />
                            </Form.Group>
                            <Form.Group>
                                <Form.Label>País</Form.Label>
                                <Field name="country.id" as="select"  className="form-select" >
                                   <option value=""></option>
                                   {countriesList && countriesList.map(country => <option value={country.id}>{country.name}</option>)}
                                 </Field>
                            </Form.Group>
                            <Form.Group>
                                <Form.Label>Activo:</Form.Label>
                                <Field name="active" as="select"  className="form-select" >
                                   <option value=""></option>
                                   <option value="true">Activo</option>
                                   <option value="false">Inactivo</option>
                                 </Field>
                            </Form.Group>
                            {status && status.error && <Alert key="alertError1" variant="danger" >{status.error}</Alert> }
                            {status && status.result && <Alert key="alertOk" variant="success" >{status.result}</Alert> }
                            <Form.Group>
                                <Button variant="primary" type="submit">Guardar</Button>
                            </Form.Group>
                        </FormFormik>
                    )}
                 </Formik>
                 }
                 {error && <Alert key="alertError2" variant="danger" >{error}</Alert>}
             </div>
        );
    }
}



function SystemsEdit(props) {
    let params = useParams();
    return <SystemsEditNoParams {...props} params={params} />
}
export {SystemsEdit}