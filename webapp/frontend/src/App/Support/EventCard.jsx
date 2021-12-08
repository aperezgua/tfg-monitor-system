import React from 'react';
import { Button, Card } from 'react-bootstrap';
import { eventLogService } from '_services';


/** Compontente para mostrar un card  */
class EventCard extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            eventSeverity: props.eventSeverity,
            refreshTime: props.refreshTime,
            eventSummary: null,
            showEvents: props.showEvents
        };

        this.showEvents = this.showEvents.bind(this);
    }

    /**  */
    componentWillReceiveProps(nextProps) {

    }

    showEvents() {
        this.state.showEvents(this.state.eventSeverity);
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
                    this.setState({ eventSummary });
                },
                error => {
                    this.setState({ error });
                }
            )
            , this.state.refreshTime);

    }

    render() {
        const { eventSummary } = this.state;
        let bgClass = 'success';
        if (eventSummary && eventSummary.severity === 'CRITICAL') {
            bgClass = 'danger';
        } else if (eventSummary && eventSummary.severity === 'MAJOR') {
            bgClass = 'warning';
        }

        return (
            <div>
                {eventSummary &&
                    <Card
                        bg={bgClass}
                        key={'eventCard_' + eventSummary.severity}
                        className="mb-2">
                        <Card.Header>{eventSummary.number} {eventSummary.severity}</Card.Header>
                        <Card.Body>
                            <Card.Text>
                                Incremento: {eventSummary.increment}%
                  </Card.Text>
                            <Button variant="secondary" onClick={this.showEvents}>Ver</Button>
                        </Card.Body>
                    </Card>
                }
            </div>
        );
    }
}

export { EventCard }