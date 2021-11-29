import React from 'react';
import { Route, Routes} from 'react-router-dom'
import { SystemsList, SystemsEdit } from 'App/Admin/Systems';
import { NotFound } from 'App/NotFound';

class SystemsPage extends React.Component {  
    render() {
        return (
            <div>
                <Routes>
                    <Route path="list" element={<SystemsList/>} />
                    <Route path="edit/:id" element={<SystemsEdit/>} />
                    <Route path="*" element={<NotFound/>}/>
                </Routes>
            </div>
        );
    }
}

export {SystemsPage}