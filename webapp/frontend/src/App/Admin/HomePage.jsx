import React from 'react';
import { agentService } from '_services';

class HomePage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            users : null
        };
    }
    
    componentDidMount() {
        agentService.findLastNotificationData().then(
            agents =>  {
                this.setState({ agents });
            },
            error => {
                console.log("Error: " +(typeof error) + " " + error);
            }
        );
    }
    
    render() {
        const { agents} = this.state;
        return (             
            <div>              
             <h3>Ultimos agentes notificados:</h3>
               {agents && agents.length > 0 &&
                   <table className="table table-striped table-hover">
                      <thead>
                        <tr>
                          <th scope="col">#</th>
                          <th scope="col">Nombre</th>
                          <th scope="col">Sistema</th>
                          <th scope="col">Fecha última notificación</th>
                          <th scope="col">Tamaño</th>
                        </tr>
                      </thead>
                      <tbody>
                        {agents.map(agent =>
                            <tr key={'tr' +agent.token}>
                              <th scope="row">{agent.token}</th>
                              <td>{agent.name}</td>
                              <td>{agent.systems && agent.systems.name}</td>
                              <td>{agent.lastNotification}</td>
                              <td>{agent.size}</td>
                            </tr>
                        )}
                      </tbody>
                   </table>
                }
             </div>
        );
    }
}
export {HomePage}
