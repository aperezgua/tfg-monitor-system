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
      this.state = {
            fieldName : nextProps.name,
            fieldValue: nextProps.value,
            fieldOriginalValue : nextProps.value
        };
    }
    
    generateToken() {
        console.log("lala");
        
        agentService.generateToken().then(
            token => {
                this.setState({fieldValue : token.token});
                this.props.onChangeTokenHandler(token.token);
            },
            error => {
              console.log("error" + error);  
            }
        );
        
        return false;
    }
    
    render() {
        const {fieldName, fieldValue, fieldOriginalValue} = this.state;
        return (            
            <Form.Group>
                <Form.Label >Token:</Form.Label> 
                <InputGroup className="mb-3">
                    <FormControl name={fieldName} value={fieldValue}  /> 
                    <Button variant="outline-secondary" id="button-addon2" disabled={fieldOriginalValue} onClick={this.generateToken}>Generar</Button>
                </InputGroup>
             </Form.Group>
        );
    }
    
}


export {AgentToken}