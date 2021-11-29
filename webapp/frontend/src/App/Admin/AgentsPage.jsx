import React from 'react';
import { Route, Routes} from 'react-router-dom'
import { AgentsList, AgentEdit } from 'App/Admin/Agents';
import { NotFound } from 'App/NotFound';

class AgentsPage extends React.Component {  
    render() {
        return (
            <div>
                <Routes>
                    <Route path="list" element={<AgentsList/>} />
                    <Route path="edit/:token" element={<AgentEdit/>} />
                    <Route path="*" element={<NotFound/>}/>
                </Routes>
            </div>
        );
    }
}

export {AgentsPage}