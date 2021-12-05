import React from 'react';

import Moment from 'moment';
import { useParams } from "react-router-dom";
import { eventLogService } from '_services';
import { Chart } from 'react-charts'



/** Compontente para mostrar los eventos segun su criticidad */
class RuleEventsPageNoParams extends React.Component {
    
    constructor(props) {
        super(props);        
      
           let data =  [
              {
                label: 'Series 1',
                data: [[0, 1], [1, 2], [2, 4], [3, 2], [4, 7]]
              },
              {
                label: 'Series 2',
                data: [[0, 3], [1, 1], [2, 5], [3, 6], [4, 4]]
              }
            ];
       let axes =  [
              { primary: true, type: 'linear', position: 'bottom' },
              { type: 'linear', position: 'left' }
            ];
        this.state={
            eventList : null,
            data : data, 
            axes : axes
        };
        
        //https://www.npmjs.com/package/react-charts
        
    }

    
     
    /** */
    componentDidMount() {
        console.log("AKI: " +this.props.params.token + " " + this.props.params.rule);
     
    }
    render() {
        const { eventList, agentTokenId, data, axes} = this.state;
        return (
            <div>
                <h1>Grafica de eventos de {agentTokenId}</h1>
                {data && <Chart data={data} axes={axes} />}
                {eventList && eventList.length > 0 &&
                    <table className="table table-striped table-hover">
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Fecha</th>
                                <th scope="col">Sistema</th>
                                <th scope="col">Agente</th>
                                <th scope="col">Regla</th>
                            </tr>
                        </thead>
                        <tbody>
                            {eventList.map((event, index) =>
                                <tr key={'tr' + index}>
                                    <th scope="row">{index}</th>
                                    <td>{Moment(event.date).format('DD/MM/yyyy HH:mm:ss')}</td>
                                    <td>{event.agent.systems.name}</td>
                                    <td>{event.agent.name}</td>
                                    <td>{event.ruleName}</td>
                                </tr>
                            )}
                        </tbody>
                     </table>
                }
            </div>
        );
    }
}


function RuleEventsPage(props) {
    let params = useParams();
    return <RuleEventsPageNoParams {...props} params={params} />
}

export { RuleEventsPage }