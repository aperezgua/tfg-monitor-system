import React from 'react';
import { Button, Modal } from 'react-bootstrap';
import { FormControl, Form } from 'react-bootstrap';


class Rule extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            rule: props.value,
            buttonValue: props.buttonValue,
            show : false
        };
        
        this.handleSave = this.handleSave.bind(this);
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
    
    handleSave() {
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
                  <Modal.Body>
                    <Form.Group>
                       <Form.Label>Nombre:</Form.Label>
                       <FormControl name="name" defaultValue={rule.name} readOnly={false} onChange={this.handleFormValueChage}/>
                    </Form.Group> 
                  </Modal.Body>
                  <Modal.Footer>
                    <Button variant="secondary" onClick={this.handleClose}>Cerrar</Button>
                    <Button variant="primary" onClick={this.handleSave}>Guardar</Button>
                  </Modal.Footer>
                </Modal>
            </div>
        );
    }
}

export { Rule }