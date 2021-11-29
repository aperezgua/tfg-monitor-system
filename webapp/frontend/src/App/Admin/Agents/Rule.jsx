import React from 'react';
import { Button, Modal, Form, Row, Col } from 'react-bootstrap';
import { Formik, Form as FormFormik,  Field, ErrorMessage} from 'formik';
import { RuleConditions, RuleRegexp } from 'App/Admin/Agents';
import * as Yup from 'yup';

class Rule extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            indexValue : props.indexValue,
            rule : props.value,
            buttonValue : props.buttonValue,
            show : false,
            agentTokenId : props.agentTokenId
        };
        this.handleClose = this.handleClose.bind(this);
        this.handleShow = this.handleShow.bind(this);
        this.handleFormValueChage = this.handleFormValueChage.bind(this);
    }

    /** Si las propiedades del parent cambian las notifico */
    componentWillReceiveProps(nextProps) {
        this.setState({
            agentTokenId: nextProps.agentTokenId
        });
    }

    handleClose() {     
        this.setState({show : false});   
    }
    
    handleShow() {
       this.setState({show : true});
    }
    
    handleFormValueChage(values) {
        this.setState({rule : values});
        //console.log("alal" +this.state.indexValue +" - " +JSON.stringify(this.state.rule));
        this.props.onChangeRuleHandler(this.state.indexValue, this.state.rule);
    }

    render() {
        const { rule, agentTokenId, buttonValue, show } = this.state;
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
                                name: Yup.string().required('El nombre es obligatorio.'),
                                regularExpression: Yup.string().required('La expresión regular es obligatoria.'),
                                active: Yup.string().required('Es necesario especificar un valor.'),
                                calculationType: Yup.string().required('Es necesario especificar un valor.'),
                                matchType: Yup.string().required('Es necesario especificar un valor.'),
                                severity: Yup.string().required('Es necesario especificar un valor.')
                            })}
                            onSubmit={( values) => {                                
                                this.handleFormValueChage(values);
                                this.handleClose();
                            }}>
                            {({values, errors, touched, submitForm, setFieldValue}) => (
                                <div>
                                <Modal.Body>
                                    <FormFormik key="editRuleForm">
                                        <Row>
                                            <Col>
                                                <Form.Group>
                                                   <Form.Label>Nombre:</Form.Label>
                                                   <Field name="name" type="text" className={'form-control' + (errors.name && touched.name ? ' is-invalid' : '')} />
                                                   <ErrorMessage name="name" component="div" className="alert alert-error" />
                                                </Form.Group>
                                             </Col>
                                            <Col>
                                                <Form.Group>
                                                    <Form.Label>Activo:</Form.Label>
                                                    <Field name="active" as="select" className={'form-select' + (errors.active && touched.active ? ' is-invalid' : '')} >
                                                       <option value=""></option>
                                                       <option value="true">Activo</option>
                                                       <option value="false">Inactivo</option>
                                                     </Field>
                                                     <ErrorMessage name="active" component="div" className="alert alert-error" />
                                                </Form.Group>
                                            </Col>
                                         </Row>
                                         <Form.Group>
                                           <RuleRegexp name="regularExpression" agentTokenId={agentTokenId} value={values && values.regularExpression} setFieldValue={setFieldValue}/>                                        
                                           <ErrorMessage name="regularExpression" component="div" className="alert alert-error" />
                                        </Form.Group> 
                                        <Row>
                                            <Col>
                                                <Form.Group>
                                                    <Form.Label>Tipo de cálculo:</Form.Label>
                                                    <Field name="calculationType" as="select"  className={'form-select' + (errors.calculationType && touched.calculationType ? ' is-invalid' : '')}>
                                                       <option value=""></option>
                                                       <option value="COUNT_EVENT">Contar</option>
                                                       <option value="DIRECT_VALUE">Valor directo</option>
                                                     </Field>
                                                     <ErrorMessage name="calculationType" component="div" className="alert alert-error" />
                                                </Form.Group>
                                            </Col>
                                            <Col>
                                                <Form.Group>
                                                    <Form.Label>Tipo de coincidencia:</Form.Label>
                                                    <Field name="matchType" as="select"  className={'form-select' + (errors.matchType && touched.matchType ? ' is-invalid' : '')}>
                                                       <option value=""></option>
                                                       <option value="ALL">Todas</option>
                                                       <option value="ANY">Alguna</option>
                                                     </Field>
                                                     <ErrorMessage name="matchType" component="div" className="alert alert-error" />
                                                </Form.Group>
                                            </Col>
                                        </Row>
                                        <Form.Group>
                                            <Form.Label>Nivel:</Form.Label>
                                            <Field name="severity" as="select"  className={'form-select' + (errors.severity && touched.severity ? ' is-invalid' : '')}>
                                               <option value=""></option>
                                               <option value="MINOR">Baja</option>
                                               <option value="MAJOR">Alta</option>
                                               <option value="CRITICAL">Crítica</option>
                                             </Field>
                                             <ErrorMessage name="severity" component="div" className="alert alert-error" />
                                        </Form.Group>
                                        <RuleConditions value={values && values.conditions} name="conditions" setFieldValue={setFieldValue} />
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

export { Rule }