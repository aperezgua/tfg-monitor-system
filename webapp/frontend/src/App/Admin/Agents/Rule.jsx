import React from 'react';
import { Button, Modal } from 'react-bootstrap';
import { FormControl, Form } from 'react-bootstrap';
import { Formik, Form as FormFormik,  Field, ErrorMessage} from 'formik';
import * as Yup from 'yup';

class Rule extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            rule: props.value,
            buttonValue: props.buttonValue,
            show : false
        };
        
        this.handleClose = this.handleClose.bind(this);
        this.handleShow = this.handleShow.bind(this);
        this.handleFormValueChage = this.handleFormValueChage.bind(this);
    }

    componentWillReceiveProps(nextProps) {
        this.setState({ rule: nextProps.value })
    }

    handleClose() {     
        this.setState({show : false});   
    }
    
    handleShow() {
       this.setState({show : true});
    }
    
    handleFormValueChage() {
        console.log("alal");
    }

    render() {
        const { rule, buttonValue, show } = this.state;
        return (
            <div>
                <Button onClick={this.handleShow}>{buttonValue}</Button>
                <Modal  show={show} onHide={this.handleClose} animation={false} size="lg" aria-labelledby="contained-modal-title-vcenter" centered>
                  <Modal.Header closeButton>
                    <Modal.Title id="contained-modal-title-vcenter">Edición de regla</Modal.Title>
                  </Modal.Header>
                 
                    <Formik key="editRuleFormik"
                            enableReinitialize
                            initialValues={rule}
                            validationSchema={Yup.object().shape({
                                name: Yup.string().required('El nombre es obligatorio.')
                            })}
                            onSubmit={( values, { setStatus, setSubmitting }) => {
                                console.log("GUARDAR!!");
                                this.handleClose();
                            }}>
                            {({errors, status, touched}) => (
                                <div>
                                <Modal.Body>
                                    <FormFormik key="editRuleForm">
                                        <Form.Group>
                                           <Form.Label>Nombre:</Form.Label>
                                           <Field name="name" type="text" className={'form-control' + (errors.name && touched.name ? ' is-invalid' : '')} />
                                           <ErrorMessage name="name" component="div" className="alert alert-error" />
                                        </Form.Group> 
                                    </FormFormik>
                                 </Modal.Body>
                                  <Modal.Footer>
                                    <Button variant="secondary" onClick={this.handleClose}>Cerrar</Button>
                                    <Button variant="primary" type="submit">Guardar</Button>
                                  </Modal.Footer>
                              </div>
                            )}
                    </Formik>
                </Modal>
            </div>
        );
    }
}

export { Rule }