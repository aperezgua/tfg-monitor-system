import React from 'react';
import { LastEventsConsole} from 'App/Support';
import { systemsService } from '_services';
import Select from 'react-select'

class SupportHomePage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          systemsList : null,
          error : null
        };
    }
    /** Cuando es creado el componente se llama al servicio para cargar los sistemas y si se especifica token, los datos
        del agente */
    componentDidMount() {
        systemsService.find({}).then(
            systemsList => {
                this.setState( { systemsList : [
  { value: 'chocolate', label: 'Chocolate' },
  { value: 'strawberry', label: 'Strawberry' },
  { value: 'vanilla', label: 'Vanilla' }
]} );
            },
            error => {
                this.setState({error});
            }
        ) ;
    }
  
    render() {
        
        return (             
            <div>
             <h3>Support:</h3>
             <div>
                 <Select options={this.state.systemsList} isMulti/>
             </div>
             <LastEventsConsole/>
             </div>
        );
    }
}
export {SupportHomePage}
