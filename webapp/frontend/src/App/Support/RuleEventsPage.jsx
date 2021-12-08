import React from 'react';

import Moment from 'moment';
import { useParams } from "react-router-dom";
import { eventLogService } from '_services';
import { Chart } from 'react-charts'
import './RuleEventsPage.css';


/** Compontente para mostrar los eventos segun su criticidad */
class RuleEventsPageNoParams extends React.Component {
    
    constructor(props) {
       super(props);
      
       let axes =  [
              { primary: true, type: 'time', position: 'bottom' },
              { type: 'linear', position: 'left' }
            ];
        this.state={
            eventList : null,
            data : null, 
            axes : axes
        };
        
        //https://www.npmjs.com/package/react-charts
        
    }

    
     
    /** */
    componentDidMount() {
       console.log("AKI: " +this.props.params.token + " " + this.props.params.rule);
       eventLogService.findEventsByRule(this.props.params.token, this.props.params.rule).then(
                    eventList => {
                        this.setState( { eventList } );
                        
                        let values = [] 
                        for(let i=0; i < eventList.length; i++) {
                            values.push([new Date(eventList[i].date), eventList[i].ruleDoubleValue ]);
                        }
                        
                        
                        let data =  [
                              {
                                label: 'Series 1',
                                data: values
                              }
                            ];
            
       this.setState({data : data});
                    },
                    error => {
                        this.setState({error});
                    }
                ) 
        
      
     
    }
    render() {
        const { eventList, data, axes} = this.state;
        let  agentTokenId = this.props.params.token;
        return (
            <div>
                <div className="graphic">
                    {data && <Chart data={data} axes={axes} />}
                </div>
                <div className="rule-events-eventList">
                    <h1>Grafica de eventos de {agentTokenId}</h1>
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
            </div>
        );
    }
}


function RuleEventsPage(props) {
    let params = useParams();
    return <RuleEventsPageNoParams {...props} params={params} />
}

export { RuleEventsPage }