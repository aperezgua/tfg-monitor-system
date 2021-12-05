import React from 'react';
import { LastEventsConsole, EventCard,EventsBySeverity } from 'App/Support';
import { Row, Col } from 'react-bootstrap';
import './SupportHomePage.css';


class SupportHomePage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          error : null,
          selectedSeverity : null
        };
        
        this.refreshEventsBySeverity = this.refreshEventsBySeverity.bind(this);
    }
    
    refreshEventsBySeverity(severity) {        
        this.setState({selectedSeverity : severity});
    }
    
  
    render() {
        let {selectedSeverity} = this.state;
        return (
            <div className="support-home">
                <div className="support-home-cards">
                    <Row>
                        <Col>
                            <EventCard eventSeverity="CRITICAL" refreshTime="2000" showEvents={this.refreshEventsBySeverity}/>
                        </Col>
                        <Col>
                            <EventCard eventSeverity="MAJOR" refreshTime="2000"  showEvents={this.refreshEventsBySeverity}/>
                        </Col>
                        <Col>
                            <EventCard eventSeverity="MINOR" refreshTime="2000"  showEvents={this.refreshEventsBySeverity}/>
                        </Col>
                    </Row>
                </div>
                
                    <div className="support-home-events-severity">
                        {selectedSeverity && <EventsBySeverity refreshTime="2000"eventSeverity={selectedSeverity}/>}
                    </div>
                    <div className="support-home-console">
                        <LastEventsConsole refreshTime="2000"/>
                    </div>
                
            </div>
        );
    }
}
export {SupportHomePage}
