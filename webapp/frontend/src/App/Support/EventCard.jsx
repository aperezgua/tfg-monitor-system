import React from 'react';
import { Button, Card } from 'react-bootstrap';
import Moment from 'moment';
import { eventLogService } from '_services';


/** Compontente para mostrar un card  */
class EventCard extends React.Component {
    
    constructor(props) {
        super(props);
        this.state={
            eventSeverity : props.eventSeverity,
            eventSummary : null
        };
    }

    /**  */
    componentWillReceiveProps(nextProps) {
       
    }

    componentWillUnmount() {
      clearInterval(this.interval);
    }
    
     
    /** Cuando es creado el componente se llama al servicio para cargar los sistemas y si se especifica token, los datos
        del agente */
    componentDidMount() {
        
        this.interval = setInterval(() => 
                eventLogService.findEventSummary(this.state.eventSeverity).then(
                    eventSummary => {
                        this.setState( { eventSummary } );
                    },
                    error => {
                        this.setState({error});
                    }
                ) 
            , 2000);
         
    }
     
    render() {
        const {  eventSummary } = this.state;
        let bgClass = 'success'; 
        if(eventSummary && eventSummary.severity == 'CRITICAL') {
            bgClass = 'danger';
        } else if(eventSummary && eventSummary.severity == 'MAJOR') {
            bgClass = 'warning';
        }
        
        return (
          <div>
              {eventSummary &&
              <Card
                bg={bgClass}
                key={'eventCard_' +eventSummary.severity}
                className="mb-2">
                <Card.Header>{eventSummary.number} {eventSummary.severity}</Card.Header>
                <Card.Body>
                  <Card.Text>
                    Incremento: {eventSummary.increment}%
                  </Card.Text>
                </Card.Body>
              </Card>
              }
          </div>
        );
    }
}

export { EventCard }