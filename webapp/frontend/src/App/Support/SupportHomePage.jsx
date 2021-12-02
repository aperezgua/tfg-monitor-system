import React from 'react';
import { LastEventsConsole} from 'App/Support';

class SupportHomePage extends React.Component {
    constructor(props) {
        super(props);
    }
    
  
    render() {
        return (             
            <div>
             <h3>Support:</h3>
             <LastEventsConsole/>
             </div>
        );
    }
}
export {SupportHomePage}
