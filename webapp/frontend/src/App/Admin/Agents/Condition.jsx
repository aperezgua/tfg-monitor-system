import React from 'react';
import { Button, Modal, Form, Row, Col } from 'react-bootstrap';
import { Formik, Form as FormFormik,  Field, ErrorMessage} from 'formik';
import * as Yup from 'yup';

class Condition extends React.Component {
    /** Constructor donde se establecen los datos principales */
    constructor(props) {
        super(props);
        this.state = {
            indexValue : props.indexValue,
            condition : props.value,
            buttonValue : props.buttonValue,
            show : false
        };
        this.handleClose = this.handleClose.bind(this);
        this.handleShow = this.handleShow.bind(this);
        this.handleFormValueChage = this.handleFormValueChage.bind(this);
    }

    handleClose() {     
        this.setState({show : false});   
    }
    
    handleShow() {
       this.setState({show : true});
    }
    
    handleFormValueChage(values) {
        this.setState({condition : values});
        //console.log("alal" +this.state.indexValue +" - " +JSON.stringify(this.state.rule));
        this.props.onChangeConditionHandler(this.state.indexValue, this.state.condition);
    }

    render() {
        const { condition, buttonValue, show } = this.state;
        return (
            <div>
                <Button onClick={this.handleShow}>{buttonValue}</Button>
                <Modal backdrop="static" show={show} onHide={this.handleClose} animation={false}  centered>
                  <Modal.Header closeButton>
                    <Modal.Title id="contained-modal-title-vcenter">Edición de condición</Modal.Title>
                  </Modal.Header>
                 
                    <Formik key="editRuleFormik"
                            enableReinitialize
                            initialValues={condition}
                            validationSchema={Yup.object().shape({
                                value: Yup.string().required('El nombre es obligatorio.'),
                                comparationType: Yup.string().required('La expresión regular es obligatoria.')
                            })}
                            onSubmit={( values ) => {
                                this.handleFormValueChage(values);
                                this.handleClose();
                            }}>
                            {({errors, touched, submitForm}) => (
                                <div>
                                <Modal.Body>
                                    <FormFormik key="editConditionForm">
                                        <Row>
                                            <Col>
                                                <Form.Group>
                                                    <Form.Label>Comparación:</Form.Label>
                                                    <Field name="comparationType" as="select" className={'form-select' + (errors.comparationType && touched.comparationType ? ' is-invalid' : '')} >
                                                       <option value=""></option>
                                                       <option value="CONTAINS">Contiene</option>
                                                       <option value="GREATER_THAN">Mayor que</option>
                                                       <option value="LESS_THAN">Menor que</option>
                                                       <option value="AVG_GREATER_THAN">Media mayor que</option>
                                                       <option value="AVG_LESS_THAN">Media menor que</option>
                                                     </Field>
                                                     <ErrorMessage name="active" component="div" className="alert alert-error" />
                                                </Form.Group>
                                            </Col>
                                             <Col>
                                                <Form.Group>
                                                   <Form.Label>Valor:</Form.Label>
                                                   <Field name="value" type="text" className={'form-control' + (errors.value && touched.value ? ' is-invalid' : '')} />
                                                   <ErrorMessage name="value" component="div" className="alert alert-error" />
                                                </Form.Group>
                                             </Col>
                                              <Col>
                                                <Form.Group>
                                                   <Form.Label>En tiempo:</Form.Label>
                                                   <Field name="time" type="text" className={'form-control' + (errors.time && touched.time ? ' is-invalid' : '')} />
                                                   <ErrorMessage name="time" component="div" className="alert alert-error" />
                                                </Form.Group>
                                             </Col>
                                         </Row>
                                    </FormFormik>
                                 </Modal.Body>
                                  <Modal.Footer>
                                    <Button variant="secondary" onClick={this.handleClose}>Cerrar</Button>
                                    <Button variant="primary" onClick={submitForm}>Guardar</Button>
                                  </Modal.Footer>
                              </div>
                            )}
                    </Formik>
                </Modal>
            </div>
        );
    }
}

export { Condition }