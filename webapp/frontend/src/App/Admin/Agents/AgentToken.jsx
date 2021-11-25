import React from 'react';
import { agentService } from '_services';
import { Button, Form, InputGroup, FormControl } from 'react-bootstrap';

class AgentToken extends React.Component {
    
     constructor(props) {
        super(props);
        
        this.state = {
            fieldName : props.name,
            fieldValue: props.value,
            fieldOriginalValue : props.value
        };
        
        this.generateToken = this.generateToken.bind(this);
        
    }
    
    componentWillReceiveProps(nextProps) {
        this.setState({
            fieldValue: nextProps.value,
            fieldOriginalValue : nextProps.value
        });
    }
    
    generateToken() {
        const {fieldName, fieldValue, fieldOriginalValue} = this.state;
        
        agentService.generateToken().then(
            token => {
                console.log("lala: " +fieldName + " = " +token.token);
                //this.setState({fieldValue : token.token });
                this.props.setFieldValue(fieldName, token.token);
                
            },
            error => {
              console.log("error" + error);  
            }
        );
        
        return false;
    }
    
    onChangeFormControl() {
        
    }
    
    render() {
        const {fieldName, fieldValue, fieldOriginalValue} = this.state;
        return (            
            <Form.Group>
                <Form.Label >Token:</Form.Label> 
                <InputGroup className="mb-3">
                    <FormControl name={fieldName} value={fieldValue} onChange={this.onChangeFormControl}  /> 
                    <Button variant="outline-secondary" id="button-addon2" disabled={fieldOriginalValue} onClick={this.generateToken}>Generar</Button>
                </InputGroup>
             </Form.Group>
        );
    }
    
}


export {AgentToken}