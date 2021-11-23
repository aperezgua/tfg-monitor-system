import React from 'react';
import { Link } from "react-router-dom";
import { Button } from 'react-bootstrap';

class AgentRules extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            rules: props.value
        };
        this.removeRule = this.removeRule.bind(this);
    }

    componentWillReceiveProps(nextProps) {
        this.setState({ rules: nextProps.value })
    }

    removeRule(index) {
        console.log("remove" + index);

        return false;
    }

    render() {
        const { rules } = this.state;
        return (
            <div>Hello
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
                                <tr key={'tr' + rule.order}>
                                    <th scope="row">{rule.order}</th>
                                    <td>{rule.name}</td>
                                    <td>{rule.regularExpression}</td>
                                    <td>{rule.severity}</td>
                                    <td>Ver</td>
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