import React from 'react';
import { Button } from 'react-bootstrap';
import { Condition } from 'App/Admin/Agents';

class RuleConditions extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            name: props.name,
            conditions: props.value
        };
        this.removeCondition = this.removeCondition.bind(this);
        this.addCondition = this.addCondition.bind(this);
        this.onChangeConditionHandler = this.onChangeConditionHandler.bind(this);
    }

    /** Cuando se recibe async del padre, se actualiza el valor */
    componentWillReceiveProps(nextProps) {
        this.setState({ conditions: nextProps.value })
    }
    
    /** Cuando cambiamos el valor de la condición notificamos al padre */
    onChangeConditionHandler(index, condition) {        
       let {conditions} = this.state;
       conditions[index] = condition;
       
       // Se notifica y volvemos a recibir a través de componentWillReceiveProps
       this.props.setFieldValue(this.state.name, conditions);
    }

    /** Eliminamos condición y notificamos al padre */
    removeCondition(index) {
        let conditions = this.state.conditions;
        conditions.splice(index, 1);
        this.props.setFieldValue(this.state.name, conditions);
        return false;
    }
    
    /** Agregamos nueva condición */
    addCondition() {
         let conditions = this.state.conditions;
         conditions.push({value : '', comparationType : 'CONTAINS', time : ''});
         this.props.setFieldValue(this.state.name, conditions); 
    }

    render() {
        const { conditions } = this.state;
        return (
            <div>
                <table className="table table-striped table-hover">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Tipo de comparación</th>
                            <th scope="col">Valor</th>
                            <th scope="col">Tiempo</th>
                            <th scope="col"></th>
                            <th scope="col"><Button onClick={this.addCondition} >+</Button></th>
                        </tr>
                    </thead>
                    {conditions && conditions.length > 0 &&
                        <tbody>
                            {conditions.map((condition, index) =>
                                <tr key={'tr' + index}>
                                    <th scope="row">{index}</th>
                                    <td>{condition.comparationType}</td>
                                    <td>{condition.value}</td>
                                    <td>{condition.time}</td>
                                    <td>
                                        <Condition value={condition} indexValue={index} buttonValue="Ver" onChangeConditionHandler={this.onChangeConditionHandler} />
                                    </td>
                                    <td><Button onClick={() => this.removeCondition(index)} >Eliminar</Button></td>
                                </tr>
                            )}
                        </tbody>
                    }
                 </table>
            </div>
        );
    }
}

export { RuleConditions }