import React from 'react';

import Moment from 'moment';
import { eventLogService } from '_services';

import './EventsBySeverity.css';


/** Compontente para mostrar los eventos segun su criticidad */
class EventsBySeverity extends React.Component {
    constructor(props) {
        super(props);
        this.state={
            eventList : [],            
            eventSeverity : props.eventSeverity,
            refreshTime : props.refreshTime
        };
    }

    /** Cuando se recibe async del padre, se actualiza el valores */
    componentWillReceiveProps(nextProps) {
       this.state={            
            eventSeverity : nextProps.eventSeverity
        };
    }

    componentWillUnmount() {
      clearInterval(this.interval);
    }
    
     
    /** Cuando es creado el componente se llama al servicio para cargar los sistemas y si se especifica token, los datos
        del agente */
    componentDidMount() {
        
        this.interval = setInterval(() => 
        eventLogService.findLogBySeverity(this.state.eventSeverity).then(
                    eventList => {
                        this.setState( { eventList } );
                    },
                    error => {
                        this.setState({error});
                    }
                ) 
            , this.state.refreshTime);
         
    }
    render() {
        const { eventList } = this.state;
        return (
            <div>
                <h1>Listado de eventos de tipo: {this.state.eventSeverity}</h1>
                {eventList && eventList.length > 0 &&
                    <div className="div-scroll">
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
                     </div>
                }
            </div>
        );
    }
}

export { EventsBySeverity }