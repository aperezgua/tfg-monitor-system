import React from 'react';
import { Route, Routes} from 'react-router-dom'
import { UsersList, UserEdit } from 'App/Admin/Users';
import { NotFound } from 'App/NotFound';

class UsersPage extends React.Component {  
    render() {
        return (
            <div>
                <Routes>
                    <Route path="list" element={<UsersList/>} />
                    <Route path="edit/:id" element={<UserEdit/>} />
                    <Route path="*" element={<NotFound/>}/>
                </Routes>
            </div>
        );
    }
}

export {UsersPage}