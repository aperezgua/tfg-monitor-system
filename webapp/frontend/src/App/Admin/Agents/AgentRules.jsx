import React from 'react';
import { Button } from 'react-bootstrap';
import { Rule } from 'App/Admin/Agents';

class AgentRules extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            rules: props.value
        };
        this.removeRule = this.removeRule.bind(this);
        this.onChangeRuleHandler = this.onChangeRuleHandler.bind(this);
    }

    componentWillReceiveProps(nextProps) {
        this.setState({ rules: nextProps.value })
    }
    
    
    onChangeRuleHandler(index, rule) {
        console("AgentRules change: " + index);
    }

    removeRule(index) {
        console.log("remove" + index);

        return false;
    }

    render() {
        const { rules } = this.state;
        return (
            <div>
                {rules && rules.length > 0 &&
                    <table className="table table-striped table-hover">
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Nombre</th>
                                <th scope="col">Expresión regular</th>
                                <th scope="col">Nivel</th>
                                <th scope="col"></th>
                                <th scope="col"></th>
                            </tr>
                        </thead>
                        <tbody>
                            {rules.map((rule, index) =>
                                <tr key={'tr' + index}>
                                    <th scope="row">{index}</th>
                                    <td>{rule.name}</td>
                                    <td>{rule.regularExpression}</td>
                                    <td>{rule.severity}</td>
                                    <td>
                                        <Rule value={rule} index={index} buttonValue="Ver" onChangeRuleHandler={this.onChangeRuleHandler} />
                                    </td>
                                    <td><Button onClick={() => this.removeRule(index)} >Eliminar</Button></td>
                                </tr>
                            )}
                        </tbody>
                    </table>
                }
            </div>
        );
    }
}

export { AgentRules }