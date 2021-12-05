import React from 'react';
import { LastEventsConsole, EventCard } from 'App/Support';
import { Row, Col } from 'react-bootstrap';
import './SupportHomePage.css';


class SupportHomePage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          error : null
        };
    }
    
  
    render() {        
        return (
            <div className="support-home">
                <div className="support-home-cards">
                    <Row>
                        <Col>
                            <EventCard eventSeverity="CRITICAL" />
                        </Col>
                        <Col>
                            <EventCard eventSeverity="MAJOR" />
                        </Col>
                        <Col>
                            <EventCard eventSeverity="MINOR"/>
                        </Col>
                    </Row>
                </div>
                <div className="support-home-console">
                    <LastEventsConsole/>
                </div>
            </div>
        );
    }
}
export {SupportHomePage}
