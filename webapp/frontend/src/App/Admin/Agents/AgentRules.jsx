import React from 'react';
import { Button } from 'react-bootstrap';
import { Rule } from 'App/Admin/Agents';

class AgentRules extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            name: props.name,
            rules: props.value, 
            agentTokenId : props.agentTokenId
        };
        this.removeRule = this.removeRule.bind(this);
        this.addRule = this.addRule.bind(this);
        this.onChangeRuleHandler = this.onChangeRuleHandler.bind(this);
    }

    /** Se actualizan las propiedades recibidas */
    componentWillReceiveProps(nextProps) {
        this.setState({ 
            rules: nextProps.value ,
            agentTokenId : nextProps.agentTokenId
        });
    }
    
    
    onChangeRuleHandler(index, rule) {        
       let {rules} = this.state;
       rules[index] = rule;
       
       // Se notifica y volvemos a recibir a través de componentWillReceiveProps
       this.props.setFieldValue(this.state.name, rules);
    }

    removeRule(index) {
        let rules = this.state.rules;
       
        rules.splice(index, 1);
        
        console.log("remove: " +this.state.name + " " +index + " - " + JSON.stringify(rules));
       
        // Se notifica y volvemos a recibir a través de componentWillReceiveProps
        //
        this.props.setFieldValue(this.state.name, rules);
        
        
        return false;
    }
    
    addRule() {
         let rules = this.state.rules;
         rules.push({
                active : true, 
                name : '', 
                calculationType : 'DIRECT_VALUE', 
                severity : 'MINOR', 
                matchType : 'ALL', 
                regularExpression : '',
                conditions : []
                });
         this.props.setFieldValue(this.state.name, rules); 
    }

    render() {
        const { rules, agentTokenId } = this.state;
        return (
            <div>
                <table className="table table-striped table-hover">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Nombre</th>
                            <th scope="col">Expresión regular</th>
                            <th scope="col">Nivel</th>
                            <th scope="col"></th>
                            <th scope="col"><Button onClick={this.addRule} >Agregar</Button></th>
                        </tr>
                    </thead>
                    {rules && rules.length > 0 &&
                        <tbody>
                            {rules.map((rule, index) =>
                                <tr key={'tr' + index}>
                                    <th scope="row">{index}</th>
                                    <td>{rule.name}</td>
                                    <td>{rule.regularExpression}</td>
                                    <td>{rule.severity}</td>
                                    <td>
                                        <Rule value={rule} agentTokenId={agentTokenId} indexValue={index} buttonValue="Ver" onChangeRuleHandler={this.onChangeRuleHandler} />
                                    </td>
                                    <td><Button onClick={() => this.removeRule(index)} >Eliminar</Button></td>
                                </tr>
                            )}
                        </tbody>
                    }
                 </table>
            </div>
        );
    }
}

export { AgentRules }