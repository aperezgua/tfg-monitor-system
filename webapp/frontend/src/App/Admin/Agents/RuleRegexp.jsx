import React from 'react';
import { logService } from '_services';
import {  Form, InputGroup, FormControl } from 'react-bootstrap';
import './RuleRegexp.css';

class RuleRegexp extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            agentTokenId: props.agentTokenId,
            fieldName: props.name,
            regexp: props.value,
            logList : null
        };
        this.changeRegepx = this.changeRegepx.bind(this);
    }

    /** Cuando se recibe async del padre, se actualiza el valor */
    componentWillReceiveProps(nextProps) {
        this.setState({ 
            agentTokenId: nextProps.agentTokenId,
            regexp: nextProps.value 
        }
        );
    }
    
    changeRegepx(event) {
        let fieldName = event.target.name;
        let fieldValue = event.target.value;
        
        //console.log("change: " +fieldName + " -> " + fleldVal);
        this.props.setFieldValue(fieldName, fieldValue);
        if(fieldValue) {
            logService.findByRegexp(this.state.agentTokenId, fieldValue).then(
                logs => {
                     this.setState({logList : logs});
                },
                error => {
                  console.log("error" + error);  
                }
            );
        } else {
            this.setState({logList : null});
        }
       
        
        return false;
    }

    render() {
        const { fieldName, regexp, logList } = this.state;
        return (
             <Form.Group>
                <Form.Label >Regexp:</Form.Label> 
                <InputGroup className="mb-3">
                    <FormControl name={fieldName} defaultValue={regexp} onChange={this.changeRegepx}  />
                </InputGroup>
                {logList && logList.length > 0 &&
                    <div className="console">
                        {logList.map((log, index) =>
                            <div>{log.date} - {log.logLine}</div>
                        )}
                    </div>
                }
             </Form.Group>
        );
    }
}

export { RuleRegexp }