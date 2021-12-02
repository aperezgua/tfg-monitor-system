import React from 'react';
import { Button } from 'react-bootstrap';
import Moment from 'moment';
import { eventLogService } from '_services';

import './LastEventsConsole.css';

/** Compontente para mostrar la consolta de enventos */
class LastEventsConsole extends React.Component {
    constructor(props) {
        super(props);
        this.state={
            eventList : []
        };
    }

    /** Cuando se recibe async del padre, se actualiza el valores */
    componentWillReceiveProps(nextProps) {
       
    }

    componentWillUnmount() {
      clearInterval(this.interval);
    }
    
     
    /** Cuando es creado el componente se llama al servicio para cargar los sistemas y si se especifica token, los datos
        del agente */
    componentDidMount() {
        this.interval = setInterval(() => eventLogService.findLastLogEvents({limitResults : 10}).then(
                    eventList => {
                        this.setState( { eventList } );
                    },
                    error => {
                        this.setState({error});
                    }
                ) 
            , 2000);
         
    }
    render() {
        const { eventList } = this.state;
        return (
            <div>
                {eventList && eventList.length > 0 &&
                    <div className="console">
                    {eventList.map((event, index) =>
                        <div className={'severity' +event.rule.severity}>
                            <span>{Moment(event.date).format('DD/MM/yyyy HH:mm:ss')}</span>
                            <span>{event.agent.name}</span>
                            <span>{event.ruleName}</span>
                        </div>
                    )}
                    </div>
                }
            </div>
        );
    }
}

export { LastEventsConsole }